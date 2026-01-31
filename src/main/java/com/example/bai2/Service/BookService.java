package com.example.bai2.Service;

import com.example.bai2.Model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Đánh dấu lớp này là một Service
public class BookService {

    // Khởi tạo danh sách sách để lưu trữ dữ liệu tạm thời
    private List<Book> books = new ArrayList<>();

    // 1. Lấy toàn bộ danh sách sách
    public List<Book> getAllBooks() {
        return books;
    }

    // 2. Lấy sách theo ID sử dụng Stream API
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 3. Thêm một cuốn sách mới
    public void addBook(Book book) {
        books.add(book);
    }

    // 4. Cập nhật thông tin sách theo ID
    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    // 5. Xóa sách khỏi danh sách theo ID
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}