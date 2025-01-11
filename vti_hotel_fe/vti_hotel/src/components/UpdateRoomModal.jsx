import React, { useState } from "react";
import { Modal, Form, Input, InputNumber, Button, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { MoreService } from "../service/MoreService";
import { toast, ToastContainer } from "react-toastify";

const UpdateRoomModal = ({ visible, onCancel, onUpdate, roomData }) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [image, setImage] = useState(null)

  const hanldeUpload = async (file) =>{
    const formData = new FormData();
    formData.append("folder", "image_room")
    formData.append("file", file)

    try{
      setLoading(true)
      const response = await MoreService.uploadImage(formData)
      return response.data.url
    }catch(error){
      toast.error("Failed")
    }finally{
      setLoading(false)
    }
  }

  const handleFinish = async (values) => {
    try{
      setLoading(true)
      let imageUrl = roomData?.imageRoom
      if (image) {
        imageUrl = await hanldeUpload(image)
      }

      const updateRoomValues = {...values, imageRoom: imageUrl}
      onUpdate(updateRoomValues)
      form.resetFields();
    }catch(err){
      toast.error(err)
    }finally{
      setLoading(false)
    }
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

        <Form.Item label="Ảnh mới">
          <Upload
          beforeUpload={(file) => {
            setImage(file)
            return false;
          }}
          maxCount={1}
          >
            <Button icon={<UploadOutlined />}>Tải ảnh lên</Button>
          </Upload>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }} loading={loading}>
            Cập nhật
          </Button>
        </Form.Item>
      </Form>
      <ToastContainer/>
    </Modal>
  );
};

export default UpdateRoomModal;
