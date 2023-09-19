import { useEffect, useState } from 'react'
import TicketList from './TicketList'
import { useNavigate } from 'react-router-dom'

export default function Technician({ user }) {
   const [tickets, setTickets] = useState([])
   const navigate = useNavigate()

   const fetchTickets = async () => {
      const res = await fetch(`/api/tickets`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const tickets = await res.json()
      setTickets(() => tickets.sort((a, b) => a.id - b.id))
   }

   useEffect(() => {
      fetchTickets()
   }, [])

   return <TicketList tickets={tickets} />
}
