package com.epam.mentoring.kotlin.data.model.converter

import com.epam.mentoring.kotlin.data.model.SubBreed
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class StringListOfBreedReadConverter : Converter<String, List<SubBreed>> {
    override fun convert(source: String): List<SubBreed> {
        if (source.trim().isEmpty()) return emptyList()
        return source.split(',').map { it.trim() }.map { SubBreed(it) }
    }
}

@Component
@WritingConverter
class StringListOfBreedWriteConverter : Converter<List<SubBreed>, String> {
    override fun convert(source: List<SubBreed>): String {
        return source.joinToString { it.name }
    }

}