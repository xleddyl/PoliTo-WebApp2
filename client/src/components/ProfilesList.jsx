import { useState } from 'react'

export default function ProfilesList({ profiles, manager }) {
   const [isPopupOpen, setIsPopupOpen] = useState(false)
   const [selectedProfile, setSelectedProfile] = useState(null)

   const openPopup = (profile) => {
      setSelectedProfile(profile)
      setIsPopupOpen(true)
   }

   const closePopup = () => {
      setSelectedProfile(null)
      setIsPopupOpen(false)
   }

   return (
      <div className="flex flex-row">
         <div className="flex-grow">
            <div className="relative overflow-x-auto shadow-md rounded-lg w-full">
               <table className="w-full text-sm text-left text-gray-400">
                  <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                     <tr>
                        <th scope="col" className="px-6 py-3">
                           email
                        </th>
                        <th scope="col" className="px-6 py-3">
                           role
                        </th>
                        <th scope="col" className="px-6 py-3">
                           name
                        </th>
                        <th scope="col" className="px-6 py-3">
                           phone
                        </th>
                        <th></th>
                     </tr>
                  </thead>
                  <tbody>
                     {profiles &&
                        profiles.map((p, i) => (
                           <tr
                              key={p.email}
                              className={'border-b border-gray-700 ' + (i % 2 === 0 ? 'bg-gray-800' : 'bg-gray-900')}
                           >
                              <td className="px-6 py-4">{p.email}</td>
                              <td className="px-6 py-4">
                                 {p.address ? 'CUSTOMER' : p.specialization ? 'TECHNICIAN' : 'MANAGER'}
                              </td>
                              <td className="px-6 py-4">{p.name}</td>
                              <td className="px-6 py-4">{p.phone}</td>
                              <td>
                                 <button onClick={() => openPopup(p)}>more</button>
                              </td>
                           </tr>
                        ))}
                  </tbody>
               </table>
            </div>
         </div>

         {isPopupOpen && selectedProfile && <ProfilePopup profile={selectedProfile} onClose={closePopup} />}
      </div>
   )
}

function ProfilePopup({ profile, onClose }) {
   const role = profile.address ? 'CUSTOMER' : profile.specialization ? 'TECHNICIAN' : 'MANAGER'

   return (
      <div className="fixed inset-0 flex items-center justify-center z-50">
         <div className="bg-white p-4 w-1/2 rounded-lg shadow-md">
            <h2 className="text-lg font-semibold mb-4">{profile.name}</h2>
            <div className="flex flex-col space-y-2">
               <div>
                  <strong>Email:</strong> {profile.email}
               </div>
               <div>
                  <strong>Role:</strong> {role}
               </div>
               <div>
                  <strong>Phone:</strong> {profile.phone}
               </div>
               {role === 'CUSTOMER' && (
                  <>
                     <div>
                        <strong>Address: </strong> {profile.address}
                     </div>
                     <div>{profile.purchasesIDs.length} purchases made</div>
                  </>
               )}
               {role === 'TECHNICIAN' && (
                  <>
                     <div>
                        <strong>Specialization: </strong> {profile.specialization}
                     </div>
                     <div>
                        <strong>Assigned to: </strong>
                        {profile.managerID}
                     </div>
                  </>
               )}
               {role === 'MANAGER' && (
                  <>
                     <div>
                        <strong>Level: </strong> {profile.level}
                     </div>
                     <div>
                        <strong>Works with: </strong> {profile.techniciansIDs.map((t) => `${t} `)}
                     </div>
                  </>
               )}
            </div>
            <button onClick={onClose} className="mt-4 bg-gray-800 hover:bg-gray-700 text-white py-2 px-4 rounded">
               Close
            </button>
         </div>
      </div>
   )
}
