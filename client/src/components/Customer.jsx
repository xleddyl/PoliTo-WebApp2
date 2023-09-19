import PurchasesList from './PurchasesList'
import ProductsList from './ProductsList'
import { useEffect, useState } from 'react'

export default function Customer({ user }) {
   const [purchases, setPurchases] = useState([])
   const [products, setProducts] = useState([])

   const fetchProducts = async () => {
      const res = await fetch(`/api/products`)
      if (!res.ok) return
      const products = await res.json()
      setProducts(products)
   }

   const fetchPurchases = async () => {
      const res = await fetch(`/api/purchases/${user.email}`)
      if (!res.ok) return
      const purchases = await res.json()
      setPurchases(purchases)
   }

   const addTicket = async () => {
      console.log('ciao')
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
