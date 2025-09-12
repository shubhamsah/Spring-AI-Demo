package com.example.springaidemo.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.core.io.Resource
import java.nio.charset.StandardCharsets

@Component
class PromptRepository(
    @Value("classpath:nl2sql.sql.tpl")
    private val nl2sqlTemplate: Resource
) {
    fun loadTemplate(): String {
        return nl2sqlTemplate.inputStream.readAllBytes().toString(StandardCharsets.UTF_8)
    }
}