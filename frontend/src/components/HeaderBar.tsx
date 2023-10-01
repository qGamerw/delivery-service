import { Layout, Button } from 'antd';
import React, { useState } from 'react';
import { MenuOutlined } from '@ant-design/icons';

const { Header } = Layout;

const HeaderBar: React.FC = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    return (
        <Header style={{ backgroundColor: 'white', textAlign: 'center'}}>
            <Button
                icon={<MenuOutlined />} 
                onClick={toggleSidebar}
                style={{ float: 'left' , marginTop: "15px", marginLeft: "-30px"  }} 
            />
            <h1 style={{ color: 'black', marginTop: "0px" }}>Мои карты</h1>
        </Header>
    );
};

export default HeaderBar;
