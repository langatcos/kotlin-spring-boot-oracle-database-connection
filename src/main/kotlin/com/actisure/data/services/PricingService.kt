package com.actisure.data.services
import com.actisure.data.controllers.messages.Pricing
import com.actisure.data.repositories.PricingRepository
import org.springframework.stereotype.Service
@Service

class PricingService(val db: PricingRepository) {
   fun findMessages(): List<Pricing> = db.findMessages()

    fun post(message: Pricing){
        db.save(message)
    }

   // fun findMessageById(id: String): Message? = db.findById(id).orElse(null)
}