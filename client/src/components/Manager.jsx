import { useEffect, useState } from 'react'
import Accordion from './Accordion'
import { useNavigate } from 'react-router-dom'

export default function Manager({ user }) {
   const [products, setProducts] = useState([])
   const [profiles, setProfiles] = useState([])
   const [purchases, setPurchases] = useState([])
   const [tickets, setTickets] = useState([])
   const [error, setError] = useState('')

   const navigate = useNavigate()

   const fetchAllProducts = async () => {
      const res = await fetch(`/api/products`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const products = await res.json()
      setProducts(() => products.sort((a, b) => a.id - b.id))
   }

   const fetchAllProfiles = async () => {
      const res = await fetch(`/api/profiles`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const profiles = await res.json()
      setProfiles(() => profiles.sort((a, b) => a.email.localeCompare(b.email)))
   }

   const fetchAllPurchases = async () => {
      const res = await fetch(`/api/purchases`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const purchases = await res.json()
      setPurchases(() => purchases.sort((a, b) => a.id - b.id))
   }

   const fetchAllTickets = async () => {
      const res = await fetch(`/api/tickets`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const tickets = await res.json()
      setTickets(() => tickets.sort((a, b) => a.id - b.id))
   }

   const createTechnician = async (obj) => {
      setError('')
      try {
         const data = {
            ...obj,
            managerID: user.email,
         }
         const res = await fetch('/api/createExpert', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
               'Content-type': 'application/json',
            },
         })
         if (res.status === 401) navigate('/', { replace: true })
         if (res.ok) fetchAllProfiles()
      } catch (e) {
         console.log(e)
         setError('Something bad happened: ' + e)
      } 
   }

   useEffect(() => {
      fetchAllProducts()
      fetchAllProfiles()
      fetchAllPurchases()
      fetchAllTickets()
   }, [])

   return (
      <div className="flex flex-col gap-3">
         <Accordion products={products} title="Products" />
         <Accordion profiles={profiles} title="Profiles" createTechnician={createTechnician} error={error}/>
         <Accordion purchases={purchases} tickets={tickets} title="Purchases" />
         <Accordion tickets={tickets} title="Tickets" />
      </div>
   )
}
