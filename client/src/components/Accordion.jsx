import React, { useState } from 'react'
import ProductsList from './ProductsList'
import ProfilesList from './ProfilesList'
import PurchasesList from './PurchasesList'
import TicketList from './TicketList'

export default function Accordion({ products, profiles, purchases, tickets, title, createTechnician, error }) {
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
            <span>{title}</span>
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
            {isOpen && title === 'Products' && <ProductsList products={products} manager={true} />}
            {isOpen && title === 'Profiles' && (
               <ProfilesList profiles={profiles} manager={true} createTechnician={createTechnician} error={error} />
            )}
            {isOpen && title === 'Purchases' && (
               <PurchasesList purchases={purchases} manager={true} tickets={tickets} />
            )}
            {isOpen && title === 'Tickets' && <TicketList tickets={tickets} manager={true} />}
         </div>
      </div>
   )
}
