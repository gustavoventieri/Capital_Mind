import { createTheme } from "@mui/material";

export const DarkTheme = createTheme({
  palette: {
    primary: {
      main: "#F97316", // Laranja Intenso (Principal)
      dark: "#EA580C", // Laranja mais escuro (Hover)
      light: "#FDBA74", // Laranja Claro (Destaques)
      contrastText: "#FFFFFF", // Texto Branco
    },
    background: {
      default: "#111827", // Preto Chumbo para fundo
      paper: "#1F2937", // Papel cinza escuro
    },
    text: {
      primary: "#FFFFFF", // Texto Branco para facilitar leitura
      secondary: "#9CA3AF", // Cinza Claro para textos secundários
    },
  },
});
