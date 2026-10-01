import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc =
            new Scanner(System.in);

    static TicketBookingDAO dao =
            new TicketBookingDAO();

    static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm"
            );

    public static void main(String[] args) {

        int choice = 0;

        do {

            showMenu();

            try {

                System.out.print(
                        "Chọn chức năng: "
                );

                choice =
                        Integer.parseInt(
                                sc.nextLine()
                        );

                switch (choice) {

                    case 1:
                        displayBookings();
                        break;

                    case 2:
                        addBooking();
                        break;

                    case 3:
                        updateBooking();
                        break;

                    case 4:
                        deleteBooking();
                        break;

                    case 5:
                        searchCustomer();
                        break;

                    case 6:
                        searchMovie();
                        break;

                    case 7:

                        System.out.println(
                                "Đã thoát chương trình!"
                        );

                        break;

                    default:

                        System.out.println(
                                "Vui lòng chọn từ 1 đến 7!"
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Bạn phải nhập số!"
                );
            }

        } while (choice != 7);

        sc.close();
    }


    // ============================
    // MENU
    // ============================

    public static void showMenu() {

        System.out.println();

        System.out.println(
                "===================================================="
        );

        System.out.println(
                "          CINEMA TICKET BOOKING MANAGEMENT"
        );

        System.out.println(
                "===================================================="
        );

        System.out.println(
                "1. Danh sách tất cả phiếu đặt vé"
        );

        System.out.println(
                "2. Thêm mới phiếu đặt vé"
        );

        System.out.println(
                "3. Cập nhật thông tin phiếu đặt vé"
        );

        System.out.println(
                "4. Xóa phiếu đặt vé"
        );

        System.out.println(
                "5. Tìm kiếm phiếu đặt vé theo tên khách hàng"
        );

        System.out.println(
                "6. Tìm kiếm phiếu đặt vé theo tên phim"
        );

        System.out.println(
                "7. Thoát"
        );
    }
    public static void displayBookings() {
        List<TicketBooking> list = dao.getAllBookings();
        printList(list);
    }
    public static void addBooking() {
        System.out.println("\n===== THÊM PHIẾU ĐẶT VÉ =====");
        String movie = inputMovie();
        String customer = inputCustomer();
        LocalDateTime showTime = inputDateTime("Nhập suất chiếu (yyyy-MM-dd HH:mm): ");
        LocalDateTime bookingDate = inputDateTime("Nhập ngày đặt (yyyy-MM-dd HH:mm): ");
        int quantity = inputQuantity();
        String status = inputStatus();
        TicketBooking booking = new TicketBooking(movie, customer, showTime, bookingDate, quantity, status);
        if (dao.addBooking(booking)) {
            System.out.println("Thêm phiếu đặt vé thành công!");
        } else {
            System.out.println("Thêm phiếu đặt vé thất bại!");
        }
    }
    public static void updateBooking() {
        try {
            System.out.print("Nhập booking_id cần cập nhật: ");
            int id = Integer.parseInt(sc.nextLine());
            if (!dao.existsById(id)) {
                System.out.println("Không tìm thấy phiếu đặt vé!");
                return;
            }
            System.out.println("booking_id = " + id + " không được phép thay đổi.");
            String movie = inputMovie();
            String customer = inputCustomer();
            LocalDateTime showTime = inputDateTime("Nhập suất chiếu mới (yyyy-MM-dd HH:mm): ");
            LocalDateTime bookingDate = inputDateTime("Nhập ngày đặt mới (yyyy-MM-dd HH:mm): ");
            int quantity = inputQuantity();
            String status = inputStatus();
            TicketBooking booking = new TicketBooking(id, movie, customer, showTime, bookingDate, quantity, status);
            if (dao.updateBooking(booking)) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            System.out.println("booking_id phải là số!");
        }
    }
    public static void deleteBooking() {
        try {
            System.out.print("Nhập booking_id cần xóa: ");
            int id = Integer.parseInt(sc.nextLine());
            if (!dao.existsById(id)) {
                System.out.println("Không tìm thấy phiếu đặt vé!");
                return;
            }
            System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                if (dao.deleteBooking(id)) {
                    System.out.println("Xóa phiếu đặt vé thành công!");
                }
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } catch (NumberFormatException e) {
            System.out.println("booking_id phải là số!");
        }
    }
    public static void searchCustomer() {
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Tên tìm kiếm không được để trống!");
            return;
        }
        List<TicketBooking> list = dao.searchByCustomer(name);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy phiếu đặt vé phù hợp!");
        } else {
            printList(list);
        }
    }
    public static void searchMovie() {
        System.out.print("Nhập tên phim cần tìm: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Tên phim không được để trống!");
            return;
        }
        List<TicketBooking> list = dao.searchByMovie(name);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy phiếu đặt vé phù hợp!");
        } else {
            printList(list);
        }
    }
    public static String inputMovie() {
        while (true) {
            System.out.print("Nhập tên phim: ");
            String movie = sc.nextLine().trim();
            if (movie.isEmpty()) {
                System.out.println("Tên phim không được để trống!");
            } else if (movie.length() > 150) {
                System.out.println("Tên phim tối đa 150 ký tự!");
            } else {
                return movie;
            }
        }
    }
    public static String inputCustomer() {
        while (true) {
            System.out.print("Nhập tên khách hàng: ");
            String customer = sc.nextLine().trim();
            if (customer.isEmpty()) {
                System.out.println("Tên khách hàng không được để trống!");
            } else if (customer.length() > 100) {
                System.out.println("Tên khách hàng tối đa 100 ký tự!");
            } else {
                return customer;
            }
        }
    }
    public static int inputQuantity() {
        while (true) {
            try {
                System.out.print("Nhập số lượng ghế: ");
                int quantity = Integer.parseInt(sc.nextLine());
                if (quantity <= 0) {
                    System.out.println("Số lượng ghế phải lớn hơn 0!");
                } else {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                System.out.println("Số lượng ghế phải là số nguyên!");
            }
        }
    }
    public static LocalDateTime inputDateTime(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDateTime.parse(sc.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Sai định dạng! Ví dụ: 2026-10-10 19:30");
            }
        }
    }
    public static String inputStatus() {
        while (true) {
            System.out.println("Trạng thái:");
            System.out.println("1. Booked");
            System.out.println("2. Cancelled");
            System.out.println("3. CheckedIn");
            System.out.print("Chọn trạng thái: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                return "Booked";
            } else if (choice.equals("2")) {
                return "Cancelled";
            } else if (choice.equals("3")) {
                return "CheckedIn";
            } else {
                System.out.println("Trạng thái không hợp lệ!");
            }
        }
    }
    public static void printList(List<TicketBooking> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách đang trống!");
            return;
        }
        System.out.printf("%-5s %-25s %-20s %-18s %-18s %-8s %-12s%n", "ID", "TÊN PHIM", "KHÁCH HÀNG", "SUẤT CHIẾU", "NGÀY ĐẶT", "GHẾ", "TRẠNG THÁI");
        for (TicketBooking b : list) {
            System.out.printf("%-5d %-25s %-20s %-18s %-18s %-8d %-12s%n", b.getBookingId(), b.getMovieTitle(), b.getCustomerName(), b.getShowTime().format(formatter), b.getBookingDate().format(formatter), b.getSeatQuantity(), b.getStatus());
        }
    }
}