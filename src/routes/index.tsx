import { Button } from "@mui/material";
import { Navigate, Route, Routes } from "react-router-dom";

import { useThemeContext } from "../shared/contexts";
import { SmallDrawer } from "../shared/components/Drawer";

export const AppRoutes = () => {
  const { toggleTheme } = useThemeContext();

  return (
    <Routes>
      <Route
        path="/"
        element={
          <>
            <SmallDrawer />
            <Button variant="contained" color="primary" onClick={toggleTheme}>
              Toggle Theme
            </Button>
          </>
        }
      />

      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};
