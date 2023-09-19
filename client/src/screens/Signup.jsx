import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function Signup() {
   const [username, setUsername] = useState(undefined)
   const [password, setPassword] = useState(undefined)
   const [email, setEmail] = useState(undefined)
   const [firstName, setFirstName] = useState(undefined)
   const [lastName, setLastName] = useState(undefined)
   const [phone, setPhone] = useState(undefined)
   const [address, setAddress] = useState(undefined)
   const [response, setResponse] = useState('')
   const [error, setError] = useState('')

   const navigate = useNavigate()

   const signup = async (e, username, password) => {
      e.preventDefault()
      setResponse('')
      setError('')
      try {
         const res = fetch('/api/signup', {
            method: 'POST',
            body: JSON.stringify({ username, email, firstName, lastName, password, phone, address }),
            headers: {
               'Content-type': 'application/json',
            },
         })
         console.log(res)
         setResponse('success: ' + res)

         navigate('/')
      } catch (e) {
         console.log(e)
         setError('Something bad happened: ' + e)
      }
   }

   return (
      <>
         <section className="bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-24 mx-auto">
               <div className="w-full rounded-lg shadow border md:mt-0 sm:max-w-md xl:p-0 bg-gray-800 border-gray-700">
                  <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                     <h1 className="text-xl font-bold leading-tight tracking-tight md:text-2xl text-white">
                        Create an account
                     </h1>
                     <form className="space-y-4 md:space-y-6" action="#">
                        <div>
                           <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                              Your Name
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="firstName"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="First Name"
                              required
                              onChange={(e) => setFirstName(e.target.value)}
                              value={firstName || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                              Your Surname
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="lastName"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="Last Name"
                              required
                              onChange={(e) => setLastName(e.target.value)}
                              value={lastName || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                              Your Username
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="userName"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="User Name"
                              required
                              onChange={(e) => setUsername(e.target.value)}
                              value={username || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                              Your Phone Number
                           </label>
                           <input
                              type="text"
                              name="phone"
                              id="phone"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="333 0000000"
                              required
                              onChange={(e) => setPhone(e.target.value)}
                              value={phone || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="text" className="block mb-2 text-sm font-medium text-white">
                              Your Address
                           </label>
                           <input
                              type="text"
                              name="address"
                              id="address"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="address"
                              required
                              onChange={(e) => setAddress(e.target.value)}
                              value={address || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="email" className="block mb-2 text-sm font-medium text-white">
                              Your email
                           </label>
                           <input
                              type="email"
                              name="email"
                              id="email"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              placeholder="name@company.com"
                              required
                              onChange={(e) => setEmail(e.target.value)}
                              value={email || ''}
                           />
                        </div>

                        <div>
                           <label htmlFor="password" className="block mb-2 text-sm font-medium text-white">
                              Password
                           </label>
                           <input
                              type="password"
                              name="password"
                              id="password"
                              placeholder="••••••••"
                              className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
                              required
                              onChange={(e) => setPassword(e.target.value)}
                              value={password || ''}
                           />
                        </div>
                        <div className="flex justify-center items-center">
                           {response && (
                              <div className="mt-1 font-semibold text-green-600 w-96 whitespace-pre-wrap">
                                 {response}
                              </div>
                           )}
                           {error && (
                              <div className="mt-1 font-semibold text-red-600 w-96 whitespace-pre-wrap">{error}</div>
                           )}
                        </div>
                        <button
                           type="submit"
                           className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center bg-primary-600 hover:bg-primary-700 focus:ring-primary-800"
                           onClick={(e) => signup(e, username, password)}
                        >
                           Create an account
                        </button>
                     </form>
                  </div>
               </div>
            </div>
         </section>
      </>
   )
}
