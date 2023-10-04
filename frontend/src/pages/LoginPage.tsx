import React, { useState } from 'react';
import {Input, Button, Form, message} from 'antd';
import authService from "../services/authService";
import {loginUser} from "../slices/userSlice";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";

interface LoginProps {
    username: string;
    password: string;
}

const LoginPage: React.FC = () => {
    const [loginData, setLoginData] = useState<LoginProps>({
        username: '',
        password: '',
    });

    const dispatch = useDispatch();
    const navigate = useNavigate();

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
        authService.login(loginData, dispatch).then((user) => {
            console.log(user)
            navigate("/info")
        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("Неправильный логин или пароль");
        })
    };

    return (
        <div style={{paddingLeft : 12}}>
            <h1>Вход</h1>
            <Form>
                <Form.Item  style={{paddingRight : 8}}>
                    <h3>Email</h3>
                    <Input
                        style={{ maxWidth: '500px' }}
                        maxLength={30}
                        type="text"
                        value={loginData.username}
                        onChange={(e) => handleInputChange(e, 'username')}
                    />
                </Form.Item>

                <Form.Item style={{paddingRight : 8}}>
                    <h3>Пароль</h3>
                    <Input.Password
                        style={{ maxWidth: '500px' }}
                        maxLength={30}
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