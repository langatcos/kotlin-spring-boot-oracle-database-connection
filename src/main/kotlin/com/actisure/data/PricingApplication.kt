package com.actisure.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EnableJdbcRepositories
@SpringBootApplication

class PricingApplication
fun main(args: Array<String>) {
	runApplication<PricingApplication>(*args)
}

