package com.example.book.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.book.viewmodel.BookViewModel
import com.example.book.model.Chapter

@Composable
fun BookDetailScreen(bookId: String, viewModel: BookViewModel = viewModel()) {
    val book = viewModel.getBookById(bookId)

    if (book != null) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
                Text(text = "Tác giả: ${book.author}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = book.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Danh sách chương", style = MaterialTheme.typography.titleLarge)
            }

            items(book.chapters) { chapter ->
                ChapterItem(chapter)
            }
        }
    } else {
        Text("Không tìm thấy sách.")
    }
}


@Composable
fun ChapterItem(chapter: Chapter) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = chapter.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chapter.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
