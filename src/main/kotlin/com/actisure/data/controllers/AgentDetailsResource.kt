package com.actisure.data.controllers

import com.actisure.data.benefitcomponents.BenefitComponent
import com.actisure.data.controllers.messages.AgentDetails
import com.actisure.data.controllers.messages.RenewalList
import com.actisure.data.repositories.AgentDetailsRepository
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AgentDetailsResource(val agent: AgentDetailsRepository) {
    @GetMapping("/getagentdetails/{entityid}")
        @ApiOperation(value = "Gat The Branch name by Agent Entity ID", notes = "This endpoint returns the details of the branch by Entity ID")
    fun getAgentDetails(@PathVariable entityid: Int): ResponseEntity<List<AgentDetails>> {
        val agentDetails = agent.getAgentDetailsByEntityid(entityid)
        return if (agentDetails != null) {
            ResponseEntity.ok(agentDetails)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    /*fun index(): List<AgentDetails> {
        return agent.findAll()
    }*/
}