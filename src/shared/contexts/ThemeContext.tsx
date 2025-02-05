import {
  createContext,
  useCallback,
  useContext,
  useEffect,
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

export const useThemeContext = () => useContext(ThemeContext);

export const AppThemeProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const storedTheme = localStorage.getItem("theme") as "dark" | "light" | null;
  const [themeName, setThemeName] = useState<"dark" | "light">(
    storedTheme || "dark"
  );

  useEffect(() => {
    localStorage.setItem("theme", themeName);
  }, [themeName]);

  const toggleTheme = useCallback(() => {
    setThemeName((oldTheme) => {
      const newTheme = oldTheme === "dark" ? "light" : "dark";
      localStorage.setItem("theme", newTheme); // Salva no localStorage
      return newTheme;
    });
  }, []);

  const theme = useMemo(
    () => (themeName === "dark" ? DarkTheme : LightTheme),
    [themeName]
  );

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
