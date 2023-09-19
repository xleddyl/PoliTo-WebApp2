import { useState } from 'react'

export default function Chat({ messages, sendMessage }) {
   const [message, setMessage] = useState('')
   const [file, setFile] = useState(undefined)

   return (
      <div className="mx-auto">
         {messages &&
            messages.map((m) => (
               <div
                  key={m.id}
                  className={
                     'p-4 mb-4 text-sm rounded-lg bg-gray-800 text-blue-400 ' +
                     (m.fromCustomer ? 'text-end' : 'text-start')
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
               className="w-52 text-white focus:ring-4 font-medium rounded-lg text-sm bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-blue-800"
               onClick={() => {
                  sendMessage(message, file)
                  setMessage('')
                  setFile(undefined)
               }}
            >
               Invia
            </button>
            <input type="file" className="text-white" onChange={(e) => setFile(e.target.files[0])} />
         </div>
      </div>
   )
}
