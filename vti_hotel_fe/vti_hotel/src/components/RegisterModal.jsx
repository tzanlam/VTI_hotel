import React, { useState } from "react";
import { Modal, Form, Input, Button, Image } from "antd";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ConfirmEmail from "./ConfirmEmail";
import { AccountService } from "../service/AccountService";
import logo from "../asset/image/logo.jpg";

const RegisterModal = ({ isVisible, onClose }) => {
  const [isConfirmVisible, setIsConfirmVisible] = useState(false);
  const [newAccountId, setNewAccountId] = useState(null);
  const [formRegister] = Form.useForm();

  const handleRegister = async (formRegisterData) => {
    try {
      const response = await AccountService.register(formRegisterData);
      setNewAccountId(response.data.accountId);
      toast.success("Đăng ký thành công. Vui lòng xác nhận email!");
      setIsConfirmVisible(true);
      formRegister.resetFields(); // Reset form sau khi đăng ký thành công
    } catch (error) {
      const errorMessage = error.response?.data?.message || "Lỗi đăng ký, vui lòng thử lại";
      if (errorMessage.includes("Email đã được sử dụng")) {
        formRegister.setFields([
          {
            name: "email",
            errors: ["Email này đã được sử dụng!"],
          },
        ]);
      } else {
        toast.error(errorMessage);
      }
    }
  };

  return (
    <>
      <Modal open={isVisible} title="Đăng ký tài khoản" onCancel={onClose} footer={null}>
        <div className="login-modal-content">
          <Image src={logo} className="logo" alt="Hotel Logo" />
          <Form form={formRegister} onFinish={handleRegister} layout="vertical">
            <Form.Item label="Tên đăng nhập" name="username" rules={[{ required: true, message: "Vui lòng nhập tên đăng nhập!" }]}>
              <Input />
            </Form.Item>
            <Form.Item label="Mật khẩu" name="password" rules={[{ required: true, message: "Vui lòng nhập mật khẩu!" }]}>
              <Input.Password />
            </Form.Item>

            {/* Họ và tên */}
            <Form.Item label="Họ và tên" name="fullName" rules={[{ required: true, message: "Vui lòng nhập họ và tên!" }]}>
              <Input />
            </Form.Item>

            {/* Email */}
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

            {/* Số điện thoại */}
            <Form.Item label="Số điện thoại" name="phoneNumber" rules={[{ required: true, message: "Vui lòng nhập số điện thoại!" }]}>
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

      <ConfirmEmail isVisible={isConfirmVisible} accountId={newAccountId} onClose={() => setIsConfirmVisible(false)} />
    </>
  );
};

export default RegisterModal;
