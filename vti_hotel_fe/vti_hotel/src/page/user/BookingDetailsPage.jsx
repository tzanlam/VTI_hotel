import React from "react";
import { useLocation } from "react-router-dom";
import { Card } from "antd";

const BookingDetailsPage = () => {
  const location = useLocation();
  const booking = location.state?.booking;

  if (!booking) {
    return <h2>Không tìm thấy thông tin đặt phòng!</h2>;
  }

  return (
    <div style={{ padding: "20px", border: "10px 10px solid" }}>
      <h1>HUY PHƯƠNG HOTEL</h1>
      <Card title="Chi tiết đặt phòng" bordered>
        <p><strong>ID đặt phòng:</strong> {booking.bookingId}</p>
        <p><strong>Tên tài khoản:</strong> {booking.account}</p>
        <p><strong>Phòng:</strong> {booking.room}</p>
        <p><strong>Loại đặt phòng:</strong> {booking.typeBooking}</p>
        <p><strong>Ngày check-in:</strong> {booking.checkIn}</p>
        <p><strong>Ngày check-out:</strong> {booking.checkOut}</p>
        <p><strong>Mã giảm giá:</strong> {booking.voucher || "Không có"}</p>
        <p><strong>Tổng tiền:</strong> {booking.totalPrice}</p>
        <p><strong>Trạng thái:</strong> {booking.bookingStatus}</p>
      </Card>
    </div>
  );
};

export default BookingDetailsPage;
