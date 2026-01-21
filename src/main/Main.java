package main;

import manager.BookManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== MENU QUẢN LÝ SÁCH =====");
            System.out.println("1. Thêm sách");
            System.out.println("2. Xóa sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xuất danh sách sách");
            System.out.println("5. Tìm sách chứa 'Lập trình'");
            System.out.println("6. Tìm tối đa K sách có giá <= P");
            System.out.println("7. Tìm sách theo danh sách tác giả");
            System.out.println("0. Thoát");
            System.out.print(" Chọn chức năng: ");


            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.addBook();
                    break;

                case 2:
                    manager.removeBook();
                    break;

                case 3:
                    manager.updateBook();
                    break;

                case 4:
                    manager.showAll();
                    break;

                case 5:
                    manager.findBookByKeyword();
                    break;

                case 6:
                    manager.findBookByPriceAndLimit();
                    break;

                case 7:
                    manager.findBookByAuthors();
                    break;

                case 0:
                    System.out.println("Thoát chương trình");
                    break;

                default:
                    System.out.println("Sai lựa chọn");
                    break;
            }

        } while (choice != 0);
    }
}
