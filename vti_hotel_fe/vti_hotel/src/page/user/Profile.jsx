import React, { useEffect, useState } from 'react';
import { AccountService } from '../../service/AccountService';
import { toast } from 'react-toastify';
import { Image } from 'antd';
import { useParams } from 'react-router-dom';
import avt_default from '../../asset/image/avt_default.jpg'

const Profile = () => {
    const {accountId} = useParams();
    const [account, setAccount] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchAccount = async () => {
            try {
                const data = await AccountService.fetchAccountById(accountId);
                if (!data) { // Kiểm tra dữ liệu trả về
                    toast.error("Tài khoản không tồn tại");
                    setLoading(false);
                    return;
                }
                setAccount(data); // Lưu dữ liệu tài khoản vào state
                setLoading(false); // Đặt trạng thái loading thành false
                toast.success("Xem thông tin cá nhân thành công");
            } catch (error) {
                setLoading(false);
                toast.error("Có lỗi xảy ra khi tải thông tin cá nhân");
            }
        };
        

        fetchAccount(); // Gọi hàm fetchAccount
    }, [accountId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!account) {
        return <div>Account not found</div>;
    }

    return (
        <div>
            <h1>Profile</h1>
            <div>
                <strong>Account ID:</strong> {account.accountId}
            </div>
            <div>
                <strong>Full Name:</strong> {account.fullName}
            </div>
            <div>
                <strong>Username:</strong> {account.username}
            </div>
            <div>
                <strong>Email:</strong> {account.email}
            </div>
            <div>
                <strong>Phone Number:</strong> {account.phoneNumber}
            </div>
            <div>
                <strong>Birth Date:</strong> {account.birthDate}
            </div>
            <div>
                <strong>Image Card:</strong>
                {/* Hiển thị ảnh từ imageCard hoặc ảnh mặc định nếu không có */}
                <Image
                    src={account.imageCard || avt_default} // Sử dụng ảnh mặc định nếu không có imageCard
                    alt="Image Card"
                    width={200} // Chỉnh kích thước ảnh theo nhu cầu
                />
            </div>
            <div>
                <strong>Gender:</strong> {account.gender}
            </div>
            <div>
                <strong>Account Level:</strong> {account.accountLevel}
            </div>
            <div>
                <strong>Amount Spent:</strong> {account.amountSpent}
            </div>
            <div>
                <strong>Cumulative Points:</strong> {account.cumulativePoint}
            </div>
            <div>
                <strong>Account Status:</strong> {account.accountStatus}
            </div>
        </div>
    );
};

export default Profile;
