package com.intern.aifortodo.ui.Message

data class Message(
    val content: String,
    val isFromAI: Boolean // true 表示 AI 消息，false 表示用户消息
)
