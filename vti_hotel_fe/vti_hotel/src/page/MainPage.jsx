import React from 'react';
import LoginModal from '../components/LoginModal';
import '../asset/css/MainPage.css';
import { Footer } from 'antd/es/layout/layout';

const MainPage = () => {
    return (
        <div className="main-page">
            {/* Header */}
            <header className="main-header">
                <h1>Huy Phương Hotel</h1>
                <LoginModal />
            </header>

            {/* Content */}
            <main className="main-content">
                <p>Chào mừng đến với Huy Phương Hotel!</p>
                <p>Hãy tận hưởng kỳ nghỉ tuyệt vời của bạn với dịch vụ đẳng cấp của chúng tôi.</p>
            </main>

            {/* Footer */}
            <Footer style={{ textAlign: "center" }}>
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
      </Footer>
        </div>
    );
};

export default MainPage;