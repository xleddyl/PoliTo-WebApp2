package it.polito.wa2.server.profiles

import it.polito.wa2.server.BadRequestException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.manager.ManagerDTO
import it.polito.wa2.server.profiles.manager.ManagerService
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import it.polito.wa2.server.purchase.PurchaseDTO
import it.polito.wa2.server.security.aut.getUserDetail
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import it.polito.wa2.server.ticketing.tickets.TicketService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProfileController(
    private val customerService: CustomerService,
    private val technicianService: TechnicianService,
    private val managerService: ManagerService,
    private val ticketService: TicketService,
) {


    @GetMapping("/profiles")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<Any> {
        val userDetail = getUserDetail(user)
        return customerService.getAll(userDetail) + technicianService.getAll(userDetail) + managerService.getAll(
            userDetail
        )
    }

    @GetMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): Any? {
        val userDetail = getUserDetail(user)
        return try {
            customerService.getByEmail(email, userDetail)
        } catch (e: Exception) {
            if (e !is UnauthorizedException) {
                try {
                    technicianService.getByEmail(email, userDetail)
                } catch (e: Exception) {
                    if (e !is UnauthorizedException) {
                        try {
                            managerService.getByEmail(email, userDetail)
                        } catch (e: Exception) {
                            throw e
                        }
                    } else {
                        throw e
                    }
                }
            } else {
                throw e
            }
        }
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@RequestBody profileDTO: Any, @AuthenticationPrincipal user: DefaultOAuth2User?): Any {
        val userDetail = getUserDetail(user)
        return when (profileDTO) {
            is CustomerDTO -> customerService.addProfile(profileDTO, userDetail)
            is TechnicianDTO -> technicianService.addProfile(profileDTO, userDetail)
            is ManagerDTO -> managerService.addProfile(profileDTO, userDetail)
            else -> throw BadRequestException("Bad Request")
        }
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(
        @RequestBody profileDTO: Any,
        @PathVariable email: String,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): Any {
        val userDetail = getUserDetail(user)
        return when (profileDTO) {
            is CustomerDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                customerService.editProfile(profileDTO, userDetail)
            }

            is TechnicianDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                technicianService.editProfile(profileDTO, userDetail)
            }

            is ManagerDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                managerService.editProfile(profileDTO, userDetail)
            }

            else -> throw BadRequestException("Bad Request")
        }
    }

    @GetMapping("/profiles/{email}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getTickets(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): List<TicketDTO> {
        val userDetail = getUserDetail(user)
        return when (userDetail.role) {
            UserRoles.CUSTOMER -> {
                ticketService.getAll(userDetail)
            }

            UserRoles.TECHNICIAN -> {
                technicianService.getTickets(email, userDetail)
            }

            UserRoles.MANAGER -> {
                ticketService.getAll(userDetail)
            }

            else -> {
                throw UnauthorizedException("Unauthorized")
            }
        }
    }

    // CUSTOMER

    @GetMapping("/profiles/{email}/purchases")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerPurchases(
        @PathVariable email: String,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): List<PurchaseDTO> {
        return customerService.getPurchases(email, getUserDetail(user))
    }

    // TECHNICIAN

    @GetMapping("/profiles/pending")
    @ResponseStatus(HttpStatus.OK)
    fun getTechniciansPending(@AuthenticationPrincipal user: DefaultOAuth2User?): List<TechnicianDTO> {
        return technicianService.getAllPending(getUserDetail(user))
    }

    @GetMapping("/profiles/{email}/manager")
    @ResponseStatus(HttpStatus.OK)
    fun getTechnicianManager(
        @PathVariable email: String,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): ManagerDTO {
        return technicianService.getManager(email, getUserDetail(user))
    }


    // MANAGER

    @GetMapping("/profiles/{email}/technicians")
    @ResponseStatus(HttpStatus.OK)
    fun getManagerTechnicians(
        @PathVariable email: String,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): List<TechnicianDTO> {
        return managerService.getTechnicians(email, getUserDetail(user))
    }
}