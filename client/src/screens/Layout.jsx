import { useEffect } from 'react'
import { Outlet, useNavigate } from 'react-router-dom'

export default function Layout({ user, setUser }) {
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
      <div className="bg-gray-900 p-10 min-h-screen">
         <div className="max-w-screen-xl mx-auto">
            <div className="pb-12">
               <div className="flex flex-row justify-between align-middle border-b-2 border-gray-800">
                  <div className="text-white">
                     Logged in as <span className="font-medium">{user.email}</span> - (
                     <span className="font-medium">{user.role}</span>)
                  </div>
                  {/* <button
                     type="button"
                     className="text-white focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 bg-yellow-600 hover:bg-yellow-700 focus:outline-none focus:ring-yellow-800"
                     onClick={() => (window.location.href = 'http://localhost:3000')}
                  >
                     DEBUG - GO TO :3000 (to be removed)
                  </button> */}
                  <button
                     type="button"
                     className="text-white focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-red-800"
                     onClick={() => logout()}
                  >
                     Logout
                  </button>
               </div>
            </div>
            <div>
               <Outlet />
            </div>
         </div>
      </div>
   )
}
