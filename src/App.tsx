import { BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./routes";

import { AuthProvider, AppThemeProvider } from "./shared/contexts/";

export const App = () => {
  return (
    <AuthProvider>
      <AppThemeProvider>
        <BrowserRouter>
          <AppRoutes />
        </BrowserRouter>
      </AppThemeProvider>
    </AuthProvider>
  );
};
