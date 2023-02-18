package com.actisure.data.benefitcomponents

data class BenefitComponent(
    val componenttypeid: Int,
    val componentid: Int,
    val description: String,
    val priceDetails: List<PriceDetails>
)