package com.example.springaidemo.dto

import jakarta.validation.constraints.NotBlank

data class Nl2SqlRequest(
    @field:NotBlank
    val naturalQuery: String,

    // Optional toggle to request EXPLAIN instead of just SQL
    val explain: Boolean = false
)