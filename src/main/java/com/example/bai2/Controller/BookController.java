package com.example.bai2.Controller;

import com.example.bai2.Model.Book;
import com.example.bai2.Model.Category;
import com.example.bai2.Service.BookService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    public static final String UPLOAD_DIR = "uploads";


    // ================== DANH SÁCH ==================
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    // ================== THÊM ==================
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Category.values());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          RedirectAttributes ra,
                          HttpServletRequest request) {

        try {
            if (!imageFile.isEmpty()) {

                // Lấy đường dẫn thật tới thư mục static/uploads
                String uploadPath = request.getServletContext()
                        .getRealPath("/uploads");

                File folder = new File(uploadPath);
                if (!folder.exists()) folder.mkdirs();

                String fileName = System.currentTimeMillis()
                        + "_" + imageFile.getOriginalFilename();

                File destFile = new File(folder, fileName);

                System.out.println("Lưu ảnh tại: " + destFile.getAbsolutePath());

                Files.copy(
                        imageFile.getInputStream(),
                        destFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                book.setImageUrl("/uploads/" + fileName);
            }

            bookService.addBook(book);
            ra.addFlashAttribute("success", "Thêm sách thành công!");
            return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("Lỗi upload ảnh!");
            return "redirect:/add";
        }
    }



    // ================== FORM SỬA ==================
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            ra.addFlashAttribute("error", "Không tìm thấy sách!");
            return "redirect:/";
        }
        model.addAttribute("book", book);
        model.addAttribute("categories", Category.values());
        return "edit-book";
    }

    // ================== SỬA ==================
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes ra) {

        try {
            Book oldBook = bookService.getBookById(book.getId());
            if (oldBook == null) {
                ra.addFlashAttribute("error", "Sách không tồn tại!");
                return "redirect:/";
            }

            if (!imageFile.isEmpty()) {
                File folder = new File(UPLOAD_DIR);
                if (!folder.exists()) folder.mkdirs();

                String fileName = System.currentTimeMillis()
                        + "_" + imageFile.getOriginalFilename();

                File destFile = new File(folder, fileName);
                imageFile.transferTo(destFile);

                book.setImageUrl("/uploads/" + fileName);
            } else {
                book.setImageUrl(oldBook.getImageUrl());
            }

            bookService.updateBook(book);
            ra.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/";

        } catch (IOException e) {
            ra.addFlashAttribute("error", "Lỗi upload ảnh!");
            return "redirect:/edit/" + book.getId();
        }
    }

    // ================== XÓA ==================
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes ra) {

        Book book = bookService.getBookById(id);
        if (book == null) {
            ra.addFlashAttribute("error", "Không tìm thấy sách!");
            return "redirect:/";
        }

        // XÓA FILE ẢNH (nếu có)
        if (book.getImageUrl() != null) {
            String filePath = "src/main/resources/static" + book.getImageUrl();
            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }

        bookService.deleteBook(id);
        ra.addFlashAttribute("success", "Xóa sách thành công!");
        return "redirect:/";
    }
}
