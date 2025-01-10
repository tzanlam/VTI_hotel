import React, { useEffect, useState } from "react";
import { RoomService } from "../../service/RoomService";
import { Button, Card, Spin, Alert } from "antd";
import "../../asset/css/RoomPage.css";
import UpdateRoomModal from "../../components/UpdateRoomModal";

const RoomManager = () => {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [isUpdateModalVisible, setIsUpdateModalVisible] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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

  const handleOpenUpdateModal = (room) => {
    setSelectedRoom(room);
    setIsUpdateModalVisible(true);
  };

  const handleUpdateRoom = (updatedRoom) => {
    setLoading(true);
    RoomService.updateRoom(selectedRoom.roomId, updatedRoom)
      .then(() => {
        setRooms((prevRooms) =>
          prevRooms.map((room) =>
            room.roomId === selectedRoom.roomId ? { ...room, ...updatedRoom } : room
          )
        );
        setIsUpdateModalVisible(false);
      })
      .catch(() => {
        setError("Lỗi khi cập nhật thông tin phòng.");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalVisible(false);
    setSelectedRoom(null);
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
                className="room-card"
              >
                <Card.Meta
                  title={room.roomName}
                  description={`Giá: ${room.priceDay} VND/ngày`}
                />
                <Button
                  type="primary"
                  onClick={() => handleOpenUpdateModal(room)}
                  style={{ marginTop: "10px" }}
                >
                  Chỉnh sửa
                </Button>
              </Card>
            ))
          ) : (
            <h3>Không có phòng nào.</h3>
          )}
        </div>
      )}

      {selectedRoom && (
        <UpdateRoomModal
          visible={isUpdateModalVisible}
          onCancel={handleCloseUpdateModal}
          onUpdate={handleUpdateRoom}
          roomData={selectedRoom}
        />
      )}
    </div>
  );
};

export default RoomManager;
