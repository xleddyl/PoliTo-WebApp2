package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.products.ProductService
import it.polito.wa2.server.profiles.ProfileService
import it.polito.wa2.server.ticketing.messages.Message
import it.polito.wa2.server.ticketing.messages.MessageService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class TicketServiceImpl(
        private val ticketRepository: TicketRepository,
        private val profileService: ProfileService,
        private val productService: ProductService,
        private val messageService: MessageService
) : TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ticketId: Long): TicketDTO {
        return ticketRepository.findByIdOrNull(ticketId)?.toDTO() ?: throw NotFoundException("Ticket not found")
    }

    override fun createTicket(ticketDTO: TicketDTO): TicketDTO {
        if (ticketDTO.id != null && ticketRepository.findByIdOrNull(ticketDTO.id) != null) throw DuplicateException("Ticket id already exists")
        val customer = profileService.getByEmail(ticketDTO.customer.email)
        val product = productService.getById(ticketDTO.product.ean)
        val messages = mutableSetOf<Message>()
        ticketDTO.messages?.forEach { messages.add(it.fromDTO()) }
        return ticketRepository.save(
                Ticket(
                        id = ticketDTO.id,
                        product = product.fromDTO(),
                        customer = customer.fromDTO(),
                        technician = ticketDTO.technician?.fromDTO(),
                        statuses = ticketDTO.statuses,
                        description = ticketDTO.description,
                        priority = ticketDTO.priority,
                        messages = messages
                )
        ).toDTO()
    }

    override fun editTicket(ticketId: Long, ticketDTO: TicketDTO): TicketDTO {
        if (ticketRepository.findByIdOrNull(ticketDTO.id) == null) throw NotFoundException("Ticket not found")
        val customer = profileService.getByEmail(ticketDTO.customer.email)
        val product = productService.getById(ticketDTO.product.ean)
        val messages = mutableSetOf<Message>()
        ticketDTO.messages?.forEach { messages.add(it.fromDTO()) }
        return ticketRepository.save(
                Ticket(
                        id = ticketId,
                        product = product.fromDTO(),
                        customer = customer.fromDTO(),
                        technician = ticketDTO.technician?.fromDTO(),
                        statuses = ticketDTO.statuses,
                        description = ticketDTO.description,
                        priority = ticketDTO.priority,
                        messages = messages
                )
        ).toDTO()
    }

}