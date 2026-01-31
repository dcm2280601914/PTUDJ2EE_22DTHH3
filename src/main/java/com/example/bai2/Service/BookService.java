package com.example.bai2.Service;

import com.example.bai2.Model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // Biến để quản lý ID tự động tăng, bắt đầu từ 5 (vì đã có 4 cuốn mẫu)
    private int nextId = 5;

    public BookService() {
        books.add(new Book(1, "Java Core", "Nguyễn Văn A"));
        books.add(new Book(2, "Spring Boot", "Trần Văn B"));
        books.add(new Book(3, "Clean Code", "Robert C. Martin"));
        books.add(new Book(4, "Design Patterns", "GoF"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Cập nhật hàm THÊM: Tự động gán ID mới
    public void addBook(Book book) {
        book.setId(nextId++); // Gán ID hiện tại và tăng lên cho lần sau
        books.add(book);
    }

    public void updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book existingBook = books.get(i);
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                return;
            }
        }
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}