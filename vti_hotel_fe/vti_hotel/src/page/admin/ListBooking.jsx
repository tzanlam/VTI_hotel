import React, { useState, useEffect } from "react";
import { Table, Modal, Spin, Alert, Button } from "antd";
import { BookingService }from "../../service/BookingService";

const ListBooking = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedBooking, setSelectedBooking] = useState(null);

  useEffect(() => {
    BookingService.fetchBookings()
      .then((response) => {
        setBookings(response.data);
      })
      .catch(() => {
        setError("Không thể tải danh sách đặt phòng.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const columns = [
    {
      title: "Mã đặt phòng",
      dataIndex: "bookingId",
      key: "bookingId",
    },
    {
      title: "Tên khách hàng",
      dataIndex: "account",
      key: "account",
    },
    {
      title: "Trạng thái",
      dataIndex: "bookingStatus",
      key: "bookingStatus",
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

  const handleViewDetails = (booking) => {
    setSelectedBooking(booking);
  };

  const handleCloseDetails = () => {
    setSelectedBooking(null);
  };

  return (
    <div>
      <h1>Danh sách đặt phòng</h1>

      {loading ? (
        <div style={{ textAlign: "center", marginTop: 50 }}>
          <Spin size="large" />
        </div>
      ) : error ? (
        <Alert message={error} type="error" showIcon />
      ) : (
        <Table
          dataSource={bookings}
          columns={columns}
          rowKey="bookingId"
          pagination={{ pageSize: 5 }}
        />
      )}

      {selectedBooking && (
        <Modal
          title="Chi tiết đặt phòng"
          open={!!selectedBooking}
          onCancel={handleCloseDetails}
          footer={[
            <Button key="close" onClick={handleCloseDetails}>
              Đóng
            </Button>,
          ]}
        >
          <p>
            <strong>Mã đặt phòng:</strong> {selectedBooking.bookingId}
          </p>
          <p>
            <strong>Tên khách hàng:</strong> {selectedBooking.account}
          </p>
          <p>
            <strong>Tên phòng:</strong> {selectedBooking.room}
          </p>
          <p>
            <strong>Loại đặt phòng:</strong> {selectedBooking.typeBooking}
          </p>
          <p>
            <strong>Ngày nhận phòng:</strong> {selectedBooking.checkIn}
          </p>
          <p>
            <strong>Ngày trả phòng:</strong> {selectedBooking.checkOut}
          </p>
          <p>
            <strong>Mã giảm giá:</strong> {selectedBooking.voucher }
          </p>
          <p>
            <strong>Tổng giá:</strong> {selectedBooking.totalPrice} VND
          </p>
          <p>
            <strong>Trạng thái:</strong> {selectedBooking.bookingStatus}
          </p>
        </Modal>
      )}
    </div>
  );
};

export default ListBooking;
