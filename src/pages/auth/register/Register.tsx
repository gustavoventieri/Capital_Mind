import { useState, useEffect, useRef } from "react";
import {
  Grid,
  Typography,
  TextField,
  Button,
  IconButton,
  InputAdornment,
  Box,
  CircularProgress,
  Link,
  Snackbar,
  Alert,
  useMediaQuery,
  Theme,
} from "@mui/material";
import { AlertColor } from "@mui/material/Alert";
import * as yup from "yup";
import { useAuthContext, useThemeContext } from "../../../shared/contexts";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";
import { AuthService } from "../../../shared/services/api/request/AuthService";
import { useNavigate } from "react-router-dom";

// Definindo o schema de validação com Yup
const registerSchema = yup.object().shape({
  name: yup
    .string()
    .required("Name Is Required")
    .min(3, "Name Must Be At Least 3 Characters"),
  email: yup
    .string()
    .email("Invalid Email Format")
    .required("Email is required"),
  salary: yup
    .number()
    .typeError("Salary Must Be a Valid Number")
    .positive("Salary Must Be Greater Than Zero")
    .required("Salary Is Required"),
  password: yup
    .string()
    .required("Password Is Required")
    .min(8, "Password Must Be At Least 8 Characters"),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref("password")], "Passwords Must Match")
    .required("Confirm Password Is R  equired"),
});

export const Register = () => {
  const { toggleTheme, themeName } = useThemeContext();
  const { setToken } = useAuthContext();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const nameInputRef = useRef<HTMLInputElement | null>(null);
  const [name, setName] = useState("");
  const [nameError, setNameError] = useState("");
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [salary, setSalary] = useState("");
  const [salaryError, setSalaryError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [confirmPasswordError, setConfirmPasswordError] = useState("");

  const [open, setOpen] = useState(false);
  const [snackMessage, setSnackMessage] = useState("");
  const [snackColor, setSnackColor] = useState<AlertColor>("error");

  const isMiniTablet = useMediaQuery((theme: Theme) =>
    theme.breakpoints.down("md")
  );

  useEffect(() => {
    if (nameInputRef.current) {
      nameInputRef.current.focus();
    }

    if (open) {
      const timer = setTimeout(() => {
        setOpen(false);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [open]);

  const handleClickShowPassword = () => setShowPassword(!showPassword);

  const handleSnackbarClose = () => setOpen(false);
  const handleSubmit = async () => {
    setIsLoading(true);
    setNameError("");
    setEmailError("");
    setSalaryError("");
    setPasswordError("");
    setConfirmPasswordError("");

    try {
      // Validando os dados de entrada
      await registerSchema.validate(
        { name, email, salary, password, confirmPassword },
        { abortEarly: false }
      );

      // Chamando a service de registro
      const response = await AuthService.register({
        name,
        email,
        password,
        salary: Number(salary),
      });

      if (response instanceof Error) {
        throw response;
      }

      setToken(response.token);
      
      const timer = setTimeout(() => {
        navigate("/home");
      }, 5000);
      return () => clearTimeout(timer);
    } catch (error) {
      if (error instanceof yup.ValidationError) {
        if (isMiniTablet) {
          error.inner.forEach((error) => {
            switch (error.path) {
              case "name":
                setNameError(error.message);
                break;
              case "email":
                setEmailError(error.message);
                break;
              case "salary":
                setSalaryError(error.message);
                break;
              case "password":
                setPasswordError(error.message);
                break;
              case "confirmPassword":
                setConfirmPasswordError(error.message);
                break;
              default:
                break;
            }
          });
        }
        const errorMessages = error.inner
          .map((err) => `${err.message}<br />`)
          .join("");
        setSnackMessage(errorMessages);
        setSnackColor("error");
        setOpen(true);
      } else if (error instanceof Error) {
        setEmailError("Email Already Registered");
        setSnackMessage("Email Already Registered");
        setSnackColor("error");
        setOpen(true);
      } else {
        // Caso seja um erro inesperado, tratamos como string genérica
        setSnackMessage("Ocorreu um erro inesperado.");
        setSnackColor("error");
        setOpen(true);
      }
    } finally {
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
      }}
    >
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: isMiniTablet ? "100%" : "80%",
          height: isMiniTablet ? "100%" : "80%",
          borderRadius: 2,
          boxShadow: 19,
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
              marginBottom={2}
              sx={{
                color: themeName === "light" ? "primary.main" : "text.primary",
              }}
            >
              Register Your Account
            </Typography>
            <Box width={"90%"}>
              <TextField
                label="Name"
                placeholder="Enter your Name"
                fullWidth
                disabled={isLoading}
                value={name}
                inputRef={nameInputRef}
                onChange={(e) => setName(e.target.value)}
                margin="normal"
                error={isMiniTablet ? !!nameError : undefined}
                helperText={isMiniTablet ? nameError : undefined}
              />
              <TextField
                label="E-mail"
                placeholder="Enter Your Email"
                type="email"
                fullWidth
                disabled={isLoading}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                margin="normal"
                error={isMiniTablet ? !!emailError : undefined}
                helperText={isMiniTablet ? emailError : undefined}
              />
              <TextField
                label="Salary"
                placeholder="Enter Your Salary"
                fullWidth
                disabled={isLoading}
                value={salary}
                onChange={(e) => setSalary(e.target.value)} // Corrigido
                margin="normal"
                error={isMiniTablet ? !!salaryError : undefined}
                helperText={isMiniTablet ? salaryError : undefined}
              />
              <TextField
                margin="normal"
                fullWidth
                name="password"
                label="Password"
                type={showPassword ? "text" : "password"}
                placeholder="Enter Your Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
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
              />

              <TextField
                margin="normal"
                fullWidth
                name="confirmPassword"
                label="Confirm Password"
                type={showPassword ? "text" : "password"}
                placeholder="Confirm Your Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                autoComplete="current-password"
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
                error={isMiniTablet ? !!confirmPasswordError : undefined}
                helperText={isMiniTablet ? confirmPasswordError : undefined}
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
                Register
              </Button>
              <Typography
                variant="body2"
                component="span"
                color="textSecondary"
                sx={{ display: "block", marginTop: 2 }}
              >
                Already have an account?{" "}
                <Link href="/login" color="primary">
                  Sign in here
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
              bgcolor: "#FFEEFA",
              color: "red",
              "& .MuiAlert-icon": { color: "red" },
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
