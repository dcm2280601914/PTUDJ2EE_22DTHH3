package com.example.bai2.Service;

import com.example.bai2.Model.Book;
import com.example.bai2.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final List<Book> books = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public BookService() {
    }

    // Lấy tất cả sách (trả về bản copy để an toàn)
    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books));
        // Hoặc: return new ArrayList<>(books); nếu muốn cho phép modify copy
    }

    // Lấy sách theo ID (đổi sang Long)
    public Book getBookById(Long id) {
        if (id == null) return null;
        return books.stream()
                .filter(b -> b.getId() != null && b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm sách mới
    public void addBook(Book book) {
        if (book == null) return;
        book.setId(nextId.getAndIncrement());
        books.add(book);
    }

    // Cập nhật sách
    public boolean updateBook(Book updatedBook) {
        if (updatedBook == null || updatedBook.getId() == null) return false;

        return books.stream()
                .filter(b -> b.getId() != null && b.getId().equals(updatedBook.getId()))
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCategory(updatedBook.getCategory());
                    book.setImageUrl(updatedBook.getImageUrl());
                    return true;
                })
                .orElse(false);
    }

    // Xóa sách
    public boolean deleteBook(Long id) {
        if (id == null) return false;
        return books.removeIf(b -> b.getId() != null && b.getId().equals(id));
    }
}