import { Box, CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuthContext } from "../../contexts"; // Seu contexto de autenticação
import { isAuthenticated } from "../../services"; // Função que verifica a validade do token

export const PrivateRoutes = () => {
  const { token, logout } = useAuthContext();
  const [loading, setLoading] = useState(true); // Estado para indicar se está carregando
  const [redirecting, setRedirecting] = useState(false); // Para controlar a navegação
  const location = useLocation(); // Para redirecionamento correto

  // Verificação do token e mudança de estado após carregamento do mesmo
  useEffect(() => {
    const savedToken = localStorage.getItem("token"); // Verifica o localStorage
    if (!savedToken || !isAuthenticated(savedToken)) {
      logout(); // Se o token estiver ausente ou inválido, faz o logout
      setRedirecting(true); // Indica que o redirecionamento deve ocorrer
    }
    setLoading(false); // Quando o token for verificado, termina o carregamento
  }, [logout]);

  // Enquanto estiver carregando, renderiza uma tela de loading ou nada
  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        height="100vh"
      >
        <CircularProgress variant="indeterminate" color="inherit" size={20} />
      </Box>
    );
  }

  // Se o token for inválido ou não existir, faz logout e redireciona
  if (redirecting) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <Outlet />; // Renderiza as rotas protegidas
};
