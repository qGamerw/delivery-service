import {Route, Routes, useNavigate} from "react-router-dom";
import { useSelector, useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { RootState } from './store';
import React from "react";
import NotFoundPage from "./pages/NotFoundPage";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import HeaderBar from "./components/HeaderBar";
import OrderNotificationComponent from "./components/OrderNotificationComponent";
import ListOrderPage from "./pages/ListOrderPage";
import ActiveOrdersPage from "./pages/ActiveOrdersPage";
import AllOrdersPage from "./pages/AllOrdersPage";
import UserInfoPage from "./pages/UserInfoPage";
import { setAuth } from "./slices/authSlice";
import authService from "./services/authService";

const App: React.FC = () => {
    const user = useSelector((state: RootState) => state.auth.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            dispatch(setAuth(true));
        }

        const refreshInterval = setInterval(() => {
            console.log("Check for refresh");
            const userStr = sessionStorage.getItem("user");
            let user = null;
            if (userStr) {
                user = JSON.parse(userStr);
            }

            if (user) {
            const refresh_token = user.refresh_token;

                authService.refresh(refresh_token, dispatch)
                    .then((userData) => {
                        console.log("Refresh successful", userData);
                    })
                    .catch((error) => {
                        console.error("Error during refresh", error);
                        navigate("/login");
                    });
            }
        }, 14 * 60 * 1000);

        return () => clearInterval(refreshInterval);
    }, [user, dispatch, navigate]);

    return (
        <div>
            <HeaderBar/>
            <OrderNotificationComponent />
            <Routes>
                <Route path="/" element={<MainPage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/orders" element={<ListOrderPage/>}/>
                <Route path="/active-orders" element={<ActiveOrdersPage/>}/>
                <Route path="/all-orders" element={<AllOrdersPage/>}/>
                <Route path="/info" element={<UserInfoPage/>}/>
                <Route path="*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
