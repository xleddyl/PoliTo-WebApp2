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
         navigate('/dashboard')
      }
      fun()
   }, [])

   return (
      <div>
         <button
            type="button"
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
            onClick={() => (window.location.href = 'http://localhost:8081/login')}
         >
            Login
         </button>
         <button
            type="button"
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
            onClick={() => navigate('/signup')}
         >
            Signup
         </button>
      </div>
   )
}
