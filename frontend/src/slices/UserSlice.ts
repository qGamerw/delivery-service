import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface UserState {
    user: any | null;
    isAuth: boolean;
}
const getUserFromLocalStorage = (): any | null => {
    const userString = localStorage.getItem('user');
    if (userString) {
        return JSON.parse(userString);
    }
    return null;
};

const initialState: UserState = {
    user: getUserFromLocalStorage(),
    isAuth: false,
};

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        set: (state, action: PayloadAction<any>) => {
            state.user = action.payload;
        },
        login: (state, action: PayloadAction<any>) => {
            state.isAuth = true;
            state.user = action.payload;

            localStorage.setItem('user', JSON.stringify(action.payload));
        },
        logout: (state) => {
            state.isAuth = false;
            state.user = null;

            localStorage.removeItem('user');
        },
    },
});

export const { set, login, logout } = userSlice.actions;

export default userSlice.reducer;