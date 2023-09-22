import { useState } from 'react'

export default function StatusUpdate({ updateStatus }) {
   const [selectedStatus, setSelectedStatus] = useState('')

   return (
      <div className="mx-auto">
         <div className="text-white text-lg font-medium pb-2">Update Status</div>
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
               className="bg-blue-500 text-white p-2 rounded"
               onClick={() => {
                  updateStatus(selectedStatus)
                  setSelectedStatus('')
               }}
            >
               Confirm
            </button>
         </div>
      </div>
   )
}
