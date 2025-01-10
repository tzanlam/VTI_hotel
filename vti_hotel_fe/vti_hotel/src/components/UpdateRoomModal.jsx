import React from "react";
import { Modal, Form, Input, InputNumber, Button, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";

const UpdateRoomModal = ({ visible, onCancel, onUpdate, roomData }) => {
  const [form] = Form.useForm();

  const handleFinish = (values) => {
    onUpdate(values);
    form.resetFields();
  };

  return (
    <Modal
      title="Cập nhật thông tin phòng"
      open={visible}
      onCancel={onCancel}
      footer={null}
    >
      <Form
        form={form}
        layout="vertical"
        initialValues={roomData}
        onFinish={handleFinish}
      >
        <Form.Item
          name="roomName"
          label="Tên phòng"
          rules={[{ required: true, message: "Tên phòng là bắt buộc!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="description"
          label="Mô tả"
          rules={[{ required: true, message: "Mô tả là bắt buộc!" }]}
        >
          <Input.TextArea rows={4} />
        </Form.Item>

        <Form.Item
          name="quantity"
          label="Số lượng"
          rules={[{ required: true, message: "Số lượng là bắt buộc!" }]}
        >
          <InputNumber min={1} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item
          name="priceDay"
          label="Giá ngày"
          rules={[{ required: true, message: "Giá ngày là bắt buộc!" }]}
        >
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item
          name="priceNight"
          label="Giá đêm"
          rules={[{ required: true, message: "Giá đêm là bắt buộc!" }]}
        >
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item
          name="priceFirstHour"
          label="Giá giờ đầu"
          rules={[{ required: true, message: "Giá giờ đầu là bắt buộc!" }]}
        >
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item
          name="priceNextHour"
          label="Giá giờ tiếp theo"
          rules={[{ required: true, message: "Giá giờ tiếp theo là bắt buộc!" }]}
        >
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item name="imageRoom" label="Hình ảnh">
          <Upload
            beforeUpload={() => false}
            listType="picture"
            accept="image/*"
          >
            <Button icon={<UploadOutlined />}>Tải ảnh lên</Button>
          </Upload>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Cập nhật
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default UpdateRoomModal;
