import React, { useState } from 'react';
import { Image, Form, Input, Button, Checkbox } from 'antd';
import { useNavigate } from 'react-router-dom';
import logo from "../asset/image/logo.jpg";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { MoreService } from '../service/MoreService';
import '../asset/css/LoginPage.css';

const LoginPage = () => {
    const navigate = useNavigate();
    const [isRegister, setIsRegister] = useState(false); // Trạng thái để chuyển đổi giữa Đăng nhập và Đăng ký

    // Xử lý đăng nhập
    const handleLogin = async (loginRequest) => {
        try {
            const response = await MoreService.login(loginRequest);
            localStorage.setItem("token", response.data.token);
            toast.success("Đăng nhập thành công");
            navigate("/home"); // Chuyển hướng đến trang chủ sau khi đăng nhập
        } catch (error) {
            console.error("Lỗi đăng nhập:", error);
            toast.error("Sai thông tin đăng nhập hoặc mật khẩu");
        }
    };

    // Xử lý đăng ký
    const handleRegister = async (registerRequest) => {
        try {
            const response = await MoreService.register(registerRequest);
            console.log(response);
            
            toast.success("Đăng ký thành công, vui lòng đăng nhập");
            setIsRegister(false); // Chuyển về trạng thái đăng nhập sau khi đăng ký thành công
        } catch (error) {
            console.error("Lỗi đăng ký:", error);
            toast.error("Lỗi đăng ký, vui lòng thử lại");
        }
    };

    // Xử lý quên mật khẩu
    const handleForgotPassword = () => {
        toast.info("Chức năng quên mật khẩu đang được phát triển");
    };

    return (
        <div className="login-page">
            <div className="login-container">
                <Image src={logo} className="logo" alt="Hotel Logo" />
                <h2>{isRegister ? "Đăng ký tài khoản" : "Đăng nhập"}</h2>

                <Form
                    className="login-form"
                    onFinish={isRegister ? handleRegister : handleLogin} // Gọi đúng hàm dựa vào trạng thái đăng ký hay đăng nhập
                >
                    {/* Tên đăng nhập */}
                    <Form.Item
                        label="Tên đăng nhập"
                        name="username"
                        rules={[
                            { required: true, message: 'Vui lòng nhập tên đăng nhập!' }
                        ]}
                    >
                        <Input />
                    </Form.Item>

                    {/* Mật khẩu */}
                    <Form.Item
                        label="Mật khẩu"
                        name="password"
                        rules={[
                            { required: true, message: 'Vui lòng nhập mật khẩu!' }
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>

                    {/* Checkbox "Nhớ tôi" chỉ hiển thị khi là trang Đăng nhập */}
                    {!isRegister && (
                        <Form.Item name="remember" valuePropName="checked">
                            <Checkbox>Nhớ tôi</Checkbox>
                        </Form.Item>
                    )}

                    {/* Nút đăng nhập hoặc đăng ký */}
                    <Form.Item className="form-buttons">
                        <Button type="primary" htmlType="submit">
                            {isRegister ? "Đăng ký" : "Đăng nhập"}
                        </Button>
                    </Form.Item>

                    {/* Nút chuyển đổi giữa Đăng nhập và Đăng ký */}
                    <Form.Item>
                        {isRegister ? (
                            <div>
                                <span>Bạn đã có tài khoản? </span>
                                <Button type="link" onClick={() => setIsRegister(false)}>Đăng nhập</Button>
                            </div>
                        ) : (
                            <div>
                                <span>Chưa có tài khoản? </span>
                                <Button type="link" onClick={() => setIsRegister(true)}>Đăng ký</Button>
                            </div>
                        )}
                    </Form.Item>

                    {/* Liên kết quên mật khẩu */}
                    {!isRegister && (
                        <Form.Item>
                            <Button type="link" onClick={handleForgotPassword}>Quên mật khẩu?</Button>
                        </Form.Item>
                    )}
                </Form>
            </div>
        </div>
    );
};

export default LoginPage;