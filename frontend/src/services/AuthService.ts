import axios from "axios";

interface Login {
    username: string;
    password: string;
}

interface User {
    accessToken: string;
}

const API_URL_CART = "/api/auth/";

const login = async (login: Login): Promise<User> => {
    const {username, password} = login;

    let response = await axios
        .post<User>(API_URL_CART + "signin", {
            username,
            password,
        });
    console.log(response);
    if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
};

const logout = (): void => {
    console.log("logout");
    localStorage.removeItem("user");
};

const authService = {
    login,
    logout,
};

export default authService;