package com.example.book.network

import com.example.book.model.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: String): Book

    @POST("books")
    suspend fun addBook(@Body book: Book): Response<Unit>
}
