import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import TicketList from '../components/TicketList'
import Chat from '../components/Chat'

export default function Ticket() {
   const { id } = useParams()
   const [ticket, setTicket] = useState(undefined)
   const [messages, setMessages] = useState([])
   const navigate = useNavigate()

   const fetchTicket = async () => {
      const res = await fetch(`/api/tickets/${id}`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const ticket = await res.json()
      setTicket(ticket)
      console.log(ticket)
   }

   const fetchMessages = async () => {
      const res = await fetch(`/api/tickets/${id}/messages`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const messages = await res.json()
      setMessages(messages)
      console.log(messages)
   }

   const sendMessage = async (message, file) => {
      console.log(message, file)
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
         {ticket && <TicketList tickets={[ticket]} ticketPage={true} />}
         <Chat messages={messages} sendMessage={sendMessage} />
      </div>
   )
}
