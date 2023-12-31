package kr.co.mgv.booking.dao;

import kr.co.mgv.booking.vo.Booking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookingDao {
    void insertBooking(Booking booking);
    Booking getBookingByBookingNo(long no);
    List<Booking> getBookings();
    List<Booking> getBookingsByUserId(String userId);
    void updateBooking(Booking Booking);
    void insertBookedSeats(Map<String,Object> params);
    List<String> getBookedSeatsByScheduleId(int scheduleId);
    void completeBookedSeats(Map<String,Object> params);
    void deleteBookedSeats(Map<String,Object> params);
    void clearTimeOutBookedSeats();
    void deleteBookingByBookingNo(long bookingNo);

    int getTotalRows(String userId);
}
