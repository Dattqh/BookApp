package com.example.book.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.Book
import com.example.book.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BookViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        viewModelScope.launch {
            try {
                val bookList = RetrofitClient.apiService.getBooks()
                _books.value = bookList
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error fetching books: ${e.message}")
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.addBook(book)
                if (response.isSuccessful) {
                    Log.d("BookViewModel", "Book added successfully")
                    fetchBooks() // Refresh list
                } else {
                    Log.e("BookViewModel", "Failed to add book: ${response.code()} - ${response.message()}")
                }
            } catch (e: HttpException) {
                Log.e("BookViewModel", "HttpException: ${e.message}")
            } catch (e: Exception) {
                Log.e("BookViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun getBookById(id: String): Book? {
        return books.value.find { it._id == id }
    }

}
