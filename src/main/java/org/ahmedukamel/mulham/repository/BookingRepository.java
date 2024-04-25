package org.ahmedukamel.mulham.repository;

import org.ahmedukamel.mulham.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = """
            SELECT b
            FROM bookings b
            WHERE YEAR (b.start) = :year
            """)
    List<Booking> findByDate(@Param(value = "year") Integer year);

    @Query(value = """
            SELECT b
            FROM bookings b
            WHERE YEAR (b.start) = :year
            AND MONTH (b.start) = :month
            """)
    List<Booking> findByDate(@Param(value = "year") Integer year,
                           @Param(value = "month") Integer month);

    @Query(value = """
            SELECT b
            FROM bookings b
            WHERE YEAR (b.start) = :year
            AND MONTH (b.start) = :month
            AND DAY (b.start) = :day
            """)
    List<Booking> findByDate(@Param(value = "year") Integer year,
                           @Param(value = "month") Integer month,
                           @Param(value = "day") Integer day);
}