package com.example.springaidemo.controller

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val chatModel: OpenAiChatModel,
    private val chatClientBuilder: ChatClient.Builder
) {

    @GetMapping("/test-ai")
    fun testAi(@RequestParam(defaultValue = "Hello Gemini!") prompt: String): String? {
        val generation = chatModel.call(Prompt(prompt))
        return generation.result.output.text
    }

    private val chatClient: ChatClient = chatClientBuilder.build()

    @GetMapping("/test-client")
    fun testAiChatClient(@RequestParam(defaultValue = "Hello Gemini!") prompt: String): String? {
        val result = chatClient.prompt(Prompt(prompt)).call()
        return result.content()
    }

}