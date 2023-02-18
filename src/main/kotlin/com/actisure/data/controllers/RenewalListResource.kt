package com.actisure.data.controllers
import com.actisure.data.controllers.messages.RenewalList
import com.actisure.data.services.RenewalListService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RenewalListResource (val renewal: RenewalListService) {
        @GetMapping("/renewallist")
        fun index(): List<RenewalList> = renewal.listAllPolicies()
}