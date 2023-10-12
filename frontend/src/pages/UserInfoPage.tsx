import React from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { Button, Modal } from 'antd';
import { RootState } from '../store';
import profileImage from "../images/user-profile-image.jpg";

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
  const user = useSelector((store: RootState) => store.auth.user);
  return (
    <ProfileContainer>
      <ProfileImage src={profileImage} alt="User Profile" />
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
          </>
        ) : (
          <p>Информации о пользователе нет</p>
        )}
      </ProfileInfo>
      <ButtonContainer>
        <Button type="primary">
          Посмотреть прошлые заказы
        </Button>
      </ButtonContainer>
    </ProfileContainer>
  );
};

export default UserPage;
