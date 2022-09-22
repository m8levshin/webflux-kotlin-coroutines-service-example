package com.epam.mentoring.kotlin

import com.epam.mentoring.kotlin.data.RepoFiller
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class KotlinApplicationTests {

    @MockBean
    lateinit var repoFiller: RepoFiller

    @Test
    fun contextLoads() {
    }

}
