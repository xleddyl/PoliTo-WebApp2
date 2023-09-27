import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import TicketList from '../components/TicketList'
import Chat from '../components/Chat'
import Status from '../components/Status'
import PurchasesList from '../components/PurchasesList'
import ProductsList from '../components/ProductsList'

const toBase64 = file => new Promise((resolve, reject) => {
   const reader = new FileReader();
   reader.readAsDataURL(file);
   reader.onload = () => resolve(reader.result);
   reader.onerror = reject;
})

export default function Ticket({ user }) {
   const { id } = useParams()
   const [ticket, setTicket] = useState(undefined)
   const [messages, setMessages] = useState([])
   const [purchase, setPurchase] = useState([])
   const [product, setProduct] = useState([])
   const [error, setError] = useState('')

   const navigate = useNavigate()

   const fetchTicket = async () => {
      const res = await fetch(`/api/tickets/${id}`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const ticket = await res.json()
      setTicket(ticket)

      const res2 = await fetch(`/api/purchases/list`, {
         method: 'POST',
         body: JSON.stringify([ticket.purchaseID]),
         headers: {
            'Content-type': 'application/json',
         },
      })
      if (res2.status === 401) navigate('/', { replace: true })
      if (!res2.ok) return
      const purchase = await res2.json()
      console.log(purchase)
      setPurchase(purchase[0])

      const res3 = await fetch(`/api/products/${purchase[0].product}`)
      if (res3.status === 401) navigate('/', { replace: true })
      if (!res3.ok) return
      const product = await res3.json()
      setProduct(product)
   }

   const fetchMessages = async () => {
      const res = await fetch(`/api/tickets/${id}/messages`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const messages = await res.json()
      console.log(messages)
      setMessages(messages)
   }

   const sendMessage = async (message, file) => {
      if (!file && !message) return
      const b64 = file ? await toBase64(file) : undefined
      const data = {
         ticket: id,
         fromCustomer: user.role === 'CUSTOMER',
         attachment: b64,
         content: message,
      }
      const res = await fetch(`/api/tickets/${id}/messages`, {
         method: 'POST',
         body: JSON.stringify(data),
         headers: {
            'Content-type': 'application/json',
         },
      })
      if (res.status === 401) navigate('/', { replace: true })
      if (res.ok) fetchMessages()
   }

   const updateStatus = async (status) => {
      setError('')
      try {
         const res = await fetch(`/api/tickets/${id}/status`, {
            method: 'PUT',
            body: JSON.stringify({ status }),
            headers: {
               'Content-type': 'application/json',
            },
         })
         if (!res.ok) {
            setError('Something bad happened')
            return
         }
         if (res.status === 401) navigate('/', { replace: true })
         if (res.ok) fetchTicket()
      } catch (e) {
         console.log(e)
         setError('Something bad happened: ' + e)
      } 
   }

   const updateTicket = async (priority, technician) => {
      const res = await fetch(`/api/tickets/${id}`, {
         method: 'PUT',
         body: JSON.stringify({
            id: ticket.id,
            technicianID: technician,
            statuses: ticket.statuses,
            description: ticket.description,
            priority: priority,
            messagesIDs: ticket.messagesIDs,
            purchaseID: ticket.purchaseID,
         }),
         headers: {
            'Content-type': 'application/json',
         },
      })
      if (res.status === 401) navigate('/', { replace: true })
      if (res.ok) fetchTicket()
   }

   useEffect(() => {
      if (!id) navigate('/', { replace: true })
      fetchTicket()
      fetchMessages()
      const polling = setInterval(() => fetchMessages(), 5000)
      return () => clearInterval(polling) // rimuovi l'interval per evitare memory leak
   }, [])

   if (!id) return null

   return (
      <div className="flex flex-col gap-20">
         {ticket && (
            <>
               <div className='flex flex-col gap-5'>
                  <TicketList tickets={[ticket]} ticketPage={true} user={user} updateTicket={updateTicket} />
                  <PurchasesList purchases={[purchase]} ticketPage={true} />
                  <ProductsList products={[product]} ticketPage={true} />
               </div>
               <Status updateStatus={updateStatus} user={user} statuses={ticket.statuses} error={error} />
               <Chat messages={messages} sendMessage={sendMessage} technician={ticket.technicianID} user={user} />
            </>
         )}
      </div>
   )
}
