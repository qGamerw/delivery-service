import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import authReducer from './slices/authSlice';
import shiftReducer from './slices/shiftSlice';
import orderReducer from './slices/orderSlice';

const store = configureStore({
    reducer: {
        user: userReducer,
        auth: authReducer,
        shift: shiftReducer,
        order: orderReducer,
    },
});

export default store;