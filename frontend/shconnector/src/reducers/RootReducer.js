import { combineReducers } from "@reduxjs/toolkit";
import TestSlice from "./TestSlice";

const rootReducer = combineReducers({
  test: TestSlice.reducer,
});

export default rootReducer;
