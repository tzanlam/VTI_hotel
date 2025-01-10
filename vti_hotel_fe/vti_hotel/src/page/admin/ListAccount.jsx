import React, { useEffect, useState } from "react";
import { Table, Modal, Button, Spin } from "antd";
import { AccountService } from "../../service/AccountService";

const ListAccount = () => {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedAccount, setSelectedAccount] = useState(null); // Tài khoản được chọn
  const [isModalVisible, setIsModalVisible] = useState(false);

  // Fetch danh sách tài khoản từ API
  useEffect(() => {
    AccountService.fetchAccounts()
      .then((response) => {
        if (response.data) {
          setAccounts(response.data);
        }
      })
      .catch(() => {
        console.error("Lỗi khi tải danh sách tài khoản.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  // Cột của bảng
  const columns = [
    {
      title: "ID",
      dataIndex: "accountId",
      key: "accountId",
    },
    {
      title: "Họ và Tên",
      dataIndex: "fullName",
      key: "fullName",
    },
    {
      title: "Trạng thái",
      dataIndex: "accountStatus",
      key: "accountStatus",
    },
    {
      title: "Hành động",
      key: "action",
      render: (_, record) => (
        <Button
          type="link"
          onClick={() => handleShowDetails(record)}
        >
          Xem chi tiết
        </Button>
      ),
    },
  ];

  // Hiển thị modal chi tiết tài khoản
  const handleShowDetails = (account) => {
    setSelectedAccount(account);
    setIsModalVisible(true);
  };

  // Đóng modal
  const handleModalClose = () => {
    setSelectedAccount(null);
    setIsModalVisible(false);
  };

  return (
    <div>
      <h1>Danh sách tài khoản</h1>
      {loading ? (
        <Spin size="large" />
      ) : (
        <Table
          columns={columns}
          dataSource={accounts}
          rowKey="accountId"
          pagination={{ pageSize: 10 }}
        />
      )}

      {/* Modal hiển thị chi tiết */}
      <Modal
        title="Thông tin tài khoản"
        visible={isModalVisible}
        onCancel={handleModalClose}
        footer={[
          <Button key="close" onClick={handleModalClose}>
            Đóng
          </Button>,
        ]}
      >
        {selectedAccount && (
          <div>
            <p><strong>ID:</strong> {selectedAccount.accountId}</p>
            <p><strong>Họ và Tên:</strong> {selectedAccount.fullName}</p>
            <p><strong>Tên đăng nhập:</strong> {selectedAccount.username}</p>
            <p><strong>Email:</strong> {selectedAccount.email}</p>
            <p><strong>Số điện thoại:</strong> {selectedAccount.phoneNumber}</p>
            <p><strong>Ngày sinh:</strong> {selectedAccount.birthDate}</p>
            <p><strong>Giới tính:</strong> {selectedAccount.gender}</p>
            <p><strong>Hạng tài khoản:</strong> {selectedAccount.accountLevel}</p>
            <p><strong>Tổng số tiền đã chi:</strong> {selectedAccount.amountSpent}</p>
            <p><strong>Điểm tích lũy:</strong> {selectedAccount.cumulativePoint}</p>
            <p><strong>Trạng thái:</strong> {selectedAccount.accountStatus}</p>
            <p><strong>Vai trò:</strong> {selectedAccount.role}</p>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default ListAccount;
