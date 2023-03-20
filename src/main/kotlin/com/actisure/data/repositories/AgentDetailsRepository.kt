package com.actisure.data.repositories

import com.actisure.data.controllers.details.AgentDetails
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface AgentDetailsRepository: CrudRepository<AgentDetails, String> {

    @Query("select e.entityid," +
            "info as branch, " +
            "firstname," +
            "surname " +
            "from entityroleinfo i, entity e " +
            "where infoid=1423 and i.entityid=e.entityid and e.entityid= :entityid")
    fun getAgentDetailsByEntityid(entityid:Int):List<AgentDetails>
    override fun findAll(): List<AgentDetails>
}