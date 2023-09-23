import { useEffect, useState } from 'react'
import Accordion from './Accordion'
import { useNavigate } from 'react-router-dom'

export default function Manager({ user }) {
   const [products, setProducts] = useState([])
   const [profiles, setProfiles] = useState([])
   const [purchases, setPurchases] = useState([])
   const [tickets, setTickets] = useState([])

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
      setProfiles(profiles)
   }

   const fetchAllPurchases = async () => {
      const res = await fetch(`/api/purchases`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const purchases = await res.json()
      setPurchases(purchases)
   }

   const fetchAllTickets = async () => {
      const res = await fetch(`/api/tickets`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const tickets = await res.json()
      console.log(tickets)
      setTickets(tickets)
   }

   useEffect(() => {
      fetchAllProducts()
      fetchAllProfiles()
      fetchAllPurchases()
      fetchAllTickets()
   }, [])

   return (
      <div className="flex flex-col gap-3">
         <Accordion products={products} />
         <Accordion profiles={profiles} />
         <Accordion purchases={purchases} />
         <Accordion tickets={tickets} />
      </div>
   )
}
