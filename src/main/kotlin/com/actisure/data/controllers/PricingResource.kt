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
/*@RestController
class MessageResource(val service: MessageService, val messageRepository: MessageRepository) {
    @GetMapping("/getpricing/{COMPONENTID}")
    fun getMessageById(@PathVariable COMPONENTID: String): ResponseEntity<List<Map<String, Any?>>> {
        val messages = messageRepository.findAllByCOMPONENTID(COMPONENTID)
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build()
        }

        val response = messages.map { message ->
            mapOf(
                "componenttypeid" to message.COMPONENTTYPEID,
                "componentid" to message.COMPONENTID,
                "description" to message.DESCRIPTION,
                "priceDetails" to mapOf(
                    "agefrom" to message.AGEFROM,
                    "ageto" to message.AGETO,
                    "principle_CHILD" to message.PRINCIPLE_CHILD,
                    "spouserate" to message.SPOUSERATE
                )
            )
        }

        return ResponseEntity.ok(response)
    }
}*/
//fun index(): List<Message> = service.findMessages()
/*fun getMessageById(@PathVariable COMPONENTID: String): ResponseEntity<Message> {
    val message = messageRepository.findById(COMPONENTID).orElse(null)
        ?: return ResponseEntity.notFound().build()

    return ResponseEntity.ok(message)
}

fun index(): List<Message> = service.findMessages()
*/

/*  @PostMapping("/")
  fun post(@RequestBody message: Message) {
      service.post(message)
  }*/
