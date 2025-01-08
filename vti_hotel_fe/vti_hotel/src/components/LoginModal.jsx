import React, { useState } from "react";
import { Modal, Image, Form, Input, Button, Checkbox, Dropdown, Avatar } from "antd";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import logo from "../asset/image/logo.jpg";
import { MoreService } from "../service/MoreService";
import { ToastContainer } from "react-toastify";
import "../asset/css/LoginModal.css";
import avt_default from "../asset/image/avt_default.jpg";
import { Link, useNavigate } from "react-router-dom";
import { AccountService } from "../service/AccountService";
import RegisterModal from "./RegisterModal";

const LoginModal = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [user, setUser] = useState(null);
  const [isRegisterVisible, setIsRegisterVisible] = useState(false);
  const navigate = useNavigate();

  const handleOpenModal = () => setIsModalVisible(true);
  const handleCloseModal = () => setIsModalVisible(false);

  const handleOpenRegisterModal = () => setIsRegisterVisible(true);
  const handleCloseRegisterModal = () => setIsRegisterVisible(false);

  const handleLogin = async (loginRequest) => {
    try {
      const response = await MoreService.login(loginRequest);
      localStorage.setItem("token", response.data.token);
      const accountData = await AccountService.fetchAccountById(
        response.data.accountId
      );
      if (accountData) {
        setUser({
          accountId: response.data.accountId,
          fullName: response.data.identifier,
          image: accountData.data.imageCard || avt_default,
        });
      }
      localStorage.setItem("user", JSON.stringify(accountData));
      toast.success("Đăng nhập thành công");
      if (accountData.data.role === "ADMIN") {
        navigate("/mainAdmin")
      }else{
        navigate("/")
      }
      handleCloseModal();
    } catch (error) {
      toast.error("Sai thông tin đăng nhập hoặc mật khẩu");
      handleCloseModal();
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setUser(null);
    toast.info("Đăng xuất thành công");
  };

  const handleForgotPassword = () => {
    toast.info("Chức năng quên mật khẩu đang được phát triển");
  };

  const userMenuItems = [
    {
      key: "1",
      label: <Link to={`/myProfile/${user?.accountId}`}>Thông tin cá nhân</Link>,
    },
    {
      key: "2",
      label: <Link to="/myBooking">Lịch sử đặt phòng</Link>,
    },
    {
      key: "3",
      label: (
        <a href="/" onClick={handleLogout}>
          Đăng xuất
        </a>
      ),
    },
  ];

  const userMenu = { items: userMenuItems };

  return (
    <>
      {user ? (
        <Dropdown menu={userMenu} placement="bottomRight" trigger={["click"]}>
          <div style={{ display: "flex", alignItems: "center", cursor: "pointer" }}>
            <Avatar
              src={user.image}
              alt={user.fullName}
              style={{ marginRight: "8px" }}
            />
            <span>{user.fullName}</span>
          </div>
        </Dropdown>
      ) : (
        <Button type="primary" onClick={handleOpenModal}>
          Đăng nhập
        </Button>
      )}

      <Modal
        open={isModalVisible}
        title="Đăng nhập"
        onCancel={handleCloseModal}
        footer={null}
      >
        <div className="login-modal-content">
          <Image src={logo} className="logo" alt="Hotel Logo" />
          <Form onFinish={handleLogin} layout="vertical">
            <Form.Item
              label="Tên đăng nhập"
              name="identifier"
              rules={[{ required: true, message: "Vui lòng nhập tên đăng nhập!" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Mật khẩu"
              name="password"
              rules={[{ required: true, message: "Vui lòng nhập mật khẩu!" }]}
            >
              <Input.Password />
            </Form.Item>
            <Form.Item name="remember" valuePropName="checked">
              <Checkbox>Nhớ tôi</Checkbox>
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Đăng nhập
              </Button>
            </Form.Item>
            <Form.Item>
              <Button type="link" onClick={handleForgotPassword}>
                Quên mật khẩu?
              </Button>
              <Button type="link" onClick={handleOpenRegisterModal}>
                Chưa có tài khoản? Đăng ký
              </Button>
            </Form.Item>
          </Form>
        </div>
      </Modal>

      <RegisterModal
        isVisible={isRegisterVisible}
        onClose={handleCloseRegisterModal}
      />

      <ToastContainer />
    </>
  );
};

export default LoginModal;
