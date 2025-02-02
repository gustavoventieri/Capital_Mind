import {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
} from "react";
import { ThemeProvider } from "@emotion/react";
import { Box } from "@mui/material";

import { DarkTheme, LightTheme } from "../themes";

interface IThemeContextData {
  themeName: "dark" | "light";
  toggleTheme: VoidFunction;
}

const ThemeContext = createContext({} as IThemeContextData);

interface IThemeProviderProps {
  children: React.ReactNode;
}

export const useThemeContext = () => {
  return useContext(ThemeContext);
};

export const AppThemeProvider: React.FC<IThemeProviderProps> = ({
  children,
}) => {
  const [themeName, setThemeName] = useState<"dark" | "light">("dark");

  const toggleTheme = useCallback(() => {
    setThemeName((oldThemeName) =>
      oldThemeName === "dark" ? "light" : "dark"
    );
  }, []);

  const theme = useMemo(() => {
    return themeName === "dark" ? DarkTheme : LightTheme;
  }, [themeName]);

  return (
    <ThemeContext.Provider value={{ themeName, toggleTheme }}>
      <ThemeProvider theme={theme}>
        <Box
          width="100vw"
          height="100vh"
          bgcolor={theme.palette.background.default}
        >
          {children}
        </Box>
      </ThemeProvider>
    </ThemeContext.Provider>
  );
};
