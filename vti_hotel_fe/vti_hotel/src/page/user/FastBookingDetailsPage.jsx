import React from "react";
import { useLocation } from "react-router-dom";
import { Card } from "antd";

const FaBookingDetailsPage = () => {
  const location = useLocation();
  const fastBooking = location.state?.fastBooking;

  if (!fastBooking) {
    return <h2>Không tìm thấy thông tin đặt phòng!</h2>;
  }

  return (
    <div style={{ padding: "20px", border: "10px 10px solid" }}>
      <h1>HUY PHƯƠNG HOTEL</h1>
      <Card title="Chi tiết đặt phòng nhanh" bordered>
        <p><strong>ID đặt phòng:</strong> {fastBooking.fastBookingId}</p>
        <p><strong>Tên tài khoản:</strong> {fastBooking.fullName}</p>
        <p><strong>Số điện thoại:</strong> {fastBooking.phoneNumber}</p>
        <p><strong>Phòng:</strong> {fastBooking.room}</p>
        <p><strong>Loại đặt phòng:</strong> {fastBooking.typeBooking}</p>
        <p><strong>Ngày check-in:</strong> {fastBooking.checkin}</p>
        <p><strong>Ngày check-out:</strong> {fastBooking.checkout}</p>
        <p><strong>Tổng tiền:</strong> {fastBooking.totalPrice}</p>
        <p><strong>Trạng thái:</strong> {fastBooking.fastBookingStatus}</p>
      </Card>
    </div>
  );
};

export default FaBookingDetailsPage;
