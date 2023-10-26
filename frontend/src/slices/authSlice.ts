import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface Role {
    id: number;
    role: string;
}

interface User {
    id: number;
    username: string;
    email: string;
    dateRegistration: string;
    status: string;
    latitude: number;
    longitude: number;
    isNotify: boolean;
    role: Role;
}

interface UserState {
    user: User | null;
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

const authSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        set: (state, action: PayloadAction<any>) => {
            state.user = action.payload;
        },
        setAuth: (state, action: PayloadAction<any>) => {
            state.isAuth = action.payload;
        },
        loginUser: (state, action: PayloadAction<any>) => {
            state.isAuth = true;
            state.user = action.payload;
            console.log(action.payload)

            if (action.payload.accessToken) {
                localStorage.setItem('user', JSON.stringify(action.payload));
            }
        },
        logoutUser: (state) => {
            state.isAuth = false;
            state.user = null;

            localStorage.removeItem('user');
        },
    },
});

export const { set, setAuth, loginUser, logoutUser } = authSlice.actions;

export default authSlice.reducer;