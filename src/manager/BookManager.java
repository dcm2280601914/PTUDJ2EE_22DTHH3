package manager;

import model.Book;

import java.util.ArrayList;
import java.util.Scanner;

public class BookManager {
    private ArrayList<Book> books = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // 1. Thêm sách
    public void addBook() {
        System.out.print("Nhập mã sách: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập tên sách: ");
        String name = scanner.nextLine();

        System.out.print("Nhập tác giả: ");
        String author = scanner.nextLine();

        System.out.print("Nhập giá: ");
        double price = scanner.nextDouble();

        books.add(new Book(id, name, author, price));
        System.out.println(" Thêm sách thành công");
    }

    // 2. Xóa sách theo ID
    public void removeBook() {
        System.out.print("Nhập mã sách cần xóa: ");
        int id = scanner.nextInt();

        books.removeIf(b -> b.getId() == id);
        System.out.println("✔ Đã xóa (nếu tồn tại)");
    }

    // 3. Sửa sách
    public void updateBook() {
        System.out.print("Nhập mã sách cần sửa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Book b : books) {
            if (b.getId() == id) {
                System.out.print("Tên mới: ");
                b.setName(scanner.nextLine());

                System.out.print("Tác giả mới: ");
                b.setAuthor(scanner.nextLine());

                System.out.print("Giá mới: ");
                b.setPrice(scanner.nextDouble());

                System.out.println(" Cập nhật thành công");
                return;
            }
        }
        System.out.println(" Không tìm thấy sách");
    }

    // 4. Xuất danh sách
    public void showAll() {
        if (books.isEmpty()) {
            System.out.println("Danh sách rỗng");
            return;
        }
        books.forEach(System.out::println);
    }
    /**
     * 5. Tìm sách có tên chứa chữ "Lập trình" (không phân biệt hoa thường)
     */
    public void findBookByKeyword() {

        // Từ khóa cần tìm
        String keyword = "lập trình";

        boolean found = false;

        // Duyệt danh sách sách
        for (Book b : books) {

            // Chuyển tên sách về chữ thường rồi kiểm tra có chứa keyword hay không
            if (b.getName().toLowerCase().contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }

        // Nếu không tìm thấy cuốn nào
        if (!found) {
            System.out.println("❌ Không tìm thấy sách chứa từ 'Lập trình'");
        }
    }
    /**
     * 6. Lấy tối đa K cuốn sách có giá <= P
     */
    public void findBookByPriceAndLimit() {

        // Nhập số lượng sách cần lấy
        System.out.print("Nhập số lượng K: ");
        int k = scanner.nextInt();

        // Nhập giá tối đa
        System.out.print("Nhập giá P: ");
        double p = scanner.nextDouble();

        int count = 0;

        // Duyệt danh sách sách
        for (Book b : books) {

            // Kiểm tra giá sách <= P
            if (b.getPrice() <= p) {
                System.out.println(b);
                count++;

                // Nếu đã đủ K sách thì dừng
                if (count == k) {
                    break;
                }
            }
        }

        // Nếu không có sách nào thỏa điều kiện
        if (count == 0) {
            System.out.println("❌ Không có sách phù hợp");
        }
    }
    /**
     * 7. Nhập danh sách tác giả và hiển thị sách của các tác giả đó
     */
    public void findBookByAuthors() {

        scanner.nextLine(); // Xóa bộ nhớ đệm

        // Nhập danh sách tác giả
        System.out.print("Nhập danh sách tác giả (cách nhau bằng dấu phẩy): ");
        String input = scanner.nextLine();

        // Tách chuỗi thành mảng tác giả
        String[] authors = input.split(",");

        boolean found = false;

        // Duyệt danh sách sách
        for (Book b : books) {

            // Duyệt từng tác giả nhập vào
            for (String a : authors) {

                // So sánh tên tác giả (không phân biệt hoa thường)
                if (b.getAuthor().equalsIgnoreCase(a.trim())) {
                    System.out.println(b);
                    found = true;
                }
            }
        }

        // Nếu không tìm thấy
        if (!found) {
            System.out.println("❌ Không tìm thấy sách của các tác giả đã nhập");
        }
    }

}
