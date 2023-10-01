import React, { useState } from 'react';
import { Input, Button, Form } from 'antd';

interface LoginProps {
    username: string;
    password: string;
}

const LoginPage: React.FC = () => {
    const [loginData, setLoginData] = useState<LoginProps>({
        username: '',
        password: '',
    });

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement>,
        field: 'username' | 'password'
    ) => {
        setLoginData({
            ...loginData,
            [field]: e.target.value,
        });
    };

    const handleLogin = () => {
        console.log('Logging in with:', loginData);
    };

    return (
        <div style={{alignItems: "center"}}>
            <h1 style={{paddingLeft : 8}}>Login Page</h1>
            <Form>

                <Form.Item  style={{paddingRight : 8, paddingLeft : 8}}>
                    <h3>Email</h3>
                    <Input
                        style={{ maxWidth: '500px' }}
                        maxLength={30}
                        type="text"
                        value={loginData.username}
                        onChange={(e) => handleInputChange(e, 'username')}
                    />
                </Form.Item>

                <Form.Item style={{paddingRight : 8, paddingLeft : 8}}>
                    <h3>Password</h3>
                    <Input.Password
                        style={{ maxWidth: '500px' }}
                        maxLength={30}
                        value={loginData.password}
                        onChange={(e) => handleInputChange(e, 'password')}
                    />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" onClick={handleLogin} style={{margin : 8}}>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default LoginPage;