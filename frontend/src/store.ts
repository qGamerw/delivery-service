import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import shiftReducer from './slices/shiftSlice';

const store = configureStore({
    reducer: {
        user: userReducer,
        shift: shiftReducer,
    },
});

export default store;