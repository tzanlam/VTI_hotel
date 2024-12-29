import { Button, DatePicker, Form, Input, Modal, Select, Upload } from "antd";
import React, { useState } from "react";
import { AccountService } from "../service/AccountService";
import moment from "moment";
import { toast } from "react-toastify";
import { UploadOutlined } from "@ant-design/icons";
import { Option } from "antd/es/mentions";
import { MoreService } from "../service/MoreService";

const UpdateAccountModal = ({ open, onClose, onSuccess, account }) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [image, setImage] = useState(null);

  const handleUpload = async (file) => {
    const folder = "image_card";
    try {
      setLoading(true);
      const response = await MoreService.uploadImage(file, folder);
      console.log(response);
      
      toast.success("chọn ảnh thành công");
    } catch (error) {
      toast.error("Xảy ra lỗi không mong muốn");
    } finally {
      setLoading(false);
    }
  };
  const handleSubmit = async (accountRequest) => {
    try {
      setLoading(true);
      accountRequest.imageCard = image;
      const response = AccountService.updateAccount(
        account.accountId,
        accountRequest
      );
      if (response) {
        toast.success("Bạn đã cập nhật tài khoản thành công");
      } else {
        toast.error("Cập nhật thông tin thất bại");
      }
    } catch {
      toast.error("xảy ra lỗi không xác định");
    } finally {
      setLoading(false);
    }
  };
  return (
    <Modal
      title="Cập nhật tài khoản"
      open={open}
      onCancel={onClose}
      footer={null}
    >
      <Form
        form={form}
        layout="vertical"
        initialValues={{
          fullName: account?.fullName,
          email: account?.email,
          phoneNumber: account?.phoneNumber,
          birthDate: account?.birthDate ? moment(account.birthDate) : null,
          gender: account?.gender,
          imageCard: account?.imageCard,
        }}
        onFinish={handleSubmit}
      >
        <Form.Item
          label="Họ tên"
          name="fullName"
          rules={[{ required: true, message: "Vui lòng nhập họ tên!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Email"
          name="email"
          rules={[{ required: true, message: "Vui lòng nhập email!" }]}
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

        <Form.Item
          label="Ngày sinh"
          name="birthDate"
          rules={[{ required: true, message: "Vui lòng nhập ngày sinh!" }]}
        >
          <DatePicker format="YYYY-MM-DD" />
        </Form.Item>

        <Form.Item
          label="Giới tính"
          name="gender"
          rules={[{ required: true, message: "Vui lòng chọn giới tính!" }]}
        >
          <Select>
            <Option value="MALE">Nam</Option>
            <Option value="FEMALE">Nữ</Option>
          </Select>
        </Form.Item>

        <Form.Item label="Ảnh thẻ" name="imageCard">
          <Upload
            showUploadList={false}
            beforeUpload={(file) => {
              handleUpload(file);
              return false; // Prevent auto upload
            }}
          >
            <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
          </Upload>
          {image && (
            <img
              src={image}
              alt="Ảnh thẻ"
              style={{ width: "100px", marginTop: "10px" }}
            />
          )}
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            Lưu
          </Button>
          <Button style={{ marginLeft: 10 }} onClick={onClose}>
            Hủy
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default UpdateAccountModal;