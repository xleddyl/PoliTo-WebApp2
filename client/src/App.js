import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home, Signup, Ticket, Dashboard } from './screens'

export default function App() {
   return (
      <BrowserRouter>
         <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/dashboard/ticket" element={<Ticket />} />
         </Routes>
      </BrowserRouter>
   )
}
