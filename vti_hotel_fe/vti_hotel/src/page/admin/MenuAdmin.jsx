import { Menu } from 'antd';
import React from 'react';
import { Link } from 'react-router-dom';

const MenuAdmin = () => {
    const menuItems = [
        {key: "1", label: <Link to={"/accounts"}>Quản lí tài khoản</Link>},
        {key: "2", label: <Link to={"/roomManager"}>Quản lí phòng</Link>},
        {key: "3", label: <Link to={"/listBooking"}>Qunản lí đặt phòng</Link>},
        {key: "4", label: <Link to={"/listFastBooking"}>Quản lí đặt phòng nhanh</Link>},
        {key: "5", label: <Link to={"/listComment"}>Quản lí phản hồi</Link>},
    ]
    return (
        <Menu
        mode='horizontal'
        style={{lineHeight: "64px", background: "#003366"}}
        items={menuItems}
        />
    );
};

export default MenuAdmin;