import React, { useState } from "react";
import { Modal, Image, Form, Input, Button, Checkbox } from "antd";
// import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import logo from "../asset/image/logo.jpg";
import { MoreService } from "../service/MoreService";
import { ToastContainer } from 'react-toastify';
import "../asset/css/LoginModal.css";

const LoginModal = () => {
//   const navigate = useNavigate();
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isRegister, setIsRegister] = useState(false);

  const handleOpenModal = () => setIsModalVisible(true);
  const handleCloseModal = () => setIsModalVisible(false);

  const handleLogin = async (loginRequest) => {
    try {
      const response = await MoreService.login(loginRequest);
      localStorage.setItem("token", response.data.token);
      console.log(response);
      
      toast.success("Đăng nhập thành công");
    //   navigate("/");
      handleCloseModal();
    } catch (error) {
      toast.error("Sai thông tin đăng nhập hoặc mật khẩu");
      handleCloseModal();
    }
  };

  const handleRegister = async (registerRequest) => {
    try {
      await MoreService.register(registerRequest);
      toast.success("Đăng ký thành công, vui lòng đăng nhập");
      setIsRegister(false);
    } catch (error) {
      toast.error("Lỗi đăng ký, vui lòng thử lại");
    }
  };

  const handleForgotPassword = () => {
    toast.info("Chức năng quên mật khẩu đang được phát triển");
  };

  return (
    <>
      <Button type="primary" onClick={handleOpenModal}>
        Đăng nhập
      </Button>
      <Modal
        open={isModalVisible}
        title={isRegister ? "Đăng ký tài khoản" : "Đăng nhập"}
        onCancel={handleCloseModal}
        footer={null}
      >
        <div className="login-modal-content">
          <Image src={logo} className="logo" alt="Hotel Logo" />
          <Form
            onFinish={(values) => {
              if (isRegister) {
                handleRegister(values);
              } else {
                handleLogin(values);
              }
            }}
            layout="vertical"
          >
            <Form.Item
              label="Tên đăng nhập"
              name="identifier"
              rules={[
                { required: true, message: "Vui lòng nhập tên đăng nhập!" },
              ]}
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
            {!isRegister && (
              <Form.Item name="remember" valuePropName="checked">
                <Checkbox>Nhớ tôi</Checkbox>
              </Form.Item>
            )}
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                {isRegister ? "Đăng ký" : "Đăng nhập"}
              </Button>
            </Form.Item>
            <Form.Item>
              {isRegister ? (
                <Button type="link" onClick={() => setIsRegister(false)}>
                  Đã có tài khoản? Đăng nhập
                </Button>
              ) : (
                <>
                  <Button type="link" onClick={handleForgotPassword}>
                    Quên mật khẩu?
                  </Button>
                  <Button type="link" onClick={() => setIsRegister(true)}>
                    Chưa có tài khoản? Đăng ký
                  </Button>
                </>
              )}
            </Form.Item>
          </Form>
        </div>
      </Modal>
      <ToastContainer />
    </>
  );
};

export default LoginModal;
