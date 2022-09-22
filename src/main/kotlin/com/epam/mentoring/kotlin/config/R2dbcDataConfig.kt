package com.epam.mentoring.kotlin.config

import com.epam.mentoring.kotlin.data.model.converter.StringListOfBreedReadConverter
import com.epam.mentoring.kotlin.data.model.converter.StringListOfBreedWriteConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect

@Configuration
class R2dbcDataConfig {

    @Bean
    fun customConversions(): R2dbcCustomConversions {
        val converters: MutableList<Converter<*, *>> = ArrayList()
        converters.add(StringListOfBreedReadConverter())
        converters.add(StringListOfBreedWriteConverter())
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters)
    }
}