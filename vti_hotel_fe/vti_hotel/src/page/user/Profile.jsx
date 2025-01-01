import React, { useEffect, useState } from "react";
import { AccountService } from "../../service/AccountService";
import { toast } from "react-toastify";
import { Card, Row, Col, Avatar, Typography, Divider, Button } from "antd";
import { useParams } from "react-router-dom";
import { UserOutlined } from "@ant-design/icons";
import avt_default from "../../asset/image/avt_default.jpg";
import UpdateAccountModal from "../../components/UpdateAccountModal";

const { Title, Text } = Typography;
const Profile = () => {
  const { accountId } = useParams();
  const [account, setAccount] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isModalVisible, setIsModalVisible] = useState(false)

  useEffect(() => {
    const fetchAccount = async () => {
      try {
        const response = await AccountService.fetchAccountById(accountId);
        if (response) {
            console.log(response);
          setAccount(response.data);
        } else {
          toast.error("Có lỗi xảy ra");
        }
      } catch (error) {
        setLoading(false);
        toast.error("Có lỗi xảy ra khi tải thông tin cá nhân");
      } finally {
        setLoading(false);
      }
    };

    fetchAccount();
  }, [accountId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  const handleCloseModal = () => {setIsModalVisible(false)}
  
  const handleOpenModal = () => {setIsModalVisible(true)}

  const handleUpdateSuccess = (accountRequest) => {
    setAccount(accountRequest)
    setIsModalVisible(false);
    toast.success("Thông tin đã được cập nhật")
    // window.location.reload()
  }
  return (
    <Card style={{ maxWidth: 600, margin: "20px auto", padding: 20, borderRadius: 10 }}>
      <Row justify="center" align="middle">
        <Col>
          <Avatar
            size={100}
            src={account.imageCard || avt_default}
            icon={<UserOutlined />}
          />
        </Col>
      </Row>
      <Divider />
      <Title level={3} style={{ textAlign: "center" }}>
        Thông Tin Cá Nhân
      </Title>
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        <Col span={12}>
          <Text strong>Mã tài khoản:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.accountId}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Họ tên:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.fullName}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Username:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.username}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Email:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.email}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Số điện thoại:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.phoneNumber}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Ngày sinh:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.birthDate}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Giới tính:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.gender}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Cấp độ tài khoản:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.accountLevel}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Số tiền đã chi tiêu:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.amountSpent}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Số điểm đã tích lũy:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.cumulativePoint}</Text>
        </Col>
  
        <Col span={12}>
          <Text strong>Trạng thái tài khoản:</Text>
        </Col>
        <Col span={12}>
          <Text>{account.accountStatus}</Text>
        </Col>
      </Row>
      <Divider />
      <Row justify="center">
        <Button type="primary" onClick={handleOpenModal}>
          Chỉnh sửa thông tin
        </Button>
      </Row>
      <UpdateAccountModal
      open={isModalVisible}
      onClose={handleCloseModal}
      onSuccess={handleUpdateSuccess}
      account={account}
      />
    </Card>
  );
};

export default Profile;