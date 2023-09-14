import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  testVar: "test",
};

const TestSlice = createSlice({
  name: "TestSlice",
  initialState,
  reducers: {
    updateTestVar: (state, action) => {
      state.testVar = action.payload;
    },
  },
});

export default TestSlice;
export const { updateTestVar } = TestSlice.actions;
