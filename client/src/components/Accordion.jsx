import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import ProductsList from './ProductsList'
import ProfilesList from './ProfilesList'
import PurchasesList from './PurchasesList'

export default function Accordion({ products=null, profiles=null, purchases=null }) {
   const navigate = useNavigate()

   // Define the state for the accordion
   const [isOpen, setIsOpen] = useState(false)

   // Function to toggle the accordion
   const toggleAccordion = () => {
      setIsOpen(!isOpen)
   }

   return (
    <div className="flex flex-row gap-10">
      <div className="flex-grow">
        <div
          className={`flex justify-between items-center text-white text-lg font-medium pb-2 cursor-pointer ${
            isOpen ? '' : 'bg-gray-800' // Inverted background colors here
          }`}
          onClick={toggleAccordion}
        >
          <span>{products && "Products"}{profiles && "Profiles"}{purchases && "Purchases"}</span>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className={`w-6 h-6 transition-transform transform ${
              isOpen ? 'rotate-180' : ''
            }`}
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
        <div
          className={`relative overflow-x-auto shadow-md rounded-lg w-full ${
            isOpen ? 'block' : 'hidden'
          }`}
        >
          {isOpen && products && <ProductsList products={products} />}
          {isOpen && profiles && <ProfilesList profiles={profiles} />}
          {isOpen && purchases && <PurchasesList purchases={purchases} />}
        </div>
      </div>
    </div>
  );
}
