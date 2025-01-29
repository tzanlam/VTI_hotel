import React, { useEffect, useState } from "react";
import { RoomService } from "../../service/RoomService";
import { Button, Card, Modal, Spin, Alert } from "antd";
import "../../asset/css/RoomPage.css";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { ReviewService } from "../../service/ReviewService";
import avt_default from "../../asset/image/avt_default.jpg";
const RoomPage = () => {
  const [rooms, setRooms] = useState([]);
  const [selectRoom, setSelectRoom] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [reviews, setReviews] = useState([]);
  const navigate = useNavigate();

  const fetchReviews = async (roomId) => {
    try {
      const response = await ReviewService.fetchReviewsByRoom(roomId);
      setReviews(response?.data || []);
    } catch (err) {
      toast.error("Lỗi khi tải đánh giá.");
      setReviews([]);
    }
  };

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const response = await RoomService.fetchRooms();
        setRooms(response?.data || []);
      } catch {
        setError("Lỗi khi tải dữ liệu phòng.");
      } finally {
        setLoading(false);
      }
    };

    fetchRooms();
  }, []);

  const handleCardClick = async (roomId) => {
    try {
      setLoading(true);
      const response = await RoomService.fetchRoomById(roomId);
      setSelectRoom(response.data);
      await fetchReviews(roomId); // Lấy đánh giá ngay khi mở modal
      setIsModalVisible(true);
    } catch {
      setError("Lỗi khi tải chi tiết phòng.");
    } finally {
      setLoading(false);
    }
  };

  const handleCloseModal = () => {
    setIsModalVisible(false);
    setSelectRoom(null);
    setReviews([]);
  };

  const handleBookingRedirect = (roomId) => {
    if (localStorage.getItem("user")) {
    navigate("/booking", { state: { roomId } });
    } else {
      navigate("/dat-phong-nhanh", { state: { roomId } });
    }
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
        <div className="room-grid" >
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
          className="custom-modal"
          width="80vw" // Chỉnh chiều rộng modal
          style={{ maxWidth: "90vw" }}
        >
          <div style={{ display: "flex", gap: "24px" }}>
            <div style={{ flex: 1 }}>
              <img
                alt={selectRoom.roomName}
                src={selectRoom.imageRoom}
                style={{ width: "100%", marginBottom: "16px" }}
              />
              <h4>Giới thiệu chung: {selectRoom.description}</h4>
              <div>
                <h5>Giá ngày:</h5> {selectRoom.priceDay} VND
              </div>
              <div>
                <h5>Giá đêm:</h5> {selectRoom.priceNight} VND
              </div>
              <Button
                type="primary"
                style={{ marginTop: "16px" }}
                onClick={() =>
                  handleBookingRedirect(selectRoom.roomId)
                }
              >
                Đặt phòng
              </Button>
            </div>

            <div style={{ flex: 1, maxHeight: "500px", overflowY: "auto" }}>
              <h4>Đánh giá từ khách hàng</h4>
              {reviews.length > 0 ? (
                reviews.map((review) => (
                  <div
                    key={review.reviewId}
                    style={{
                      border: "1px solid #f0f0f0",
                      borderRadius: "8px",
                      padding: "16px",
                      marginBottom: "16px",
                      display: "flex",
                      gap: "16px",
                    }}
                  >
                    <img
                      src={review.avatar || avt_default}
                      alt="Avatar"
                      style={{
                        width: "50px",
                        height: "50px",
                        borderRadius: "50%",
                        objectFit: "cover",
                      }}
                    />
                    <div style={{ flex: 1 }}>
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                          alignItems: "center",
                        }}
                      >
                        <div>
                          <strong>{review.fullName}</strong>
                          <p style={{ margin: "4px 0", fontSize: "12px" }}>
                            {review.booking ? `Mã đặt phòng: ${review.booking}`: `Mã đặt nhanh: ${review.fastBooking}` }
                          </p>
                        </div>
                        <span>
                          <strong>{review.rating}</strong> ★
                        </span>
                      </div>
                      <p style={{ marginTop: "8px" }}>{review.comment}</p>
                    </div>
                  </div>
                ))
              ) : (
                <p>Chưa có đánh giá nào cho phòng này.</p>
              )}
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
};

export default RoomPage;
