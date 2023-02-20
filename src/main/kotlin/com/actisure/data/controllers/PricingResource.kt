package com.actisure.data.controllers

import com.actisure.data.repositories.PricingRepository
import com.actisure.data.benefitcomponents.BenefitComponent
import com.actisure.data.benefitcomponents.PriceDetails
import com.actisure.data.controllers.messages.Pricing
import com.actisure.data.controllers.messages.RenewalList
import com.actisure.data.services.PricingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//start of Controller
@RestController
class PricingResource(val messageRepository: PricingRepository) {
    @GetMapping("/getpricing/{componentid}")
    fun getComponent(@PathVariable componentid: String): ResponseEntity<List<BenefitComponent>> {
        val messages = messageRepository.findAllByCOMPONENTID(componentid)
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val components = messages.groupBy { it.COMPONENTID }.map {
            val (componentId, groupMessages) = it
            BenefitComponent(
                groupMessages[0].COMPONENTTYPEID,
                componentId,
                groupMessages[0].DESCRIPTION,
                groupMessages.map { PriceDetails(it.AGEFROM, it.AGETO, it.PRINCIPLE_CHILD, it.SPOUSERATE) }
            )
        }

        return ResponseEntity.ok(components)
    }
    @GetMapping("/getallpricing")
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
