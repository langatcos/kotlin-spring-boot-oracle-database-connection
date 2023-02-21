package com.actisure.data.controllers.messages

import org.springframework.data.relational.core.mapping.Table
import javax.persistence.Id

@Table("Agents")
data class AgentDetails(
    @Id val entityid: Int?,
    val firstname:String,
    val surname:String,
    val branch:String?
)
