import React, { useState } from 'react'
import ProductsList from './ProductsList'
import ProfilesList from './ProfilesList'
import PurchasesList from './PurchasesList'
import TicketList from './TicketList'

export default function Accordion({ products = null, profiles = null, purchases = null, tickets = null }) {
   const [isOpen, setIsOpen] = useState(false)

   const toggleAccordion = () => {
      setIsOpen(!isOpen)
   }

   return (
      <div>
         <div
            className={`flex justify-between items-center text-white text-lg font-medium cursor-pointer p-2 rounded-lg ${
               isOpen ? '' : 'bg-gray-800'
            }`}
            onClick={toggleAccordion}
         >
            <span>
               {products && 'Products'}
               {profiles && 'Profiles'}
               {purchases && 'Purchases'}
               {tickets && 'Tickets'}
            </span>
            <svg
               xmlns="http://www.w3.org/2000/svg"
               className={`w-6 h-6 transition-transform transform ${isOpen ? 'rotate-180' : ''}`}
               fill="none"
               stroke="currentColor"
               viewBox="0 0 24 24"
            >
               <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d={isOpen ? 'M5 15l7-7 7 7' : 'M19 9l-7 7-7-7'}
               />
            </svg>
         </div>
         <div className={`relative overflow-x-auto rounded-lg w-full pb-10 ${isOpen ? 'block' : 'hidden'}`}>
            {isOpen && products && <ProductsList products={products} manager={true} />}
            {isOpen && profiles && <ProfilesList profiles={profiles} manager={true} />}
            {isOpen && purchases && <PurchasesList purchases={purchases} manager={true} />}
            {isOpen && tickets && <TicketList tickets={tickets} manager={true} />}
         </div>
      </div>
   )
}
