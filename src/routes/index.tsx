import { Navigate, Route, Routes } from "react-router-dom";
import {
  Home,
  Register,
  Login,
  CryptocCurrency,
  ListStocks,
  ListExpenses,
} from "../pages/";
import { PrivateRoutes } from "../shared/components";

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route element={<PrivateRoutes />}>
        <Route path="/home" element={<Home />} />
        <Route path="/cryptocurrency" element={<CryptocCurrency />} />
        <Route path="/stock" element={<ListStocks />} />
        <Route path="/expense" element={<ListExpenses />} />

        <Route path="*" element={<Navigate to="/home" />} />
      </Route>
    </Routes>
  );
};
