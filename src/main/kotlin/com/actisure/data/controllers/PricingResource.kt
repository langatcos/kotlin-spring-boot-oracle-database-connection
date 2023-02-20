package com.actisure.data.controllers

import com.actisure.data.repositories.PricingRepository
import com.actisure.data.benefitcomponents.BenefitComponent
import com.actisure.data.benefitcomponents.PriceDetails
import com.actisure.data.controllers.messages.Pricing
import com.actisure.data.controllers.messages.RenewalList
import com.actisure.data.services.PricingService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//start of Controller
@RestController
class PricingResource(val messageRepository: PricingRepository) {
    @GetMapping("/getpricing/{componentid}")
    @ApiOperation(value = "Get the Pricing Values by (Benefit ID)", notes = "This endpoint returns all the Pricing per benefit- All you need to supply is the benefit ID")
    fun getComponent(@PathVariable componentid: String): ResponseEntity<List<BenefitComponent>> {

        return messageRepository.findAllByCOMPONENTID(componentid).run {
            if (isEmpty()) {
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.ok(groupBy { it.COMPONENTID }.map {
                    val (componentId, groupList) = it
                    BenefitComponent(
                        groupList[0].COMPONENTTYPEID,
                        componentId,
                        groupList[0].DESCRIPTION,
                        groupList.map { PriceDetails(it.AGEFROM, it.AGETO, it.PRINCIPLE_CHILD, it.SPOUSERATE) }
                    )
                })
            }
        }
    }

    @GetMapping("/getallpricing")
    @ApiOperation(value = "Get the Pricing Values for all Applicable Benefits", notes = "This endpoint returns all the Pricing per benefit")

    fun index(): List<Map<String, Any>> {
        val pricingList = messageRepository.findMessages()
        val groupedPricing = pricingList.groupBy { it.COMPONENTID }
        val result = mutableListOf<Map<String, Any>>()
        groupedPricing.forEach { (componentid, pricings) ->
            val priceDetails = pricings.map {
                mapOf(
                    "agefrom" to it.AGEFROM,
                    "ageto" to it.AGETO,
                    "principleOrChildRate" to it.PRINCIPLE_CHILD,
                    "spouserate" to it.SPOUSERATE
                )
            }

            val pricingMap = mapOf(
                "componenttypeid" to pricings.first().COMPONENTID,
                "componentid" to componentid,
                "description" to pricings.first().DESCRIPTION,
                "priceDetails" to priceDetails
            )

            result.add(pricingMap)
        }

        return result
    }

}
