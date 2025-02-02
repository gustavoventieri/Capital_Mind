import React from "react";
import { Route, Routes } from "react-router-dom";

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<h1>Home</h1>} />

      <Route path="*" element={<h1>Not Found</h1>} />
    </Routes>
  );
};
