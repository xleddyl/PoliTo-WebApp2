import { useState } from 'react'
import { callAPI } from '../api/API'
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
         let response = await callAPI(
            'POST',
            '/signup',
            { username, email, firstName, lastName, password, phone, address },
            '/api',
         )
         console.log(response)
         setResponse('success: ' + response)

         navigate('/')
      } catch (e) {
         console.log(e)
         setError('Something bad happened: ' + e)
      }
   }

   return (
      <>
         <section className="bg-gray-50 dark:bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-24 mx-auto">
               <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                  <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                     <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Create an account
                     </h1>
                     <form className="space-y-4 md:space-y-6" action="#">
                        <div>
                           <label
                              htmlFor="text"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your Name
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="firstName"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="First Name"
                              required
                              onChange={(e) => setFirstName(e.target.value)}
                              value={firstName || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="text"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your Surname
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="lastName"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="Last Name"
                              required
                              onChange={(e) => setLastName(e.target.value)}
                              value={lastName || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="text"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your Username
                           </label>
                           <input
                              type="text"
                              name="text"
                              id="userName"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="User Name"
                              required
                              onChange={(e) => setUsername(e.target.value)}
                              value={username || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="text"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your Phone Number
                           </label>
                           <input
                              type="text"
                              name="phone"
                              id="phone"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="333 0000000"
                              required
                              onChange={(e) => setPhone(e.target.value)}
                              value={phone || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="text"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your Address
                           </label>
                           <input
                              type="text"
                              name="address"
                              id="address"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="address"
                              required
                              onChange={(e) => setAddress(e.target.value)}
                              value={address || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="email"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Your email
                           </label>
                           <input
                              type="email"
                              name="email"
                              id="email"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="name@company.com"
                              required
                              onChange={(e) => setEmail(e.target.value)}
                              value={email || ''}
                           />
                        </div>

                        <div>
                           <label
                              htmlFor="password"
                              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                           >
                              Password
                           </label>
                           <input
                              type="password"
                              name="password"
                              id="password"
                              placeholder="••••••••"
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
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
                           className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
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
