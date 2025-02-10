import { Navigate, Route, Routes } from "react-router-dom";
import { Home, Register, Login, CryptocCurrency, Stock } from "../pages/";

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/home" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/cryptocurrency" element={<CryptocCurrency />} />
      <Route path="/stock" element={<Stock />} />

      <Route path="*" element={<Navigate to="/home" />} />
    </Routes>
  );
};
