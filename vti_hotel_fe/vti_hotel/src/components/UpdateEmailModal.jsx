import React, { useState } from "react";
import { Modal, Form, Input, Button, message } from "antd";
import { AccountService } from "../service/AccountService";

const UpdateEmailModal = ({ open, onClose }) => {
  const [form] = Form.useForm();
  const [isVerification, setIsVerification] = useState(false);
  const [email, setEmail] = useState(null);
  const [loading, setLoading] = useState(false);
  const [timer, setTimer] = useState(null);

  // Gửi email xác nhận
  const handleSendEmail = async (values) => {
    try {
      setLoading(true);
      const response = await AccountService.sendVerificationEmail(values.email);
      if (response?.data?.success) {
        message.success("Mã xác nhận đã được gửi!");
        setEmail(values.email);
        setIsVerification(true);

        // Bắt đầu đếm ngược 10s để tắt modal
        const timeout = setTimeout(() => {
          onClose();
        }, 10000);
        setTimer(timeout);
      } else {
        message.error("Không thể gửi mã xác nhận. Vui lòng thử lại.");
      }
    } catch (error) {
      message.error("Đã xảy ra lỗi. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  // Xác minh mã
  const handleVerifyCode = async (values) => {
    try {
      setLoading(true);
      const response = await AccountService.verifyCode({
        email,
        code: values.code,
      });
      if (response?.data?.success) {
        message.success("Email đã được cập nhật thành công!");
        onClose();
        clearTimeout(timer);
      } else {
        message.error("Mã xác nhận không chính xác. Vui lòng thử lại.");
      }
    } catch (error) {
      message.error("Đã xảy ra lỗi. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal
      title={isVerification ? "Xác minh mã" : "Cập nhật email"}
      open={open}
      onCancel={onClose}
      footer={null}
    >
      <Form
        form={form}
        layout="vertical"
        onFinish={isVerification ? handleVerifyCode : handleSendEmail}
      >
        {!isVerification ? (
          <>
            <Form.Item
              label="Email mới"
              name="email"
              rules={[
                { required: true, message: "Vui lòng nhập email mới!" },
                { type: "email", message: "Email không hợp lệ!" },
              ]}
            >
              <Input />
            </Form.Item>
          </>
        ) : (
          <>
            <Form.Item
              label="Mã xác nhận"
              name="code"
              rules={[
                { required: true, message: "Vui lòng nhập mã xác nhận!" },
              ]}
            >
              <Input />
            </Form.Item>
          </>
        )}

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            {isVerification ? "Xác minh" : "Gửi mã xác nhận"}
          </Button>
          <Button style={{ marginLeft: 10 }} onClick={onClose}>
            Hủy
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default UpdateEmailModal;