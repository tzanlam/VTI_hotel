import React, { useEffect, useState } from "react";
import { RoomService } from "../../service/RoomService";
import { Button, Card, Modal, Spin, Alert } from "antd";
import "../../asset/css/RoomPage.css";
import { useNavigate } from "react-router-dom"; // Import useNavigate

const RoomPage = () => {
  const [rooms, setRooms] = useState([]);
  const [selectRoom, setSelectRoom] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // Sử dụng hook useNavigate

  useEffect(() => {
    RoomService.fetchRooms()
      .then((response) => {
        if (response.data) {
          setRooms(response.data);
        } else {
          setError("Không có dữ liệu phòng.");
        }
      })
      .catch(() => {
        setError("Lỗi khi tải dữ liệu phòng.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleCardClick = (roomId) => {
    setLoading(true);
    RoomService.fetchRoomById(roomId)
      .then((response) => {
        setSelectRoom(response.data);
        setIsModalVisible(true);
      })
      .catch(() => {
        setError("Lỗi khi tải chi tiết phòng.");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleCloseModal = () => {
    setIsModalVisible(false);
    setSelectRoom(null);
  };

  // Chuyển hướng sang BookingPage với roomId và roomName
  const handleBookingRedirect = (roomId, roomName) => {
    navigate("/booking", { state: { roomId, roomName } });
  };

  return (
    <div className="room-page">
      <h1>Danh sách phòng</h1>

      {loading ? (
        <div className="loading">
          <Spin size="large" />
        </div>
      ) : error ? (
        <Alert message={error} type="error" showIcon />
      ) : (
        <div className="room-grid">
          {rooms.length > 0 ? (
            rooms.map((room) => (
              <Card
                key={room.roomId}
                hoverable
                cover={<img alt={room.roomName} src={room.imageRoom} />}
                onClick={() => handleCardClick(room.roomId)}
                className="room-card"
              >
                <Card.Meta
                  title={room.roomName}
                  description={`Giá: ${room.priceDay} VND/ngày`}
                />
              </Card>
            ))
          ) : (
            <h3>Không có phòng nào.</h3>
          )}
        </div>
      )}

      {selectRoom && (
        <Modal
          title={selectRoom.roomName}
          open={isModalVisible}
          onCancel={handleCloseModal}
          footer={null}
          width={600}
        >
          <img
            alt={selectRoom.roomName}
            src={selectRoom.imageRoom}
            style={{ width: "100%", marginBottom: "16px" }}
          />
          <h4>Giới thiệu chung: {selectRoom.description}</h4>
          <p>
            <h5>Giá ngày:</h5> {selectRoom.priceDay} VND
          </p>
          <p>
            <h5>Giá đêm:</h5> {selectRoom.priceNight} VND
          </p>

          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <Button
              type="primary"
              onClick={() =>
                handleBookingRedirect(selectRoom.roomId, selectRoom.roomName)
              }
            >
              Đặt phòng
            </Button>
          </div>
        </Modal>
      )}
    </div>
  );
};

export default RoomPage;
