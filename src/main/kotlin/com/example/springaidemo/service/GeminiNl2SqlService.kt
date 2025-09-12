package com.example.springaidemo.service

import com.example.springaidemo.dto.Nl2SqlRequest
import com.example.springaidemo.dto.Nl2SqlResponse
import com.example.springaidemo.repository.PromptRepository
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

@Service
class GeminiNl2SqlService(
    private val chatModel: OpenAiChatModel,
    private val promptRepository: PromptRepository
) {

    /**
     * Convert natural language to SQL by injecting the user request into the prompt template
     * and calling the Gemini chat model.
     */
    fun toSql(request: Nl2SqlRequest): Nl2SqlResponse {
        // load prompt template (e.g., "Convert this natural language request into SQL: {user_request}")
        val templateRaw = promptRepository.loadTemplate()
        val promptWithUser = templateRaw.replace("{user_request}", request.naturalQuery)

        // Build system + user messages
        val systemMessage = SystemMessage("You are an AI specialized in generating PostgreSQL queries. Only return SQL without explanations.")
        val userMessage = UserMessage(promptWithUser)
       val option =  OpenAiChatOptions.builder()
            .model("gemini-1.5-pro")
            .temperature(0.0) // deterministic
            .topP(1.0)
            .maxTokens(1024)
            .build()
        // Wrap into Prompt and call the model
        val prompt = Prompt(listOf(systemMessage, userMessage),option )
        val response: ChatResponse = chatModel.call(prompt)

        // Extract plain text output
        val sql = response.result.output.text!!.trim()

        return Nl2SqlResponse(sql = sql)
    }

}
