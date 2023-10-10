import {Route, Routes, useNavigate} from "react-router-dom";
import React from "react";
import NotFoundPage from "./pages/NotFoundPage";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import HeaderBar from "./components/HeaderBar";
import ListOrderPage from "./pages/ListOrderPage";
const App: React.FC = () => {
    return (
        <div>
            <HeaderBar/>
            <Routes>
                <Route path="/" element={<MainPage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/orders" element={<ListOrderPage/>}/>
                <Route path="*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
