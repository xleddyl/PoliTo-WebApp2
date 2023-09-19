import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home, Signup, Ticket, Dashboard } from './screens'

export default function App() {
   return (
      <BrowserRouter>
         <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/dashboard/tickets" element={<Ticket />} />
            <Route path="/dashboard/tickets/:id" element={<Ticket />} />
         </Routes>
      </BrowserRouter>
   )
}
