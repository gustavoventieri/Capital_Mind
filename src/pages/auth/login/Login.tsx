import { useState, useEffect, useRef } from "react";
import {
  Grid,
  Typography,
  TextField,
  Button,
  IconButton,
  InputAdornment,
  Icon,
  Box,
  useMediaQuery,
  Theme,
  CircularProgress,
  Link,
  AlertColor,
  Snackbar,
  Alert,
} from "@mui/material";
import * as yup from "yup";
import { useThemeContext } from "../../../shared/contexts";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";

// Definindo o schema de validação com Yup
const loginSchema = yup.object().shape({
  email: yup
    .string()
    .email("Invalid email format")
    .required("Email is required"),
  password: yup
    .string()
    .required("Password is required")
    .min(8, "Password must be at least 8 characters"),
});

export const Login = () => {
  const { toggleTheme, themeName } = useThemeContext();

  const [isLoading, setIsLoading] = useState(false);
  const emailInputRef = useRef<HTMLInputElement | null>(null);
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const [open, setOpen] = useState(false);
  const [snackMessage, setSnackMessage] = useState("");
  const [snackColor, setSnackColor] = useState<AlertColor>("error");

  const isMiniTablet = useMediaQuery((theme: Theme) =>
    theme.breakpoints.down("md")
  );

  const handleSnackbarClose = () => setOpen(false);

  const handleClickShowPassword = () => setShowPassword(!showPassword);

  useEffect(() => {
    if (emailInputRef.current) {
      emailInputRef.current.focus();
    }

    if (open) {
      const timer = setTimeout(() => {
        setOpen(false);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [open]);

  const handleSubmit = async () => {
    setIsLoading(true);
    try {
      // Validando os dados de entrada
      await loginSchema.validate({ email, password }, { abortEarly: false });

      setIsLoading(false);
    } catch (error) {
      if (error instanceof yup.ValidationError) {
        if (isMiniTablet) {
          error.inner.forEach((error) => {
            if (error.path === "email") {
              setEmailError(error.message);
            } else if (error.path === "password") {
              setPasswordError(error.message);
            }
          });
        }
        const errorMessages = error.inner
          .map((err) => `${err.message}<br />`)
          .join("");

        setSnackMessage(errorMessages);
        setSnackColor("error");
        setOpen(true);
      }
      setIsLoading(false);
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        width: "100%",
        overflow: "hidden",
      }}
    >
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: isMiniTablet ? "100%" : "80%",
          height: isMiniTablet ? "100%" : "80%",
          borderRadius: 2, // Optional, arredonda as bordas
          boxShadow: 19, // Optional, adiciona sombra
          overflow: "hidden", // Impede o conteúdo de transbordar
        }}
      >
        <Grid
          container
          height="100%"
          style={{
            backgroundSize: "cover",
          }}
        >
          <Grid
            item
            xs={12}
            md={5}
            container
            display={"flex"}
            direction="column"
            justifyContent="center"
            alignItems="center"
            padding={4}
            position="relative"
            overflow="hidden"
            bgcolor="background.paper"
          >
            <IconButton
              onClick={toggleTheme}
              color="primary"
              sx={{
                position: "absolute",
                top: 16,
                left: 16,
                zIndex: 1, // Garante que o botão fique acima do fundo
              }}
            >
              {themeName === "light" ? (
                <LightModeIcon sx={{ color: "primary.main" }} />
              ) : (
                <DarkModeIcon sx={{ color: "primary.main" }} />
              )}
            </IconButton>

            <Typography
              fontSize={23}
              gutterBottom
              zIndex={1}
              marginBottom={4}
              sx={{
                color: themeName === "light" ? "primary.main" : "text.primary",
              }}
            >
              Log In into your account
            </Typography>
            <Box width={isMiniTablet ? "90%" : "90%"}>
              <TextField
                label="E-mail"
                placeholder="Enter your email"
                fullWidth
                disabled={isLoading}
                value={email}
                inputRef={emailInputRef}
                onChange={(e) => setEmail(e.target.value)}
                margin="normal"
                InputLabelProps={{
                  style: { color: "gray" },
                }}
                error={isMiniTablet ? !!emailError : undefined}
                helperText={isMiniTablet ? emailError : undefined}
                sx={{
                  marginBottom: 4,
                }}
              />
              <TextField
                margin="normal"
                fullWidth
                name="password"
                label="Password"
                type={showPassword ? "text" : "password"}
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
                InputLabelProps={{
                  style: { color: "gray" },
                }}
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={handleClickShowPassword}
                        sx={{ color: "gray" }}
                      >
                        {showPassword ? (
                          <VisibilityIcon sx={{ color: "primary.main" }} />
                        ) : (
                          <VisibilityOffIcon sx={{ color: "primary.main" }} />
                        )}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
                error={isMiniTablet ? !!passwordError : undefined}
                helperText={isMiniTablet ? passwordError : undefined}
                sx={{
                  marginBottom: 4,
                }}
              />

              <Button
                fullWidth
                variant="contained"
                disabled={isLoading}
                onClick={handleSubmit}
                endIcon={
                  isLoading ? (
                    <CircularProgress
                      variant="indeterminate"
                      color="inherit"
                      size={20}
                    />
                  ) : undefined
                }
                sx={{
                  marginTop: 2,
                  backgroundColor: "primary.main",
                  "&:hover": { backgroundColor: "primary.dark" },
                  color: "white",
                }}
              >
                Login
              </Button>
              <Typography
                variant="body2"
                align="center"
                sx={{
                  marginTop: 2,
                  color:
                    themeName === "light" ? "text.secondary" : "text.primary",
                }}
              >
                Don't have an account?{" "}
                <Link href="/register" sx={{ color: "primary.main" }}>
                  Register here
                </Link>
              </Typography>
            </Box>
          </Grid>
          {!isMiniTablet && (
            <Grid
              overflow="hidden"
              item
              xs={12}
              md={7}
              sx={{
                overflow: "hidden",
                backgroundColor: "background.paper",
                position: "relative",
              }}
              display="flex"
              justifyContent="center"
              alignItems="center"
            >
              <Box
                component="img"
                src="/assets/login_page.jpg" // Alterar para o caminho da sua imagem
                alt="Login Illustration"
                sx={{
                  width: "100%",
                  height: "100%",
                  objectFit: "cover",
                  opacity: 0.5, // Opacidade da imagem
                }}
              />
              <Box
                sx={{
                  position: "absolute",
                  top: 0,
                  left: 0,
                  width: "100%",
                  height: "100%",
                  backgroundColor: "rgba(0, 0, 0, 0.5)", // Cor preta com opacidade
                }}
              />
            </Grid>
          )}
        </Grid>
      </Box>

      {!isMiniTablet && (
        <Snackbar
          open={open}
          autoHideDuration={6000}
          anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
        >
          <Alert
            onClose={handleSnackbarClose}
            severity={snackColor}
            sx={{
              width: "100%",
              bgcolor: "error.main",
              color: "white",
            }}
          >
            <Box
              sx={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <span
                dangerouslySetInnerHTML={{
                  __html: snackMessage,
                }}
              />
            </Box>
          </Alert>
        </Snackbar>
      )}
    </Box>
  );
};
