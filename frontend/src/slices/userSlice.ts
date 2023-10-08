import { createSlice, PayloadAction } from "@reduxjs/toolkit";

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

interface UserSliceState {
  currentUser: User | null;
  userList: User[];
}

const initialState: UserSliceState = {
  currentUser: null,
  userList: [],
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setCurrentUser: (state, action: PayloadAction<User | null>) => {
      state.currentUser = action.payload;
    },
    setUserList: (state, action: PayloadAction<User[]>) => {
      state.userList = action.payload;
    },
  },
});

export const { setCurrentUser, setUserList } = userSlice.actions;

export default userSlice.reducer;
