import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketBookingDAO {
    public List<TicketBooking> getAllBookings() {
        List<TicketBooking> list = new ArrayList<>();
        String sql = "SELECT * FROM get_all_bookings()";
        try (
                Connection con = ConnectDB.openConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                TicketBooking booking = getBookingFromResultSet(rs);
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean addBooking(TicketBooking booking) {
        String sql = "CALL add_booking(?, ?, ?, ?, ?, ?)";
        try (
                Connection con = ConnectDB.openConnection();
                CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setString(1, booking.getMovieTitle());
            cs.setString(2, booking.getCustomerName());
            cs.setTimestamp(3, Timestamp.valueOf(booking.getShowTime()));
            cs.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate()));
            cs.setInt(5, booking.getSeatQuantity());
            cs.setString(6, booking.getStatus());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm phiếu: " + e.getMessage());
            return false;
        }
    }
    public boolean updateBooking(TicketBooking booking) {
        if (!existsById(booking.getBookingId())) {
            return false;
        }
        String sql = "CALL update_booking(?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection con = ConnectDB.openConnection();
                CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, booking.getBookingId());
            cs.setString(2, booking.getMovieTitle());
            cs.setString(3, booking.getCustomerName());
            cs.setTimestamp(4, Timestamp.valueOf(booking.getShowTime()));
            cs.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            cs.setInt(6, booking.getSeatQuantity());
            cs.setString(7, booking.getStatus());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteBooking(int id) {
        if (!existsById(id)) {
            return false;
        }
        String sql = "CALL delete_booking(?)";
        try (
                Connection con = ConnectDB.openConnection();
                CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, id);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa: " + e.getMessage());
            return false;
        }
    }
    public List<TicketBooking>
    searchByCustomer(String name) {
        List<TicketBooking> list = new ArrayList<>();
        String sql = "SELECT * " + "FROM search_booking_by_customer(?)";
        try (
                Connection con = ConnectDB.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            try (
                    ResultSet rs = ps.executeQuery()
            ) {
                while (rs.next()) {
                    list.add(getBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<TicketBooking>
    searchByMovie(String name) {
        List<TicketBooking> list = new ArrayList<>();
        String sql = "SELECT * " + "FROM search_booking_by_movie(?)";
        try (
                Connection con = ConnectDB.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            try (
                    ResultSet rs = ps.executeQuery()
            ) {
                while (rs.next()) {
                    list.add(getBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean existsById(int id) {
        String sql = """
                SELECT booking_id
                FROM ticket_bookings
                WHERE booking_id = ?
                """;
        try (
                Connection con = ConnectDB.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (
                    ResultSet rs = ps.executeQuery()
            ) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private TicketBooking
    getBookingFromResultSet(ResultSet rs)
            throws SQLException {
        return new TicketBooking(
                rs.getInt("booking_id"),
                rs.getString("movie_title"),
                rs.getString("customer_name"),
                rs.getTimestamp("show_time")
                        .toLocalDateTime(),
                rs.getTimestamp("booking_date")
                        .toLocalDateTime(),
                rs.getInt("seat_quantity"),
                rs.getString("status"));
    }
}