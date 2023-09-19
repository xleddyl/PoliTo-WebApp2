import PurchasesList from './PurchasesList'
import ProductsList from './ProductsList'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function Customer({ user }) {
   const [purchases, setPurchases] = useState([])
   const [products, setProducts] = useState([])
   const navigate = useNavigate()

   const fetchProducts = async () => {
      const res = await fetch(`/api/products`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const products = await res.json()
      setProducts(() => products.sort((a, b) => a.id - b.id))
   }

   const fetchPurchases = async () => {
      const res = await fetch(`/api/purchases/${user.email}`)
      if (res.status === 401) navigate('/', { replace: true })
      if (!res.ok) return
      const purchases = await res.json()
      setPurchases(() => purchases.sort((a, b) => a.id - b.id))
   }

   const addTicket = async (id, description) => {
      const data = {
         description: description,
         purchaseID: id,
      }
      const res = await fetch('/api/tickets', {
         method: 'POST',
         body: JSON.stringify(data),
         headers: {
            'Content-type': 'application/json',
         },
      })
      if (res.status === 401) navigate('/', { replace: true })
      if (res.ok) fetchPurchases()
   }

   const addPurchase = async (p) => {
      const data = {
         customer: user.email,
         product: p.ean,
         date: new Date(),
      }
      const res = await fetch('/api/purchases', {
         method: 'POST',
         body: JSON.stringify(data),
         headers: {
            'Content-type': 'application/json',
         },
      })
      if (res.status === 401) navigate('/', { replace: true })
      if (res.ok) fetchPurchases()
   }

   useEffect(() => {
      fetchProducts()
      fetchPurchases()
   }, [])

   return (
      <div className="flex flex-col gap-10">
         <PurchasesList purchases={purchases} addTicket={addTicket} />
         <ProductsList
            products={products}
            purchasedProducts={purchases.map((p) => p.product)}
            addPurchase={addPurchase}
         />
      </div>
   )
}
