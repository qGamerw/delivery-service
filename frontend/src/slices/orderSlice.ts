import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface Order {
  id: number | null;
  courierId: number | null;
  clientName: string | null;
  description: string | null;
  clientPhone: number | null;
  eStatusOrders: string | null;
  orderTime: string | null;
  address: string | null;
  branchAddress: string | null;
  flat: number | null;
  frontDoor: number | null;
  floor: number | null;
  weight: number | null;
  endCookingTime: string | null; 
  dishesOrders: any[];
}

interface OrderSliceState {
  currentOrder: Order | null;
  allOrders: Order[];
}

const initialState: OrderSliceState = {
  currentOrder: null,
  allOrders: [],
};

const orderSlice = createSlice({
  name: "orders",
  initialState,
  reducers: {
    setCurrentOrder: (state, action: PayloadAction<Order | null>) => {
      state.currentOrder = action.payload;
    },
    setAllOrders: (state, action: PayloadAction<Order[]>) => {
      state.allOrders = action.payload;
    },
  },
});

export const { setCurrentOrder, setAllOrders } = orderSlice.actions;

export default orderSlice.reducer;
