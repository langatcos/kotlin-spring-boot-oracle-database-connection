package com.actisure.data.controllers.messages

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("RenewalList")
data class RenewalList(
    @Id val policynumber: String?,
    val principalname: String?,
    val renewaldate: Date?,
    val plan : String?,
    val agentname: String?,
    val policyamount: Float?,
    val claims: Float?,
    val email: String?,
    val mobile: String?
)
