import { useState } from 'react'

export default function ProfilesList({ profiles, manager, createTechnician, error }) {
   const [isPopupOpen, setIsPopupOpen] = useState(false)
   const [selectedProfile, setSelectedProfile] = useState(null)
   const [openTechnician, setOpenTechnician] = useState(false)

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
         <div className="flex-grow flex flex-col gap-2">
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
            <button
               className="py-3 px-5 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
               onClick={() => setOpenTechnician(true)}
            >
               Add Technician
            </button>
         </div>

         {openTechnician && <TechnicianPopup onClose={() => setOpenTechnician(false)} submit={createTechnician} error={error} />}
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

function TechnicianPopup({ submit, onClose, error }) {
   const [username, setUsername] = useState('')
   const [password, setPassword] = useState('')
   const [email, setEmail] = useState('')
   const [firstName, setFirstName] = useState('')
   const [lastName, setLastName] = useState('')
   const [phone, setPhone] = useState('')
   const [specialization, setSpecialization] = useState('')
   const [address, setAddress] = useState('')

   return (
      <div className="fixed inset-0 flex items-center justify-center z-50">
         <div className="bg-gray-800 max-w-screen-md px-20 py-10 rounded-lg border-gray-600 border-2">
            <form className="space-y-4 md:space-y-6" action="#">
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="username"
                  required
                  onChange={(e) => setUsername(e.target.value)}
                  value={username}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="password"
                  required
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="email"
                  required
                  onChange={(e) => setEmail(e.target.value)}
                  value={email}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="firstName"
                  required
                  onChange={(e) => setFirstName(e.target.value)}
                  value={firstName}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="lastName"
                  required
                  onChange={(e) => setLastName(e.target.value)}
                  value={lastName}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="phone"
                  required
                  onChange={(e) => setPhone(e.target.value)}
                  value={phone}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="address"
                  required
                  onChange={(e) => setAddress(e.target.value)}
                  value={address}
               />
               <input
                  type="text"
                  name="text"
                  className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                  placeholder="specialization"
                  required
                  onChange={(e) => setSpecialization(e.target.value)}
                  value={specialization}
               />
               {error && (
                              <div className="mt-1 font-semibold text-red-600 w-96 whitespace-pre-wrap">{error}</div>
                           )}

               <div className='flex flex-row gap-2'>
                  <button
                     className="py-3 px-5 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
                     onClick={() => {
                        submit({username,
                           password,
                           email,
                           firstName,
                           lastName,
                           phone,
                           specialization, address})
                        onClose()
                     }}
                  >
                     Add Technician
                  </button>
                  <button
                     className="py-3 px-5 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
                     onClick={() => onClose()}
                     >
                     Close
                  </button>
               </div>
            </form>
         </div>
      </div>
   )
}
