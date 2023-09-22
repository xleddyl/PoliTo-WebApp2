package it.polito.wa2.server.purchase

import it.polito.wa2.server.security.aut.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val authService: AuthService
) {

    @GetMapping("/purchases")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<PurchaseDTO> {
        return purchaseService.getAll(authService.getUserDetails(user))
    }

    @PostMapping("/purchases/list")
    @ResponseStatus(HttpStatus.OK)
    fun getAllByListOfID(
        @Valid @RequestBody list: List<Long>,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): List<PurchaseDTO> {
        return purchaseService.getAllByListOfID(list, authService.getUserDetails(user))
    }

    @GetMapping("/purchases/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): List<PurchaseDTO> {
        return purchaseService.getAllByEmail(email, authService.getUserDetails(user))
    }

    @PostMapping("/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPurchase(
        @Valid @RequestBody purchaseDTO: PurchaseDTO,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): PurchaseDTO {
        return purchaseService.addPurchase(purchaseDTO, authService.getUserDetails(user))
    }

    @PutMapping("/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    fun editPurchase(
        @Valid @RequestBody purchaseDTO: PurchaseDTO,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): PurchaseDTO {
        return purchaseService.editPurchase(purchaseDTO, authService.getUserDetails(user))
    }
}