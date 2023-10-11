import React from 'react';
import { Drawer, Menu } from 'antd';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import authService from "../services/authService";
import { useDispatch, useSelector } from "react-redux";


interface SidebarProps {
  visible: boolean;
  onClose: () => void;
}

const SideBar: React.FC<SidebarProps> = ({ visible, onClose }) => {
  const location = useLocation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleMenuItemClick = () => {
    onClose();
  };

  const isAuth = useSelector((state: any) => state.auth.isAuth);

  const logout = () => {
    authService.logout(dispatch);
    navigate("/login")
  };

  return (
    <Drawer
      title="Меню"
      placement="left"
      closable={true}
      onClose={onClose}
      open={visible}
      width={250}
    >
      <Menu mode="vertical" onClick={handleMenuItemClick} selectedKeys={[location.pathname]}>
        <Menu.Item key="/">
          <Link to="/">Карта</Link>
        </Menu.Item>
        <Menu.Item key="/orders">
          <Link to="/orders">Все заказы</Link>
        </Menu.Item>
        <Menu.Item key="/active-orders">
          <Link to="/active-orders">Активные заказы</Link>
        </Menu.Item>
        <Menu.Item key="/info">
          <Link to="/info">Информация о курьере</Link>
        </Menu.Item>
        {isAuth ? (
          <Menu.Item key="/logout" onClick={logout}>
            Выход из аккаунта
          </Menu.Item>
        ) : (
          <Menu.Item key="/login">
            <Link to="/login">Вход</Link>
          </Menu.Item>
        )}
      </Menu>
    </Drawer>
  );
};

export default SideBar;