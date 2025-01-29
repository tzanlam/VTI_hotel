import React, { useState, useEffect, useCallback } from "react";
import {  ReviewService } from "../../service/ReviewService";
import { List, Card, Button, Input, message } from "antd";

const { TextArea } = Input;

const ListComment = () => {
  const [reviews, setReviews] = useState([]);
  const [selectedReview, setSelectedReview] = useState(null);
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchReviewData = async () => {
      try {
        setLoading(true);
        const response = await ReviewService.fetchReviews();
        setReviews(response.data);
        setLoading(false);
      } catch (err) {
        message.error("Lỗi khi lấy danh sách đánh giá!");
        setLoading(false);
      }
    };

    fetchReviewData();
  }, []);

  const handleReviewClick = useCallback((review) => {
    setSelectedReview(review);
  }, []);

  const handleResponseSubmit = () => {
    if (!response.trim()) {
      message.warning("Vui lòng nhập nội dung phản hồi!");
      return;
    }
    message.success(`Phản hồi cho đánh giá ${selectedReview.id} đã gửi!`);
    setResponse("");
    setSelectedReview(null);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Danh sách đánh giá</h2>
      <List
        loading={loading}
        dataSource={reviews}
        renderItem={(review) => (
          <List.Item>
            <Card
              title={"Mã đặt phòng: " + review.code}
              style={{ width: "100%" }}
              onClick={() => handleReviewClick(review)}
              hoverable
            >
              <p><strong>Họ tên:</strong> {review.fullName}</p>
              <p><strong>Phòng:</strong> {review.roomName}</p>
              <p><strong>Bình luận:</strong> {review.comment}</p>
              <p><strong>Đánh giá:</strong> {review.rating} ⭐</p>
            </Card>
          </List.Item>
        )}
      />

      {selectedReview && (
        <Card title="Trả lời đánh giá" style={{ marginTop: "20px" }}>
          <p><strong>Đánh giá từ:</strong> {selectedReview.fullName}</p>
          <TextArea
            value={response}
            onChange={(e) => setResponse(e.target.value)}
            rows={4}
            placeholder="Nhập phản hồi..."
          />
          <Button type="primary" style={{ marginTop: "10px" }} onClick={handleResponseSubmit}>
            Gửi phản hồi
          </Button>
        </Card>
      )}
    </div>
  );
};

export default ListComment;
