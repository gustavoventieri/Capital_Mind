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
      default: "#e0e0e0", // Fundo Branco
      paper: "#f0f0f0", // Papel Branco Claro
    },
    text: {
      primary: "#212121", // Cinza Escuro para texto principal
      secondary: "#757575", // Cinza Médio para texto secundário
    },
  },
  components: {
    MuiSvgIcon: {
      styleOverrides: {
        root: {
          color: "black", // Definindo a cor dos ícones como preto globalmente
        },
      },
    },
  },
});
