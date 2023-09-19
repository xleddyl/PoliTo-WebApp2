import { useState } from 'react'

export default function AddTicket({ addTicket }) {
   const [purchaseID, setPurchaseID] = useState('')
   const [description, setDescription] = useState('')

   return (
      <div className="flex flex-col items-center justify-center px-6 py-24 mx-auto">
         <div className="w-full rounded-lg shadow border md:mt-0 sm:max-w-md xl:p-0 bg-gray-800 border-gray-700">
            <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
               <h1 className="text-xl font-bold leading-tight tracking-tight md:text-2xl text-white">
                  Create a ticket
               </h1>
               <form className="space-y-4 md:space-y-6" action="#">
                  <div>
                     <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                        Purchase ID
                     </label>
                     <input
                        type="number"
                        name="text"
                        id="ID"
                        className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                        placeholder="ID"
                        required
                        onChange={(e) => setPurchaseID(e.target.value)}
                        value={purchaseID}
                     />
                  </div>
                  <div>
                     <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                        Description
                     </label>
                     <input
                        type="text"
                        name="text"
                        id="description"
                        className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                        placeholder="description"
                        required
                        onChange={(e) => setDescription(e.target.value)}
                        value={description}
                     />
                  </div>

                  <button
                     type="submit"
                     className="w-full text-white focus:ring-4 focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center bg-primary-600 hover:bg-primary-700 focus:ring-primary-800"
                     onClick={(e) => {
                        e.preventDefault()
                        addTicket(purchaseID, description)
                     }}
                  >
                     Create ticket
                  </button>
               </form>
            </div>
         </div>
      </div>
   )
}
