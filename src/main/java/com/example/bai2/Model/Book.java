package com.example.bai2.Model;

import java.util.Objects;

public class Book {

    private Long id;               // Đổi sang Long để an toàn hơn
    private String title;
    private String author;
    private Category category;     // Giả sử Category là enum
    private String imageUrl;

    // Constructor mặc định (bắt buộc cho form binding)
    public Book() {
    }

    // Constructor đầy đủ (dùng trong service để tạo mẫu)
    public Book(Long id, String title, String author, Category category, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Constructor tiện lợi (không cần ID khi thêm mới)
    public Book(String title, String author, Category category, String imageUrl) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // toString() hữu ích khi debug/log
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    // equals() & hashCode() (dựa trên ID nếu có, tránh lỗi khi dùng trong Set/List)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}