package com.actisure.data.services
import com.actisure.data.controllers.messages.RenewalList
import com.actisure.data.repositories.RenewalListRepository
import org.springframework.stereotype.Service

@Service
class RenewalListService(val rn: RenewalListRepository) {
    fun listAllPolicies(): List<RenewalList> {
        return rn.retrieveRenewalList()

    }


// fun findMessageById(id: String): Message? = db.findById(id).orElse(null)
}