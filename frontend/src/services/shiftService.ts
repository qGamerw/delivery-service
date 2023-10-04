import axios from "axios";
import authHeader from "./authHeader";
import { Dispatch } from "redux";
import { setCurrentShift, setAllShifts } from "../slices/shiftSlice";

interface Shift {
  id: number;
  beginShift: string;
  endShift: string;
}
const API_URL_SHIFT = "/api/shifts";

const createShift = async (shiftData: Shift, dispatch: Dispatch): Promise<Shift> => {
  const headers = authHeader(); 

  try {
    const response = await axios.post(API_URL_SHIFT, shiftData, { headers });
    const newShift = response.data;

    dispatch(setCurrentShift(newShift));

    return newShift;
  } catch (error) {
    console.error("Ошибка при создании смены:", error);
    throw error;
  }
};

const updateShift = async (shiftData: Shift, dispatch: Dispatch): Promise<Shift> => {
  const headers = authHeader(); 

  try {
    const response = await axios.put(API_URL_SHIFT, shiftData, { headers });
    const updatedShift = response.data;

    dispatch(setCurrentShift(updatedShift));

    return updatedShift;
  } catch (error) {
    console.error("Ошибка при обновлении смены:", error);
    throw error;
  }
};

const deleteShift = async (shiftId: number, dispatch: Dispatch): Promise<boolean> => {
  const headers = authHeader(); 

  try {
    await axios.delete(`${API_URL_SHIFT}/${shiftId}`, { headers });

    dispatch(setCurrentShift(null));
    
    return true;
  } catch (error) {
    console.error("Ошибка при удалении смены:", error);
    throw error;
  }
};

const getShiftsForCourier = async (
  courierId: number,
  dispatch: Dispatch
): Promise<Shift[]> => {
  const headers = authHeader(); 

  try {
    const response = await axios.get(`${API_URL_SHIFT}/courier/${courierId}`,{ headers });
    const shifts = response.data;

    dispatch(setAllShifts(shifts));

    return shifts;
  } catch (error) {
    console.error("Ошибка при извлечении всех смен курьера:", error);
    throw error;
  }
};

const shiftService = {
  createShift,
  updateShift,
  deleteShift,
  getShiftsForCourier,
};

export default shiftService;
