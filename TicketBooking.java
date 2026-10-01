import java.time.LocalDateTime;

public class TicketBooking {

    private int bookingId;
    private String movieTitle;
    private String customerName;
    private LocalDateTime showTime;
    private LocalDateTime bookingDate;
    private int seatQuantity;
    private String status;

    public TicketBooking() {
    }
    public TicketBooking(
            int bookingId,
            String movieTitle,
            String customerName,
            LocalDateTime showTime,
            LocalDateTime bookingDate,
            int seatQuantity,
            String status
    ) {
        this.bookingId = bookingId;
        this.movieTitle = movieTitle;
        this.customerName = customerName;
        this.showTime = showTime;
        this.bookingDate = bookingDate;
        this.seatQuantity = seatQuantity;
        this.status = status;
    }
    public TicketBooking(
            String movieTitle,
            String customerName,
            LocalDateTime showTime,
            LocalDateTime bookingDate,
            int seatQuantity,
            String status
    ) {
        this.movieTitle = movieTitle;
        this.customerName = customerName;
        this.showTime = showTime;
        this.bookingDate = bookingDate;
        this.seatQuantity = seatQuantity;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}