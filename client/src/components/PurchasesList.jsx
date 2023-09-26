import { useNavigate } from 'react-router-dom'
import AddTicket from './AddTicket'
import { useEffect, useState } from 'react'

export default function PurchasesList({ purchases, addTicket, manager, tickets, ticketPage }) {
   const navigate = useNavigate()
   const [show, setShow] = useState([])

   useEffect(() => {
      if (ticketPage) return
      const list = purchases.map((p) => {
         return tickets.find((t) => t.id === p.ticketID)?.technicianID
      })
      setShow(list)
   }, [tickets, purchases])

   return (
      <div className="flex flex-row gap-10">
         <div className="flex-grow">
            {addTicket && <div className="text-white text-lg font-medium pb-2">Purchases</div>}
            {!addTicket && ticketPage && <div className="text-white text-lg font-medium pb-2">Purchase</div>}
            <div className="relative overflow-x-auto shadow-md rounded-lg w-full">
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
                        {!ticketPage && <th scope="col" className="px-6 py-3 text-center">
                           Ticket
                        </th>}
                        {manager && (
                           <>
                              <th scope="col" className="px-6 py-3 text-center">
                                 Customer
                              </th>
                           </>
                        )}
                     </tr>
                  </thead>
                  <tbody>
                     {purchases &&
                        purchases.map((p, i) => (
                           <tr
                              key={p.id+'_purchase'}
                              className={'border-b border-gray-700 ' + (i % 2 === 0 ? 'bg-gray-800' : 'bg-gray-900')}
                           >
                              <th scope="row" className="px-6 py-4 font-medium whitespace-nowrap text-white">
                                 {p.id}
                              </th>
                              <td className="px-6 py-4">{p.date}</td>
                              <td className="px-6 py-4">{p.product}</td>
                              {!ticketPage && <td className="px-6 py-4 flex justify-center">
                                 {p.ticketID ? (
                                    <div className="flex flex-row gap-2">
                                       <svg
                                          xmlns="http://www.w3.org/2000/svg"
                                          className="text-green-800 font-medium cursor-pointer"
                                          fill="currentColor"
                                          height="1em"
                                          viewBox="0 0 512 512"
                                          onClick={() => navigate(`/dashboard/tickets/${p.ticketID}`)}
                                       >
                                          <path d="M320 0c-17.7 0-32 14.3-32 32s14.3 32 32 32h82.7L201.4 265.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L448 109.3V192c0 17.7 14.3 32 32 32s32-14.3 32-32V32c0-17.7-14.3-32-32-32H320zM80 32C35.8 32 0 67.8 0 112V432c0 44.2 35.8 80 80 80H400c44.2 0 80-35.8 80-80V320c0-17.7-14.3-32-32-32s-32 14.3-32 32V432c0 8.8-7.2 16-16 16H80c-8.8 0-16-7.2-16-16V112c0-8.8 7.2-16 16-16H192c17.7 0 32-14.3 32-32s-14.3-32-32-32H80z" />
                                       </svg>
                                       {!show[i] && (
                                          <svg
                                             xmlns="http://www.w3.org/2000/svg"
                                             className="text-yellow-800 font-medium cursor-pointer"
                                             fill="currentColor"
                                             height="1em"
                                             viewBox="0 0 512 512"
                                             onClick={() => navigate(`/dashboard/tickets/${p.ticketID}`)}
                                          >
                                             <path d="M256 32c14.2 0 27.3 7.5 34.5 19.8l216 368c7.3 12.4 7.3 27.7 .2 40.1S486.3 480 472 480H40c-14.3 0-27.6-7.7-34.7-20.1s-7-27.8 .2-40.1l216-368C228.7 39.5 241.8 32 256 32zm0 128c-13.3 0-24 10.7-24 24V296c0 13.3 10.7 24 24 24s24-10.7 24-24V184c0-13.3-10.7-24-24-24zm32 224a32 32 0 1 0 -64 0 32 32 0 1 0 64 0z" />
                                          </svg>
                                       )}
                                    </div>
                                 ) : (
                                    <div className="text-gray-600 italic">-</div>
                                 )}
                              </td>}
                              {manager && <td className="px-6 py-3 text-center">{p.customer}</td>}
                           </tr>
                        ))}
                  </tbody>
               </table>
            </div>
         </div>
         {addTicket && <AddTicket addTicket={addTicket} />}
      </div>
   )
}
