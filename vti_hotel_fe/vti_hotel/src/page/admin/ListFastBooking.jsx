import React, { useState, useEffect } from "react";
import { Table, Modal, Spin, Alert, Button } from "antd";
import { FastBookingService } from "../../service/FastBookingService";

const ListFastBooking = () => {
  const [fastBookings, setFastBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedFastBooking, setSelectedFastBooking] = useState(null);

  useEffect(() => {
    FastBookingService.fetchFastBookings()
      .then((response) => {
        setFastBookings(response.data);
      })
      .catch(() => {
        setError("Không tồn tại đặt phòng nhanh.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const columns = [
    {
      title: "Mã đặt phòng nhanh",
      dataIndex: "fastBookingId",
      key: "fastBookingId",
    },
    {
      title: "Họ và tên",
      dataIndex: "fullName",
      key: "fullName",
    },
    {
      title: "Trạng thái",
      dataIndex: "fastBookingStatus",
      key: "fastBookingStatus",
    },
    {
      title: "Hành động",
      key: "actions",
      render: (_, record) => (
        <Button type="link" onClick={() => handleViewDetails(record)}>
          Xem chi tiết
        </Button>
      ),
    },
  ];

  const handleViewDetails = (fastBooking) => {
    setSelectedFastBooking(fastBooking);
  };

  const handleCloseDetails = () => {
    setSelectedFastBooking(null);
  };

  return (
    <div>
      <h1>Danh sách đặt phòng nhanh</h1>

      {loading ? (
        <div style={{ textAlign: "center", marginTop: 50 }}>
          <Spin size="large" />
        </div>
      ) : error ? (
        <Alert message={error} type="error" showIcon />
      ) : (
        <Table
          dataSource={fastBookings}
          columns={columns}
          rowKey="fastBookingId"
          pagination={{ pageSize: 5 }}
        />
      )}

      {selectedFastBooking && (
        <Modal
          title="Chi tiết đặt phòng nhanh"
          open={!!selectedFastBooking}
          onCancel={handleCloseDetails}
          footer={[
            <Button key="close" onClick={handleCloseDetails}>
              Đóng
            </Button>,
          ]}
        >
          <p>
            <strong>Mã đặt phòng nhanh:</strong> {selectedFastBooking.fastBookingId}
          </p>
          <p>
            <strong>Họ và tên:</strong> {selectedFastBooking.fullName}
          </p>
          <p>
            <strong>Số điện thoại:</strong> {selectedFastBooking.phoneNumber}
          </p>
          <p>
            <strong>Tên phòng:</strong> {selectedFastBooking.room}
          </p>
          <p>
            <strong>Loại đặt phòng:</strong> {selectedFastBooking.typeBooking}
          </p>
          <p>
            <strong>Ngày nhận phòng:</strong> {selectedFastBooking.checkin}
          </p>
          <p>
            <strong>Ngày trả phòng:</strong> {selectedFastBooking.checkout}
          </p>
          <p>
            <strong>Tổng giá:</strong> {selectedFastBooking.totalPrice} VND
          </p>
          <p>
            <strong>Trạng thái:</strong> {selectedFastBooking.fastBookingStatus}
          </p>
        </Modal>
      )}
    </div>
  );
};

export default ListFastBooking;
