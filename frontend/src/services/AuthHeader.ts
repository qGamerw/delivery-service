interface User {
    accessToken: string;
}

const getUserFromLocalStorage = (): any | null => {
    const userString = localStorage.getItem('user');
    if (userString) {
        return JSON.parse(userString);
    }
    return null;
};
export default function authHeader(): Record<string, string> {
    let user: User | null;
    user = getUserFromLocalStorage();

    if (user && user.accessToken) {
        return { Authorization: "Bearer " + user.accessToken };
    } else {
        return {};
    }
}
