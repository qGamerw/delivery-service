import axios from "axios";
import { loginUser, logoutUser } from "../slices/authSlice";
import { Dispatch } from "redux";

interface Registration {
    username: string;
    email: string;
    password: string;
  }

interface Login {
    username: string;
    password: string;
}

interface Role {
    id: number;
    role: string;
}

interface User {
    id: number;
    username: string;
    email: string;
    phoneNumber: string;
    dateRegistration: string;
    status: string;
    latitude: number;
    longitude: number;
    isNotify: boolean;
    role: Role;
    accessToken: string;
}

const API_URL = "/api/auth/"

const register = (registration: Registration) => {
    const { username, email, password } = registration;
    return axios.post(API_URL + "signup", {
      username,
      email,
      password,
    });
  };

const login = async (login: Login, dispatch: Dispatch): Promise<User> => {
    const {username, password} = login;

    let response = await axios
        .post<User>(API_URL + "signin", {
            username,
            password,
        });
    console.log(response);
    dispatch(loginUser(response.data));
    return response.data;
};

const logout = (dispatch: Dispatch): void => {
    console.log("logout");
    dispatch(logoutUser());
};

const authService = {
    register,
    login,
    logout,
};

export default authService;