package it.polito.wa2.server.purchase

import it.polito.wa2.server.security.aut.getUserDetail
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PurchaseController(
    private val purchaseService: PurchaseService
) {

    @GetMapping("/purchases")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<PurchaseDTO> {
        return purchaseService.getAll(getUserDetail(user))
    }

    // useless?
    @GetMapping("/purchases/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): List<PurchaseDTO> {
        return purchaseService.getAllByEmail(email, getUserDetail(user))
    }

    @PostMapping("/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPurchase(@Valid @RequestBody purchaseDTO: PurchaseDTO, @AuthenticationPrincipal user: DefaultOAuth2User?): PurchaseDTO {
        return purchaseService.addPurchase(purchaseDTO, getUserDetail(user))
    }
}