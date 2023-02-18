package com.actisure.data.repositories

    import com.actisure.data.controllers.messages.Pricing
    import org.springframework.data.jdbc.repository.query.Query
    import org.springframework.data.repository.CrudRepository
    import java.util.*

interface PricingRepository : CrudRepository<Pricing, String>{



    @Query("select COMPONENTID," +
            "COMPONENTTYPEID," +
            "DESCRIPTION," +
            "AGEFROM," +
            "AGETO,PRINCIPLE_CHILD," +
            "SPOUSERATE " +
            "from BASE_RATES " +
            "ORDER BY AGEFROM ASC")
        fun findMessages(): List<Pricing>
        fun findAllByCOMPONENTID(COMPONENTID: String): List<Pricing>


    }