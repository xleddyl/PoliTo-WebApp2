import { Customer, Manager, Technician } from '../components'

export default function Dashboard({ user }) {
   return (
      <>
         {user && user.role === 'CUSTOMER' && <Customer user={user} />}
         {user && user.role === 'MANAGER' && <Manager user={user} />}
         {user && user.role === 'TECHNICIAN' && <Technician user={user} />}
      </>
   )
}
