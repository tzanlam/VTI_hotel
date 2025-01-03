import React, { useState } from "react";
import { Modal, Form, Input, Button, Image } from "antd";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ConfirmEmail from "./ConfirmEmail";
import { AccountService } from "../service/AccountService";
import logo from "../asset/image/logo.jpg"

const RegisterModal = ({ isVisible, onClose }) => {
  const [isConfirmVisible, setIsConfirmVisible] = useState(false);
  const [newAccountId, setNewAccountId] = useState(null);

  const handleRegister = async (registerRequest) => {
    try {
      const response = await AccountService.register(registerRequest);
      setNewAccountId(response.data.accountId);
      toast.success("Đăng ký thành công. Vui lòng xác nhận email!");
      setIsConfirmVisible(true);
    } catch (error) {
      toast.error("Lỗi đăng ký, vui lòng thử lại");
    }
  };

  return (
    <>
      <Modal
        open={isVisible}
        title="Đăng ký tài khoản"
        onCancel={onClose}
        footer={null}
      >
        <div className="login-modal-content">
        <Image src={logo} className="logo" alt="Hotel Logo" />
        <Form
          onFinish={handleRegister}
          layout="vertical"
        >
          <Form.Item
            label="Tên đăng nhập"
            name="username"
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
          <Form.Item
            label="Email"
            name="email"
            rules={[
              { required: true, message: "Vui lòng nhập email!" },
              { type: "email", message: "Email không hợp lệ!" },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Số điện thoại"
            name="phoneNumber"
            rules={[{ required: true, message: "Vui lòng nhập số điện thoại!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" block>
              Đăng ký
            </Button>
          </Form.Item>
        </Form>
        </div>
      </Modal>

      <ConfirmEmail
        isVisible={isConfirmVisible}
        accountId={newAccountId}
        onClose={() => setIsConfirmVisible(false)}
      />
    </>
  );
};

export default RegisterModal;
