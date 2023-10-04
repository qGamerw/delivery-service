import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface Shift {
  id: number;
  beginShift: string;
  endShift: string;
}

interface ShiftSliceState {
  currentShift: Shift | null;
  allShifts: Shift[];
}

const initialState: ShiftSliceState = {
  currentShift: null,
  allShifts: [],
};

const shiftSlice = createSlice({
  name: "shifts",
  initialState,
  reducers: {
    setCurrentShift: (state, action: PayloadAction<Shift | null>) => {
      state.currentShift = action.payload;
    },
    setAllShifts: (state, action: PayloadAction<Shift[]>) => {
      state.allShifts = action.payload;
    },
  },
});

export const { setCurrentShift, setAllShifts } = shiftSlice.actions;

export default shiftSlice.reducer;
