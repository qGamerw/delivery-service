import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import authReducer from './slices/authSlice';
import shiftReducer from './slices/shiftSlice';

const store = configureStore({
    reducer: {
        user: userReducer,
        auth: authReducer,
        shift: shiftReducer,
    },
});

export default store;