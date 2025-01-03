import React from "react";
import { Menu } from "antd";
import { Link } from "react-router-dom";

const MenuUser = () => {
  const menuItems = [
    { key: "1", label: <Link to="/">Trang chủ</Link> },
    { key: "2", label: <Link to="/rooms">Phòng</Link> },
    { key: "3", label: <Link to="/booking">Đặt phòng</Link> },
    { key: "4", label: <Link to="/contact">Liên hệ</Link> },
  ];

  return (
    <Menu
      mode="horizontal"
      style={{ lineHeight: "64px", background: "#003366" }}
      items={menuItems} // Use the items prop instead of hardcoding Menu.Item
    />
  );
};

export default MenuUser;
