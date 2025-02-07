import { Navigate, Route, Routes } from "react-router-dom";
import { Home, Register, Login, CryptocCurrency } from "../pages/";

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/home" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/cryptocurrency" element={<CryptocCurrency />} />

      <Route path="*" element={<Navigate to="/home" />} />
    </Routes>
  );
};
