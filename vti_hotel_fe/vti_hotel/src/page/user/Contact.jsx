import React, { useState } from "react";
import { Form, Select, Input, Button, Spin } from "antd";
import { toast } from "react-toastify";
import { MoreService } from "../../service/MoreService";

const { Option } = Select;
const { TextArea } = Input;

const Contact = () => {
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (values) => {
    const { email, subject, content } = values;
    setLoading(true);
    try {
      const response = await MoreService.sendMailSupported(email, subject, content);
      if (response) {
        toast.success("Gửi email thành công!");
      }
    } catch (error) {
      toast.error("Gửi email thất bại. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: "0 auto", padding: "20px" }}>
      <h1>Liên Hệ</h1>
      <Form layout="vertical" onFinish={handleSubmit}>
        {/* Email */}
        <Form.Item
          label="Email"
          name="email"
          rules={[
            { required: true, message: "Vui lòng nhập email." },
            { type: "email", message: "Email không hợp lệ." },
          ]}
        >
          <Input placeholder="Nhập email của bạn" />
        </Form.Item>

        {/* Chủ đề */}
        <Form.Item
          label="Chủ đề"
          name="subject"
          rules={[{ required: true, message: "Vui lòng chọn chủ đề." }]}
        >
          <Select placeholder="Chọn chủ đề">
            <Option value="phan_hoi">Phản hồi</Option>
            <Option value="khieu_nai">Khiếu nại</Option>
            <Option value="hoi_dap">Hỏi đáp</Option>
          </Select>
        </Form.Item>

        {/* Nội dung */}
        <Form.Item
          label="Nội dung"
          name="content"
          rules={[{ required: true, message: "Vui lòng nhập nội dung." }]}
        >
          <TextArea rows={4} placeholder="Nhập nội dung liên hệ của bạn" />
        </Form.Item>

        {/* Nút gửi */}
        <Form.Item>
          <Button type="primary" htmlType="submit" block disabled={loading}>
            {loading ? <Spin /> : "Gửi"}
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Contact;
