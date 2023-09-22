import { useNavigate } from 'react-router-dom'

export default function TicketList({ tickets, ticketPage }) {
   const navigate = useNavigate()

   return (
      <div className="flex flex-row gap-10">
         <div className="flex-grow">
            <div className="text-white text-lg font-medium pb-2">{ticketPage ? 'Ticket' : 'Tickets'}</div>
            <div className="relative overflow-x-auto shadow-md rounded-lg w-full">
               <table className="w-full text-sm text-left text-gray-400">
                  <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                     <tr>
                        <th scope="col" className="px-6 py-3">
                           id
                        </th>
                        {!ticketPage && (
                           <th scope="col" className="px-6 py-3">
                              status
                           </th>
                        )}
                        <th scope="col" className="px-6 py-3">
                           description
                        </th>
                        <th scope="col" className="px-6 py-3">
                           priority level
                        </th>
                        <th scope="col" className="px-6 py-3">
                           purchase ID
                        </th>
                        {ticketPage ? (
                           ''
                        ) : (
                           <th scope="col" className="px-6 py-3 text-center">
                              view ticket
                           </th>
                        )}
                     </tr>
                  </thead>
                  <tbody>
                     {tickets &&
                        tickets.map((p, i) => (
                           <tr
                              key={p.id}
                              className={'border-b border-gray-700 ' + (i % 2 === 0 ? 'bg-gray-800' : 'bg-gray-900')}
                           >
                              <th scope="row" className="px-6 py-4 font-medium whitespace-nowrap text-white">
                                 {p.id}
                              </th>
                              {!ticketPage && <td className="px-6 py-4">{p.statuses[p.statuses.length - 1]}</td>}
                              <td className="px-6 py-4">{p.description}</td>
                              <td className="px-6 py-4">{p.priority}</td>
                              <td className="px-6 py-4">{p.purchaseID}</td>
                              {ticketPage ? (
                                 ''
                              ) : (
                                 <td className="px-6 py-4 flex justify-center">
                                    <svg
                                       xmlns="http://www.w3.org/2000/svg"
                                       className="text-green-800 font-medium cursor-pointer"
                                       fill="currentColor"
                                       height="1em"
                                       viewBox="0 0 512 512"
                                       onClick={() => navigate(`/dashboard/tickets/${p.id}`)}
                                    >
                                       <path d="M320 0c-17.7 0-32 14.3-32 32s14.3 32 32 32h82.7L201.4 265.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L448 109.3V192c0 17.7 14.3 32 32 32s32-14.3 32-32V32c0-17.7-14.3-32-32-32H320zM80 32C35.8 32 0 67.8 0 112V432c0 44.2 35.8 80 80 80H400c44.2 0 80-35.8 80-80V320c0-17.7-14.3-32-32-32s-32 14.3-32 32V432c0 8.8-7.2 16-16 16H80c-8.8 0-16-7.2-16-16V112c0-8.8 7.2-16 16-16H192c17.7 0 32-14.3 32-32s-14.3-32-32-32H80z" />
                                    </svg>
                                 </td>
                              )}
                           </tr>
                        ))}
                  </tbody>
               </table>
            </div>
         </div>
      </div>
   )
}
