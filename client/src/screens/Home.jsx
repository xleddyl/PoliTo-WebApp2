import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

export default function Home() {
   const navigate = useNavigate()

   useEffect(() => {
      const fun = async () => {
         const res = await fetch('/api/user', { method: 'GET' })
         if (!res.ok) return
         const user = await res.json()
         if (user.role === 'NO_AUTH') return
         localStorage.setItem('user', JSON.stringify(user))
         navigate('/dashboard', { replace: true })
      }
      fun()
   }, [])

   return (
      <div className=" bg-gray-900 h-screen flex flex-col items-center p-10 gap-4">
         <div className="text-3xl pb-24 text-white font-medium">Ticketing app</div>
         <button
            type="button"
            className="w-52 text-white focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
            onClick={() => (window.location.href = 'http://localhost:8081/login')}
         >
            Login
         </button>
         <button
            type="button"
            className="w-52 text-white focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
            onClick={() => navigate('/signup')}
         >
            Signup
         </button>
      </div>
   )
}
