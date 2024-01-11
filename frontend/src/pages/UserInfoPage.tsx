import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import styled from 'styled-components';
import {Button, Dropdown, Menu, Modal} from 'antd';
import { DownOutlined } from '@ant-design/icons';
import {RootState} from '../store';
import profileImage from "../images/user-profile-image.jpg";
import {Link} from 'react-router-dom';
import { updateUserStatus } from "../slices/authSlice";
import orderService from "../services/orderService";
import courierService from "../services/courierService";
import authService from "../services/authService";

const ProfileContainer = styled.div`
  padding: 20px;
  text-align: center;
`;

const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
`;

const ProfileInfo = styled.div`
  margin-top: 20px;
`;

const ProfileHeading = styled.h2`
  font-size: 24px;
  margin-bottom: 10px;
`;

const ProfileDetail = styled.p`
  font-size: 16px;
  margin-bottom: 5px;
`;

const ButtonContainer = styled.div`
  margin-top: 20px;
`;

const UserPage: React.FC = () => {
    const dispatch = useDispatch();
    const countOrder = useSelector((store: RootState) => store.order.countOrder);
    const user = useSelector((store: RootState) => store.auth.user);
    useEffect(() => {
        orderService.getCountOrder(dispatch);
        authService.getDetails(dispatch);
    }, []);

    
    const handleStatusChange = (selectedStatus: string) => {
        courierService.updateStatus(selectedStatus);
        dispatch(updateUserStatus(selectedStatus));
    };

    const statusOptions = ['FREE', 'ACTIVE', 'INACTIVE'];

    return (
        <ProfileContainer>
            <ProfileImage src={profileImage} alt="User Profile"/>
            <ProfileInfo>
                <ProfileHeading>Профиль пользователя</ProfileHeading>
                {user ? (
                    <>
                        <ProfileDetail>
                            <strong>Логин:</strong> {user.username}
                        </ProfileDetail>
                        <ProfileDetail>
                            <strong>Email:</strong> {user.email}
                        </ProfileDetail>
                        <ProfileDetail>
                            <strong>Count order:</strong> {countOrder}
                        </ProfileDetail>
                    </>
                ) : (
                    <p>Информации о пользователе нет</p>
                )}
            </ProfileInfo>
            <ButtonContainer>
                <Button type="primary">
                    <Link to="/all-orders">Посмотреть прошлые заказы</Link>
                </Button>
                <ButtonContainer>
                    <Dropdown overlay={
                        <Menu onClick={(e) => handleStatusChange(e.key as string)}>
                            {statusOptions.map((status) => (
                                <Menu.Item key={status}>{status}</Menu.Item>
                            ))}
                        </Menu>
                    }>
                        <Button>
                            Изменить статус: {user?.status} <DownOutlined />
                        </Button>
                    </Dropdown>
                </ButtonContainer>
            </ButtonContainer>
        </ProfileContainer>
    );
};

export default UserPage;
