//package com.intern.androidtodometer.ui.ai
//
//import AiService
//
//class AiRepository(private val aiService: AiService = RetrofitInstance.aiService) {
//
//    suspend fun getSummary(query: String, tasks: String): Result<String> {
//        return try {
//            val request = QueryRequest(tasks = tasks, query = query)
//            val response = aiService.getSummary(request).execute()
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Result.success(it.response)
//                } ?: Result.failure(Exception("响应数据为空"))
//            } else {
//                Result.failure(Exception("获取摘要失败: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun askQuestion(query: String, tasks: String): Result<String> {
//        return try {
//            val request = QueryRequest(tasks = tasks, query = query)
//            val response = aiService.askQuestion(request).execute()
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Result.success(it.response)
//                } ?: Result.failure(Exception("响应数据为空"))
//            } else {
//                Result.failure(Exception("提问失败: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}
