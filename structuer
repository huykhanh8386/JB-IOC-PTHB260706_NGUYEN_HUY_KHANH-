CREATE DATABASE db_cinema_bookings;

CREATE TABLE ticket_bookings (
     booking_id SERIAL PRIMARY KEY,
     movie_title VARCHAR(150) NOT NULL,
     customer_name VARCHAR(100) NOT NULL,
     show_time TIMESTAMP NOT NULL,
     booking_date TIMESTAMP NOT NULL,
     seat_quantity INT NOT NULL,
     status VARCHAR(30) NOT NULL
);

INSERT INTO ticket_bookings
(movie_title, customer_name, show_time, booking_date, seat_quantity, status)
VALUES
    ('Avengers: Endgame', 'Nguyen Van An', '2026-10-10 19:30:00', '2026-10-01 08:00:00', 2, 'Booked'),
    ('Conan Movie 27', 'Tran Thi Mai', '2026-10-11 20:00:00', '2026-10-01 09:30:00', 3, 'Booked'),
    ('Doraemon', 'Nguyen Van Binh','2026-10-12 18:00:00', '2026-10-01 10:00:00', 1, 'CheckedIn');

CREATE OR REPLACE FUNCTION get_all_bookings()
RETURNS TABLE (
  booking_id INT,
  movie_title VARCHAR,
  customer_name VARCHAR,
  show_time TIMESTAMP,
  booking_date TIMESTAMP,
  seat_quantity INT,
  status VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            tb.booking_id,
            tb.movie_title,
            tb.customer_name,
            tb.show_time,
            tb.booking_date,
            tb.seat_quantity,
            tb.status
        FROM ticket_bookings tb
        ORDER BY tb.booking_id;
END;
$$;

CREATE OR REPLACE PROCEDURE add_booking(
    p_movie_title VARCHAR,
    p_customer_name VARCHAR,
    p_show_time TIMESTAMP,
    p_booking_date TIMESTAMP,
    p_seat_quantity INT,
    p_status VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO ticket_bookings(
        movie_title,
        customer_name,
        show_time,
        booking_date,
        seat_quantity,
        status
    )
VALUES (
   p_movie_title,
   p_customer_name,
   p_show_time,
   p_booking_date,
   p_seat_quantity,
   p_status
);
END;
$$;

CREATE OR REPLACE FUNCTION search_booking_by_customer(
    p_customer_name VARCHAR
)
RETURNS TABLE (
  booking_id INT,
  movie_title VARCHAR,
  customer_name VARCHAR,
  show_time TIMESTAMP,
  booking_date TIMESTAMP,
  seat_quantity INT,
  status VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
    SELECT
        tb.booking_id,
        tb.movie_title,
        tb.customer_name,
        tb.show_time,
        tb.booking_date,
        tb.seat_quantity,
        tb.status
    FROM ticket_bookings tb
    WHERE tb.customer_name
              ILIKE '%' || p_customer_name || '%'
    ORDER BY tb.booking_id;
END;
$$;

CREATE OR REPLACE PROCEDURE update_booking(
    p_booking_id INT,
    p_movie_title VARCHAR,
    p_customer_name VARCHAR,
    p_show_time TIMESTAMP,
    p_booking_date TIMESTAMP,
    p_seat_quantity INT,
    p_status VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE ticket_bookings
    SET
        movie_title = p_movie_title,
        customer_name = p_customer_name,
        show_time = p_show_time,
        booking_date = p_booking_date,
        seat_quantity = p_seat_quantity,
        status = p_status
    WHERE booking_id = p_booking_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_booking(
    p_booking_id INT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM ticket_bookings
    WHERE booking_id = p_booking_id;
END;
$$;

CREATE OR REPLACE FUNCTION search_booking_by_movie(
    p_movie_title VARCHAR
)
    RETURNS TABLE (
      booking_id INT,
      movie_title VARCHAR,
      customer_name VARCHAR,
      show_time TIMESTAMP,
      booking_date TIMESTAMP,
      seat_quantity INT,
      status VARCHAR
  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            tb.booking_id,
            tb.movie_title,
            tb.customer_name,
            tb.show_time,
            tb.booking_date,
            tb.seat_quantity,
            tb.status
        FROM ticket_bookings tb
        WHERE tb.movie_title
                  ILIKE '%' || p_movie_title || '%'
        ORDER BY tb.booking_id;
END;
$$;
