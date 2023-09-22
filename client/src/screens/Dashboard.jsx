import Customer from '../components/Customer'
import Manager from '../components/Manager'
import Technician from '../components/Technician'

export default function Dashboard({ user }) {
   return (
      <>
         {user && user.role === 'CUSTOMER' && <Customer user={user} />}
         {user && user.role === 'MANAGER' && <Manager user={user} />}
         {user && user.role === 'TECHNICIAN' && <Technician user={user} />}
      </>
   )
}
