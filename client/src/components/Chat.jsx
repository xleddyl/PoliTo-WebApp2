import { useState } from 'react'

export default function Chat({ messages, sendMessage, technician, user }) {
   const [message, setMessage] = useState('')
   const [file, setFile] = useState(undefined)

   if (!technician)
      return (
         <div className="mx-auto">
            <div className="text-white text-lg font-medium pb-2">Not yet assigned to a technician</div>
         </div>
      )

   return (
      <div className="mx-auto">
         {messages &&
            messages.map((m) => (
               <div
                  key={m.id}
                  className={
                     'p-4 mb-4 text-sm rounded-lg bg-gray-800 text-blue-400 ' +
                     ((user.role === 'TECHNICIAN' && !m.fromCustomer) ? 'text-end ' : 'text-start ') +
                     ((user.role !== 'TECHNICIAN' && m.fromCustomer) ? 'text-end ' : 'text-start ')
                  }
                  role="alert"
               >
                  {m.content}
               </div>
            ))}
         <div className="flex flex-row gap-3">
            <input
               type="text"
               name="text"
               className="border sm:text-sm rounded-lg block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500"
               placeholder="write something"
               required
               onChange={(e) => setMessage(e.target.value)}
               value={message}
            />
            <button
               className="py-3 px-5 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
               onClick={() => {
                  sendMessage(message, file)
                  setMessage('')
                  setFile(undefined)
               }}
            >
               Invia
            </button>
            <input type="file" className="text-white self-center" onChange={(e) => setFile(e.target.files[0])} />
         </div>
      </div>
   )
}
