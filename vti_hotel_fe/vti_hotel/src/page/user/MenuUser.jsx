// src/components/MenuUser.js
import React from 'react';
import { Menu } from 'antd';
import { Link } from 'react-router-dom';

const MenuUser = () => {
  return (
    <Menu mode="horizontal" style={{ lineHeight: '64px', background: '#003366' }}>
      <Menu.Item key="1">
        <Link to="/">Trang chủ</Link>
      </Menu.Item>
      <Menu.Item key="2">
        <Link to="/rooms">Phòng</Link>
      </Menu.Item>
      <Menu.Item key="3">
        <Link to="/services">Dịch vụ</Link>
      </Menu.Item>
      <Menu.Item key="4">
        <Link to="/contact">Liên hệ</Link>
      </Menu.Item>
    </Menu>
  );
};

export default MenuUser;
