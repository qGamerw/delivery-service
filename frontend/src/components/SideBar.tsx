import React from 'react';
import { Drawer, Menu } from 'antd';
import { Link, useLocation } from 'react-router-dom';


interface SidebarProps {
  visible: boolean;
  onClose: () => void;
}

const SideBar: React.FC<SidebarProps> = ({ visible, onClose }) => {
  const location = useLocation();
  
  const handleMenuItemClick = () => {
    onClose();
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
        <Menu.Item key="/login">
          <Link to="/login">Вход</Link>
        </Menu.Item>
        <Menu.Item key="/info">
          <Link to="/info">Информация о курьере</Link>
        </Menu.Item>
      </Menu>
    </Drawer>
  );
};

export default SideBar;