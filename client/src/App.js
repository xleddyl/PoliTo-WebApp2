import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home, Signup, Ticket, Dashboard } from './screens'
import Layout from './screens/Layout'
import { useState } from 'react'

export default function App() {
   const [user, setUser] = useState(undefined)

   return (
      <BrowserRouter>
         <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/dashboard" element={<Layout user={user} setUser={setUser} />}>
               <Route index element={<Dashboard user={user} />} />
               <Route path="/dashboard/tickets" element={<Ticket user={user} />} />
               <Route path="/dashboard/tickets/:id" element={<Ticket user={user} />} />
            </Route>
         </Routes>
      </BrowserRouter>
   )
}
