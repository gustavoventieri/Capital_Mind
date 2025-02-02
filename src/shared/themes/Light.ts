import { createTheme } from "@mui/material";

export const LightTheme = createTheme({
  palette: {
    primary: {
      main: "#D97706", // Laranja Queimado (Principal)
      dark: "#B45309", // Laranja Mais Escuro (Hover)
      light: "#F59E0B", // Laranja Vibrante (Destaques)
      contrastText: "#FFFFFF", // Texto Branco
    },
    background: {
      default: "#FFFFFF", // Fundo Branco
      paper: "#F9FAFB", // Papel Branco Claro
    },
    text: {
      primary: "#000000", // Preto para texto
      secondary: "#4B5563", // Cinza Médio para texto secundário
    },
  },
});
