package com.intern.aifortodo.ui.ai

data class QueryRequest(
    val tasks: String,
    val query: String
)

data class AiResponse(
    val tool: String,
    val reasoning: String,
    val response: String
)