import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom"; // Import useLocation
import { Form, Button, Select, DatePicker } from "antd";
import { BookingService } from "../../service/BookingService";
import { RoomService} from "../../service/RoomService"
import { ToastContainer } from "react-toastify";
import { toast } from "react-toastify";


const { Option } = Select;

const BookingPage = () => {
  const location = useLocation(); // Nhận dữ liệu từ RoomPage
  const [roomId, setRoomId] = useState(location.state?.roomId || null);
  // eslint-disable-next-line no-unused-vars
  const [roomName, setRoomName] = useState(location.state?.roomName || null);
  const [rooms, setRooms] = useState([]);

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

  const handleBookingSubmit = (values) => {
    const bookingData = {
      ...values,
      roomId,
    };
    BookingService.createBooking(bookingData)
      .then(() => {
        toast.success("Đặt phòng thành công!");
      })
      .catch((err) => {
        toast.error("Lỗi khi đặt phòng:", err);
      });
  };

  return (
    <div>
      <h1>Đặt phòng</h1>
      <Form onFinish={handleBookingSubmit}>
        <Form.Item label="Tên phòng" name="roomId">
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
        <Form.Item label="Loại đặt phòng" name="typeBooking" rules={[{ required: true, message: "Vui lòng chọn loại đặt phòng." }]}>
            <Select placeholder="Chọn loại đặt phòng">
              <Option value="DAILY">Theo ngày</Option>
              <Option value="HOURLY">Theo giờ</Option>
              <Option value="NIGHTLY">Theo đêm</Option>
            </Select>
          </Form.Item>

        <Form.Item label="Ngày check-in" name="checkInDate">
          <DatePicker />
        </Form.Item>

        <Form.Item label="Ngày check-out" name="checkOutDate">
          <DatePicker />
        </Form.Item>

        <Button type="primary" htmlType="submit">
          Đặt phòng
        </Button>
      </Form>
      <ToastContainer />
    </div>
  );
};

export default BookingPage;
