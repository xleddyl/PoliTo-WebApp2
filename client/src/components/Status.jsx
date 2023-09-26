import { useState } from 'react'

export default function Status({ updateStatus, statuses, user }) {
   const [selectedStatus, setSelectedStatus] = useState('')

   return (
      <div className="mx-auto">
         {statuses && (
            <>
               <div className="text-white text-lg font-medium pb-2">Statuses</div>
               <div className="flex flex-row flex-wrap gap-2">
                  {statuses.map((p, i) => (
                     <div
                        key={i}
                        className={`${
                           i === statuses.length - 1 ? 'bg-gray-700' : 'bg-gray-800'
                        } px-5 py-3 text-white rounded-md`}
                     >
                        {p}
                     </div>
                  ))}
               </div>
            </>
         )}

         {user.role !== 'CUSTOMER' && (
            <>
               <div className="text-white text-lg font-medium pb-2 pt-5">Update Status</div>
               <div className="flex space-x-2">
                  <select
                     className="border border-gray-300 p-2 rounded"
                     onChange={(e) => setSelectedStatus(e.target.value)}
                     value={selectedStatus}
                  >
                     <option value="" disabled>
                        Select
                     </option>
                     <option value="OPEN">OPEN</option>
                     <option value="CLOSED">CLOSED</option>
                     <option value="IN_PROGRESS">IN PROGRESS</option>
                     <option value="RESOLVED">RESOLVED</option>
                     <option value="REOPEN">REOPEN</option>
                  </select>
                  <button
                     className="py-3 px-5 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
                     onClick={() => {
                        updateStatus(selectedStatus)
                        setSelectedStatus('')
                     }}
                  >
                     Confirm
                  </button>
               </div>
            </>
         )}
      </div>
   )
}
