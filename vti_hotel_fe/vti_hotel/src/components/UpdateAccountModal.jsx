import { Button, DatePicker, Form, Input, Modal, Select, Upload } from "antd";
import React, { useState } from "react";
import dayjs from "dayjs"; // Sử dụng thư viện dayjs
import { AccountService } from "../service/AccountService";
import { toast } from "react-toastify";
import { UploadOutlined } from "@ant-design/icons";
import { MoreService } from "../service/MoreService";

const UpdateAccountModal = ({ open, onClose, onSuccess, account }) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [image, setImage] = useState(null);

  const handleUpload = async (file) => {
    const formData = new FormData();
    formData.append("folder", "image_card");
    formData.append("file", file);

    try {
      setLoading(true);
      const response = await MoreService.uploadImage(formData);
      return response.data.url;
    } catch (error) {
      toast.error("Xảy ra lỗi không mong muốn khi tải ảnh lên.");
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (values) => {
    try {
      setLoading(true);

      // Chuyển đổi ngày sinh từ DatePicker về chuỗi định dạng "YYYY-MM-DD"
      const birthDate = values.birthDate
        ? values.birthDate.format("YYYY-MM-DD")
        : null;

      // Giữ lại URL ảnh cũ nếu không có ảnh mới
      let imageUrl = account?.imageCard;
      if (image) {
        imageUrl = await handleUpload(image);
      }

      const updateValues = { ...values, birthDate, imageCard: imageUrl };

      const response = await AccountService.updateAccount(
        account.accountId,
        updateValues
      );

      if (response?.data) {
        toast.success("Cập nhật tài khoản thành công.");
        onSuccess(response.data);
        onClose();
      } else {
        toast.error("Cập nhật tài khoản thất bại.");
      }
    } catch (error) {
      toast.error("Xảy ra lỗi không xác định.");
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
          fullName: account?.fullName || "",
          email: account?.email || "",
          phoneNumber: account?.phoneNumber || "",
          birthDate: account?.birthDate
            ? dayjs(account.birthDate) // Chuyển đổi sang đối tượng dayjs
            : null,
          gender: account?.gender || "MALE",
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

        <Form.Item
          label="Ngày sinh"
          name="birthDate"
          rules={[{ required: true, message: "Vui lòng chọn ngày sinh!" }]}
        >
          <DatePicker format="YYYY-MM-DD" />
        </Form.Item>

        <Form.Item
          label="Giới tính"
          name="gender"
          rules={[{ required: true, message: "Vui lòng chọn giới tính!" }]}
        >
          <Select>
            <Select.Option value="MALE">Nam</Select.Option>
            <Select.Option value="FEMALE">Nữ</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="Ảnh mới">
          <Upload
            beforeUpload={(file) => {
              setImage(file);
              return false; // Ngăn tải ảnh tự động
            }}
            maxCount={1}
          >
            <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
          </Upload>
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
