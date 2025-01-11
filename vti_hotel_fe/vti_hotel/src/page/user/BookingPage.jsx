import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Button, Select, DatePicker, TimePicker } from "antd";
import { BookingService } from "../../service/BookingService";
import { RoomService } from "../../service/RoomService";
import { ToastContainer } from "react-toastify";
import { toast } from "react-toastify";

const { Option } = Select;

const BookingPage = () => {
  const location = useLocation(); // Nhận dữ liệu từ RoomPage
  const navigate = useNavigate();
  const [roomId, setRoomId] = useState(location.state?.roomId || null);
  // eslint-disable-next-line no-unused-vars
  const [roomName, setRoomName] = useState(location.state?.roomName || null);
  const [rooms, setRooms] = useState([]);
  const [accountId, setAccountId] = useState(null);
  const [bookingType, setBookingType] = useState(null);

  useEffect(() => {
    // Lấy thông tin account từ localStorage
    const user = localStorage.getItem("user");
    if (user) {
      try {
        const parsedUser = JSON.parse(user);
        setAccountId(parsedUser.data.accountId || null);
      } catch (error) {
        console.error("Lỗi khi parse user từ localStorage:", error);
      }
    }
  }, []);

  useEffect(() => {
    // Lấy danh sách các phòng
    RoomService.fetchRooms()
      .then((response) => {
        setRooms(response.data);
      })
      .catch((err) => {
        console.error("Lỗi khi lấy danh sách phòng:", err);
      });
  }, []);

  const handleRoomChange = (value) => {
    const selectedRoom = rooms.find((room) => room.roomId === value);
    setRoomId(selectedRoom?.roomId || null);
    setRoomName(selectedRoom?.roomName || null);
  };

  const handleBookingTypeChange = (value) => {
    setBookingType(value);
  };

  const handleBookingSubmit = (values) => {
    if (!accountId) {
      toast.error("Không thể đặt phòng do thiếu thông tin tài khoản.");
      return;
    }

    const bookingData = {
      accountId,
      roomId,
      typeBooking: bookingType,
      checkInDate: values.checkInDate.format("YYYY-MM-DD"),
      checkOutDate: values.checkOutDate?.format("YYYY-MM-DD") || null,
      checkInTime: values.checkInTime?.format("HH:mm:ss") || null,
      checkOutTime: values.checkOutTime?.format("HH:mm:ss") || null,
    };

    BookingService.createBooking(bookingData)
      .then((response) => {
        toast.success("Đặt phòng thành công!");
        navigate(`/booking-details/${response.data.bookingId}`,{
          state: {booking: response.data}
        })
      })
      .catch((err) => {
        toast.error("Lỗi khi đặt phòng: " + err.message);
      });
  };

  return (
    <div>
      <h1>Đặt phòng</h1>
      <Form onFinish={handleBookingSubmit}>
        {/* Chọn phòng */}
        <Form.Item
          label="Tên phòng"
          name="roomId"
          rules={[{ required: true, message: "Vui lòng chọn phòng." }]}
        >
          <Select
            value={roomId}
            onChange={handleRoomChange}
            placeholder="Chọn phòng"
          >
            {rooms.map((room) => (
              <Option key={room.roomId} value={room.roomId}>
                {room.roomName}
              </Option>
            ))}
          </Select>
        </Form.Item>

        {/* Loại đặt phòng */}
        <Form.Item
          label="Loại đặt phòng"
          name="typeBooking"
          rules={[{ required: true, message: "Vui lòng chọn loại đặt phòng." }]}
        >
          <Select
            placeholder="Chọn loại đặt phòng"
            onChange={handleBookingTypeChange}
          >
            <Option value="DAILY">Theo ngày</Option>
            <Option value="HOURLY">Theo giờ</Option>
            <Option value="NIGHTLY">Theo đêm</Option>
          </Select>
        </Form.Item>

        {/* Ngày check-in */}
        <Form.Item
          label="Ngày check-in"
          name="checkInDate"
          rules={[{ required: true, message: "Vui lòng chọn ngày check-in." }]}
        >
          <DatePicker />
        </Form.Item>

        {/* Ngày check-out */}
        {bookingType !== "HOURLY" && (
          <Form.Item
            label="Ngày check-out"
            name="checkOutDate"
            rules={[
              { required: true, message: "Vui lòng chọn ngày check-out." },
            ]}
          >
            <DatePicker />
          </Form.Item>
        )}

        {/* Giờ check-in */}
        {bookingType === "HOURLY" && (
          <Form.Item
            label="Giờ check-in"
            name="checkInTime"
            rules={[
              { required: true, message: "Vui lòng chọn giờ check-in." },
            ]}
          >
            <TimePicker format="HH:mm" />
          </Form.Item>
        )}

        {/* Giờ check-out */}
        {bookingType === "HOURLY" && (
          <Form.Item
            label="Giờ check-out"
            name="checkOutTime"
            rules={[
              { required: true, message: "Vui lòng chọn giờ check-out." },
            ]}
          >
            <TimePicker format="HH:mm" />
          </Form.Item>
        )}

        <Button type="primary" htmlType="submit">
          Đặt phòng
        </Button>
      </Form>
      <ToastContainer />
    </div>
  );
};

export default BookingPage;
