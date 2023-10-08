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

const API_URL_COURIERS = "/api/couriers";

const getCouriers = async (dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.get(API_URL_COURIERS, { headers });
    const couriers = response.data;

    dispatch(setUserList(couriers));

    return couriers;
  } catch (error) {
    console.error("Ошибка при получении курьера:", error);
    throw error;
  }
};

const updateUser = async (userData: User, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    const response = await axios.put(API_URL_COURIERS, userData, { headers });
    const updatedUser = response.data;

    dispatch(setCurrentUser(updatedUser));

    return updatedUser;
  } catch (error) {
    console.error("Ошибка при обновлении курьера:", error);
    throw error;
  }
};

const updateStatus = async (status: string, dispatch: Dispatch) => {
  const headers = authHeader();

  try {
    await axios.put(`${API_URL_COURIERS}/status?statusCourier=${status}`, null, { headers });

    return true;
  } catch (error) {
    console.error("Ошибка при обновлении статуса:", error);
    throw error;
  }
};

const updateCoordinates = async (latitude: number, longitude: number, dispatch: Dispatch) => {
  const headers = authHeader();
  const coordinates = { latitude, longitude };

  try {
    await axios.put(`${API_URL_COURIERS}/coordinates`, coordinates, { headers });

    return true;
  } catch (error) {
    console.error("Ошибка при обновлении координат:", error);
    throw error;
  }
};

const courierService = {
  getCouriers,
  updateUser,
  updateStatus,
  updateCoordinates,
};

export default courierService;
