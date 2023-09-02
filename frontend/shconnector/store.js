import { configureStore } from "@reduxjs/toolkit";
import roodReducer from "./src/reducers/RootReducer";

const store = configureStore({
  reducer: roodReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
});

export default store;
