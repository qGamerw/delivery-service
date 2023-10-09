import axios from "axios";
import authHeader from "./authHeader";
import { Dispatch } from "redux";
import { setCurrentUser, setUserList } from "../slices/userSlice";

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

const API_URL_ADMIN = "/api/administrators";

const getCourier = async (
  courierId: number,
  dispatch: Dispatch
): Promise<User | null> => {
  const headers = authHeader();

  try {
    const response = await axios.get(`${API_URL_ADMIN}/courier/${courierId}`, {
      headers,
    });
    const courierAdministrator = response.data;

    dispatch(setCurrentUser(courierAdministrator));

    return courierAdministrator;
  } catch (error) {
    console.error("Ошибка при получении курьера:", error);
    throw error;
  }
};

const updateUser = async (userData: User, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.put(API_URL_ADMIN, userData, { headers });
    const updatedUser = response.data;

    dispatch(setCurrentUser(updatedUser));

    return updatedUser;
  } catch (error) {
    console.error("Ошибка при обновлении пользователя:", error);
    throw error;
  }
};

const deleteUser = async (userId: number, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    await axios.delete(`${API_URL_ADMIN}/${userId}`, { headers });
    
    dispatch(setCurrentUser(null));

    return true;
  } catch (error) {
    console.error("Ошибка при удалении пользователя:", error);
    throw error;
  }
};

const getCouriers = async (dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.get(`${API_URL_ADMIN}/all-couriers`, {
      headers,
    });
    const couriers = response.data;

    dispatch(setUserList(couriers));

    return couriers;
  } catch (error) {
    console.error("Ошибка при получении курьеров:", error);
    throw error;
  }
};

const getCouriersByDate = async (shiftDate: string, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.get(`${API_URL_ADMIN}/all-couriers/by-date?shiftDate=${shiftDate}`, {
      headers,
    });
    const couriers = response.data;

    dispatch(setUserList(couriers));

    return couriers;
  } catch (error) {
    console.error("Ошибка при получении курьеров по дате:", error);
    throw error;
  }
};

const adminService = {
  getCourierAdministrator: getCourier,
  updateUser,
  deleteUser,
  getCouriers,
  getCouriersByDate,
};

export default adminService;
