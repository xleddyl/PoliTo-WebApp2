import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import AddTicket from './AddTicket'

export default function PurchaseList({ purchases, addTicket }) {
   const navigate = useNavigate()

   return (
      <div className="flex flex-row">
         <div className="flex-grow">
            <div className="text-white text-lg font-medium">Purchases</div>
            <div className="relative overflow-x-auto shadow-md rounded-lg max-w-lg">
               <table className="w-full text-sm text-left text-gray-400">
                  <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                     <tr>
                        <th scope="col" className="px-6 py-3">
                           id
                        </th>
                        <th scope="col" className="px-6 py-3">
                           Date
                        </th>
                        <th scope="col" className="px-6 py-3">
                           Product
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                           Ticket
                        </th>
                     </tr>
                  </thead>
                  <tbody>
                     {purchases &&
                        purchases.map((p, i) => (
                           <tr
                              key={p.id}
                              className={'border-b border-gray-700 ' + (i % 2 === 0 ? 'bg-gray-800' : 'bg-gray-900')}
                           >
                              <th scope="row" className="px-6 py-4 font-medium whitespace-nowrap text-white">
                                 {p.id}
                              </th>
                              <td className="px-6 py-4">{p.date}</td>
                              <td className="px-6 py-4">{p.product}</td>
                              <td className="px-6 py-4 flex justify-center">
                                 {p.ticketID ? (
                                    <svg
                                       xmlns="http://www.w3.org/2000/svg"
                                       className="text-green-800 font-medium cursor-pointer"
                                       fill="currentColor"
                                       height="1em"
                                       viewBox="0 0 448 512"
                                    >
                                       <path
                                          d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"
                                          onClick={() => navigate(`/dashboard/tickets/${p.ticketID}`)}
                                       />
                                    </svg>
                                 ) : (
                                    <div className="text-gray-600">-</div>
                                 )}
                              </td>
                           </tr>
                        ))}
                  </tbody>
               </table>
            </div>
         </div>
         <AddTicket addTicket={addTicket} />
      </div>
   )
}
