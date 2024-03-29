package com.actisure.data.controllers
import com.actisure.data.controllers.details.RenewalList
import com.actisure.data.repositories.RenewalListRepository
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RenewalListService (val renewal: RenewalListRepository) {
        @GetMapping("/renewallist")
        @ApiOperation(value = "Get the List of all Policies that are due to renew between last Month and next month (A span of 1 month)", notes = "This endpoint returns all Policies that were/are due to renew in a span of 1 month")
        fun index(): List<RenewalList> {
                return renewal.retrieveRenewalList()
        }
}