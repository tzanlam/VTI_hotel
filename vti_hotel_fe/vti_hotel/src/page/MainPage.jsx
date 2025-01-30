import React, { useState } from 'react';
import LoginModal from '../components/LoginModal';
import MenuUser from './user/MenuUser';  // Import MenuUser
import '../asset/css/MainPage.css';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';
import main_img from '../asset/image/main_img.png'
import MenuAdmin from './admin/MenuAdmin';

const { Header, Footer, Content } = Layout;

const MainPage = () => {
  const location = useLocation();
  const role = localStorage.getItem("role");
  const [isModalVisible, setIsModalVisible] = useState(false); // Di chuyển từ LoginModal.jsx

  return (
    <Layout className="main-page">
      {/* Header */}
      <Header className="main-header" style={{ background: "#003366" }}>
        <h1 style={{ color: 'white' }}>Huy Phương Hotel</h1>
        {role ? (role === "ADMIN" ? <MenuAdmin /> : <MenuUser />) : <MenuUser />}
        <LoginModal 
          isModalVisible={isModalVisible} 
          setIsModalVisible={setIsModalVisible} 
        />
      </Header>

      {/* Content */}
      <Content className="main-content">
        {location.pathname === '/' ? (        
          <div className='main-image-container'>
            <img
              src={main_img}
              alt='WELCOME TO HUY PHƯƠNG HOTEL'
              style={{ width: '100%', height: 'auto', borderRadius: '8px' }}
            />
          </div>
        ) : (
          <Outlet />
        )}
      </Content>

     {/* Footer */}
     <Footer style={{ textAlign: 'center', display: 'flex', justifyContent: 'space-between', padding: '20px' }}>
        <div className='footer-info' style={{ flex: 1, textAlign: 'left', backgroundColor: '#f0f2f5', padding: '20px', borderRadius: '8px' }}>
          <h3>Giới thiệu khách sạn</h3>
          <p>Khách sạn Huy Phương là khách sạn 3 sao, cung cấp dịch vụ nghỉ dưỡng tốt nhất cho khách hàng. Với đội ngũ nhân viên chuyên nghiệp, chúng tôi cam kết mang đến trải nghiệm tuyệt vời cho quý khách.</p>
          <p>Đến với Huy Phương, quý khách sẽ được thưởng thức các món ăn ngon, thư giãn tại spa, gym, hồ bơi và các hoạt động vui chơi giải trí khác.</p>
        </div>
        <div className='footer-contact' style={{ flex: 1, textAlign: 'left', backgroundColor: '#f0f2f5', padding: '20px', borderRadius: '8px', marginLeft: '20px' }}>
          <h3>Thông tin liên hệ</h3>
          <p><b>Địa chỉ: </b> 1518, Dương Công Khi, Ấp 1, Xuân Thới Thượng, Hóc Môn, HCM, VN</p>
          <p><b>Điện thoại:</b> 0898101075</p>
          <p><b>Email: </b> huyphuonghotel@gmail.com </p>
          <h3>Thông tin liên hệ</h3>
          <p><b>Địa chỉ: </b> 1518, Dương Công Khi, Ấp 1, Xuân Thới Thượng, Hóc Môn, HCM, VN</p>
          <p><b>Điện thoại:</b> 0898101075</p>
          <p><b>Email: </b> huyphuonghotel24h@gmail.com</p>
        </div>
        <div style={{ flex: 1, textAlign: 'right' }}>
          <h3>Vị trí khách sạn</h3>
          <div>
            <iframe
              title="Bản đồ khách sạn"
              src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d3918.3709427725935!2d106.56032151057424!3d10.859365389249838!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1729952981863!5m2!1svi!2s"
              width="100%"
              height="300"
              style={{ border: 0 }}
              allowFullScreen=""
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            ></iframe>
          </div>
        </div>
      </Footer>

    </Layout>
  );
};

export default MainPage;
