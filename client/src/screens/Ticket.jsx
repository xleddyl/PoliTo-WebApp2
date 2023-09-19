import { useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

export default function Ticket() {
   const { id } = useParams()
   const navigate = useNavigate()

   useEffect(() => {
      if (!id) navigate('/', { replace: true })
   }, [])

   if (!id) return null

   return <div>Ticket {id}</div>
}
