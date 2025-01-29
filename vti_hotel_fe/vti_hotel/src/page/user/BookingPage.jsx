import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Button, Select, DatePicker, TimePicker, Card } from "antd";
import { BookingService } from "../../service/BookingService";
import { RoomService } from "../../service/RoomService";
import { ToastContainer } from "react-toastify";
import { toast } from "react-toastify";
import '../../asset/css/BookingPage.css'; // Import CSS

const { Option } = Select;

const BookingPage = () => {
  const { state } = useLocation(); // Nhận dữ liệu từ RoomPage
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [rooms, setRooms] = useState([]);
  const [accountId, setAccountId] = useState(null);
  const [bookingType, setBookingType] = useState(null);
  const [selectedRoom, setSelectedRoom] = useState(null);

  // Lấy thông tin account từ localStorage
  useEffect(() => {
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

  // Lấy danh sách các phòng
  useEffect(() => {
    RoomService.fetchRooms()
      .then((response) => {
        setRooms(
          response.data.map((item) => ({
            label: item.roomName,
            value: item.roomId,
            image: item.imageRoom, // Đảm bảo dữ liệu có ảnh
          }))
        );
      })
      .catch((err) => {
        console.error("Lỗi khi lấy danh sách phòng:", err);
      });
  }, []);

  // Nếu có `roomId` trong state, set giá trị mặc định cho form
  useEffect(() => {
    if (state && state.roomId) {
      form.setFieldValue("roomId", state.roomId);
    }
  }, [state, form]);

  const handleRoomSelect = (roomId) => {
    setSelectedRoom(roomId);
    form.setFieldValue("roomId", roomId); // Cập nhật giá trị form
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
      roomId: values.roomId,
      typeBooking: bookingType,
      checkInDate: values.checkInDate.format("YYYY-MM-DD"),
      checkOutDate: values.checkOutDate?.format("YYYY-MM-DD") || null,
      checkInTime: values.checkInTime?.format("HH:mm:ss") || null,
      checkOutTime: values.checkOutTime?.format("HH:mm:ss") || null,
    };

    BookingService.createBooking(bookingData)
      .then((response) => {
        toast.success("Đặt phòng thành công!");
        navigate(`/booking-details/${response.data.bookingId}`, {
          state: { booking: response.data },
        });
      })
      .catch((err) => {
        toast.error("Lỗi khi đặt phòng: " + err.message);
      });
  };

  return (
    <div>
      <h1>Đặt phòng</h1>
      <div className="room-gallery">
        {rooms.map((room) => (
          <Card
            key={room.value}
            className={`room-card ${
              selectedRoom === room.value ? "room-card-selected" : ""
            }`}
            hoverable
            onClick={() => handleRoomSelect(room.value)}
          >
            <img src={room.image} alt={room.label} className="room-image" />
            <p>{room.label}</p>
          </Card>
        ))}
      </div>

      <Form onFinish={handleBookingSubmit} form={form}>
        {/* Chọn phòng */}
        <Form.Item
          label="Tên phòng"
          name="roomId"
          rules={[{ required: true, message: "Vui lòng chọn phòng." }]}
        >
          <Select
            placeholder="Chọn phòng"
            options={rooms}
            onChange={handleRoomSelect}
          />
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
            rules={[{ required: true, message: "Vui lòng chọn giờ check-in." }]}
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
