package com.actisure.data.services

import com.actisure.data.controllers.messages.AgentDetails
import com.actisure.data.repositories.AgentDetailsRepository
import org.springframework.stereotype.Service

@Service
class AgentDetailsService(val db:AgentDetailsRepository) {
    fun listagentDetails(): List<AgentDetails> {
        return db.findAll()
    }
}