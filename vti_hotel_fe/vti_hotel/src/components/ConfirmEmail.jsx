import React from "react";
import { Modal, Form, Input, Button } from "antd";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { MoreService } from "../service/MoreService";

const ConfirmEmail = ({ isVisible, accountId, onClose }) => {
  const navigate = useNavigate();

  const handleConfirmEmail = async ({ code }) => {
    try {
      await MoreService.confirmEmail(accountId, code);
      toast.success("Xác nhận email thành công!");
      navigate(`/myProfile/${accountId}`);
      onClose();
    } catch (error) {
      toast.error("Mã xác nhận không chính xác, vui lòng thử lại!");
    }
  };

  return (
    <Modal
      open={isVisible}
      title="Xác nhận Email"
      onCancel={onClose}
      footer={null}
    >
      <Form
        onFinish={handleConfirmEmail}
        layout="vertical"
      >
        <Form.Item
          label="Nhập mã xác nhận"
          name="code"
          rules={[{ required: true, message: "Vui lòng nhập mã xác nhận!" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Xác nhận
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ConfirmEmail;
