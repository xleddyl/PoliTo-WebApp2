import { useNavigate } from 'react-router-dom'

export default ApiTest = ({
   setOpen,
   changeMethod,
   setRoute,
   body,
   open,
   method,
   setBody,
   httpMethods,
   makeRequest,
   response,
   error,
}) => {
   const navigate = useNavigate()

   return (
      <>
         <div className="flex flex-row">
            <button
               type="submit"
               className="w-full bg-gray-600 rounded-md px-5 py-2 m-3 text-white font-medium"
               onClick={() => (window.location.href = 'http://localhost:8081/login')}
            >
               Login
            </button>
            <button
               type="submit"
               className="w-full bg-gray-600 rounded-md px-5 py-2 m-3 text-white font-medium"
               onClick={(e) => navigate('/signup')}
            >
               Register
            </button>
            <button
               type="submit"
               className="w-full bg-gray-600 rounded-md px-5 py-2 m-3 text-white font-medium"
               onClick={() => (window.location.href = 'http://localhost:8081/logout')}
            >
               Logout
            </button>
         </div>
         <div className="flex justify-center items-center h-80 w-screen">
            <div className="flex-col">
               <div className="inline-flex">
                  <div id="method-selector">
                     <button
                        type="button"
                        onClick={(e) => setOpen(!open)}
                        className={`text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg
                        text-sm px-4 py-2.5 w-24 max-w-sm text-center inline-flex items-center active:ring-2
                        active:outline-none active:ring-blue-400 ring-inset mr-2`}
                     >
                        {method}
                     </button>
                     <div
                        id="dropdown"
                        className={`${
                           !open && 'hidden'
                        } mt-2 bg-blue-500 divide-y divide-gray-100 rounded-lg shadow w-24 max-w-sm`}
                     >
                        <ul className="py-2 text-sm text-gray-700 dark:text-gray-200">
                           {httpMethods
                              .filter((m) => m !== method)
                              .map((m) => (
                                 <li key={m}>
                                    <button
                                       className="w-full px-4 py-2 hover:bg-blue-600 text-left"
                                       onClick={() => changeMethod(m)}
                                    >
                                       {m}
                                    </button>
                                 </li>
                              ))}
                        </ul>
                     </div>
                  </div>
                  <div id="route-and-body">
                     <input
                        type="text"
                        className="w-80 rounded-md bg-white px-4 py-2.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                        placeholder="specify a route"
                        onChange={(e) => setRoute(e.target.value)}
                     />
                     {(method === 'POST' || method === 'PUT') && (
                        <div>
                           <textarea
                              className="w-80 h-40 mt-2 resize-none rounded-md bg-white px-4 py-2.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                              placeholder="specify a body (JSON)"
                              onChange={(e) => setBody(e.target.value)}
                              value={body}
                           />
                        </div>
                     )}
                  </div>
                  <div>
                     <button
                        type="button"
                        onClick={makeRequest}
                        className={`text-white ring-2 ring-green-600 hover:bg-green-200 font-medium rounded-lg
                        text-sm px-4 py-2.5 w-auto text-center inline-flex items-center focus:ring-2
                        focus:outline-none active:ring-green-500 ml-5`}
                     >
                        <svg
                           fill="none"
                           stroke="#289550"
                           strokeWidth="2"
                           viewBox="0 0 24 24"
                           xmlns="http://www.w3.org/2000/svg"
                           className="w-5"
                        >
                           <path
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              d="M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5"
                           ></path>
                        </svg>
                     </button>
                  </div>
               </div>
            </div>
         </div>
         <div className="flex justify-center items-center">
            {response && (
               <textarea
                  className="w-96 h-40 mt-2 resize-none rounded-md bg-white px-4 py-2.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                  defaultValue={JSON.stringify(response, null, 3)}
               />
            )}
            {error && <div className="mt-1 font-semibold text-red-600 w-96 whitespace-pre-wrap">{error}</div>}
         </div>
      </>
   )
}
