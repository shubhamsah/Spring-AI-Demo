package com.example.springaidemo.controller

import com.example.springaidemo.dto.Nl2SqlRequest
import com.example.springaidemo.dto.Nl2SqlResponse
import com.example.springaidemo.service.GeminiNl2SqlService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nl2sql")
class Nl2SqlController(
    private val geminiNl2SqlService: GeminiNl2SqlService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun convert(@Valid @RequestBody req: Nl2SqlRequest): ResponseEntity<Nl2SqlResponse> {
        val resp = geminiNl2SqlService.toSql(req)
        return ResponseEntity.ok(resp)
    }
}