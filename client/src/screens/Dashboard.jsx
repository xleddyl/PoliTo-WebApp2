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
   }, [])

   const logout = async () => {
      await fetch('/logout', { method: 'GET', mode: 'no-cors' })
      localStorage.clear('user')
      navigate('/', { replace: true })
   }

   return (
      <div className="bg-gray-50 dark:bg-gray-900 h-screen p-10">
         {user && user.role === 'CUSTOMER' && <Customer />}
         {user && user.role === 'MANAGER' && <Manager />}
         {user && user.role === 'TECHNICIAN' && <Technician />}

         <button
            type="button"
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
            onClick={() => logout()}
         >
            Logout
         </button>
      </div>
   )
}
