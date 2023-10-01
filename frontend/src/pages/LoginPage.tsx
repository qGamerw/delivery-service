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
        // Perform the login logic here, e.g., send loginData to the server
        console.log('Logging in with:', loginData);
    };

    return (
        <div>
            <h1>Login Page</h1>
            <Form>
                <Form.Item label="Username">
                    <Input
                        type="text"
                        value={loginData.username}
                        onChange={(e) => handleInputChange(e, 'username')}
                    />
                </Form.Item>

                <Form.Item label="Password">
                    <Input.Password
                        value={loginData.password}
                        onChange={(e) => handleInputChange(e, 'password')}
                    />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" onClick={handleLogin}>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default LoginPage;