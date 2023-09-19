import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Customer, Manager, Technician } from '../components'

export default function Dashboard() {
   const [user, setUser] = useState(undefined)
   const navigate = useNavigate()

   useEffect(() => {
      const user = localStorage.getItem('user')
      if (user == null) navigate('/', { replace: true })
      setUser(JSON.parse(user))
   }, [navigate])

   const logout = async () => {
      await fetch('/logout', { method: 'GET', mode: 'no-cors' })
      localStorage.clear('user')
      setUser(undefined)
      navigate('/', { replace: true })
   }

   if (!user) return null

   return (
      <div className="bg-gray-50 dark:bg-gray-900 p-10">
         <div className="max-w-screen-2xl">
            <div className="pb-12">
               <div className="flex flex-row justify-between align-middle border-b-2 border-gray-800">
                  <div className="text-white">
                     Logged in as <span className="font-medium">{user.email}</span> - (
                     <span className="font-medium">{user.role}</span>)
                  </div>
                  {/* <button
                  type="button"
                  className="text-white bg-yellow-700 hover:bg-yellow-800 focus:ring-4 focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-yellow-600 dark:hover:bg-yellow-700 focus:outline-none dark:focus:ring-yellow-800"
                  onClick={() => window.location.href = 'http://localhost:3000'}
               >
                  DEBUG - GO TO :3000 (to be removed)
               </button> */}
                  <button
                     type="button"
                     className="text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 focus:outline-none dark:focus:ring-red-800"
                     onClick={() => logout()}
                  >
                     Logout
                  </button>
               </div>
            </div>

            <div>
               {user && user.role === 'CUSTOMER' && <Customer user={user} />}
               {user && user.role === 'MANAGER' && <Manager user={user} />}
               {user && user.role === 'TECHNICIAN' && <Technician user={user} />}
            </div>
         </div>
      </div>
   )
}
