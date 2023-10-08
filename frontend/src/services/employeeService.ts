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

const API_URL_EMPLOYEE = "/api/employees";

const getFreeCouriers = async (dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.get(`${API_URL_EMPLOYEE}/free-couriers`, {
      headers,
    });
    const couriers = response.data;

    dispatch(setUserList(couriers));

    return couriers;
  } catch (error) {
    console.error("Ошибка при получении свободных курьеров:", error);
    throw error;
  }
};

const updateUser = async (userData: User, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.put(API_URL_EMPLOYEE, userData, { headers });
    const updatedUser = response.data;

    dispatch(setCurrentUser(updatedUser));

    return updatedUser;
  } catch (error) {
    console.error("Ошибка при обновлении работника:", error);
    throw error;
  }
};

const getNearestFreeCourier = async (
  coordinates: { latitude: string; longitude: string },
  dispatch: Dispatch
) => {
  const headers = authHeader();

  try {
    const response = await axios.get(`${API_URL_EMPLOYEE}/nearest-free-courier`, {
      headers,
      data: coordinates,
    });
    const nearestCourier = response.data;

    dispatch(setCurrentUser(nearestCourier));

    return nearestCourier;
  } catch (error) {
    console.error("Ошибка при получении ближайших свободных курьеров:", error);
    throw error;
  }
};

const notifyCourier = async (courierId: number, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    await axios.put(`${API_URL_EMPLOYEE}/notify/${courierId}`, null, { headers });

    return true;
  } catch (error) {
    console.error("Ошибка при уведомлении курьера:", error);
    throw error;
  }
};

const employeeService = {
  getFreeCouriers,
  updateUser,
  getNearestFreeCourier,
  notifyCourier,
};

export default employeeService;
