package com.actisure.data.controllers.messages

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

//Start of data Class
@Table("BASE_RATES")
data class Pricing(
    @Id val COMPONENTID: Int,
    val COMPONENTTYPEID: Int,
    val DESCRIPTION: String,
    val AGEFROM: Int,
    val AGETO: Int,
    val PRINCIPLE_CHILD: Float,
    val SPOUSERATE: Float,

    )


//COMPONENTTYPEID,COMPONENTID,DESCRIPTION,AGEFROM,AGETO,PRINCIPLE_CHILD,SPOUSERATE