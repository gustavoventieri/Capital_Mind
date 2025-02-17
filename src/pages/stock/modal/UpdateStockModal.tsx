import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  useMediaQuery,
  useTheme,
} from "@mui/material";
import { useEffect, useState } from "react";
import {
  combinedSuggestions,
  DynamicSuggestionsInput,
} from "../dynamicinput/DynamicInput";
import { useAuthContext, useThemeContext } from "../../../shared/contexts";
import * as Yup from "yup";
import {
  getUserIdFromJWT,
  IStock,
  StockService,
} from "../../../shared/services";
import { useNavigate } from "react-router-dom";

interface CreateStockModalProps {
  open: boolean;
  handleClose: () => void;
  stock: IStock | null;
}

const validationSchema = Yup.object().shape({
  name: Yup.string()
    .oneOf(combinedSuggestions, "Invalid name. Please select from the list.") // Lista dinâmica
    .min(2, "Name cannot be less than 2 characters.")
    .max(100, "Name cannot be more than 100 characters.")
    .required("Name cannot be empty."),

  description: Yup.string()
    .min(2, "Description cannot be less than 2 characters.")
    .max(255, "Description cannot be more than 255 characters.")
    .required("Description cannot be empty."),

  quantity: Yup.number()
    .positive("Quantity must be a positive value.")
    .required("Quantity cannot be null."),

  userId: Yup.string().required("User ID cannot be null."),
});

export const UpdateStockModal = ({
  open,
  handleClose,
  stock,
}: CreateStockModalProps) => {
  const theme = useTheme();
  const { token } = useAuthContext();
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [quantity, setQuantity] = useState("");
  const [errors, setErrors] = useState<any>({});
  const { themeName } = useThemeContext();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const isMiniTablet = useMediaQuery(theme.breakpoints.down("md"));
  const navigate = useNavigate();

  useEffect(() => {
    if (stock) {
      setName(stock.name || "");
      setDescription(stock.description || "");
      setQuantity(stock.quantity.toString() || "");
    }
  }, [stock]);

  const handleSave = async () => {
    setErrors({});

    try {
      if (typeof token === "string") {
        const uuid = getUserIdFromJWT(token);

        if (uuid === undefined) return navigate("/home");

        await validationSchema.validate(
          { name, description, quantity: Number(quantity), userId: uuid },
          { abortEarly: false }
        );
        if (!stock) return Error("no stock found");
        const response = await StockService.update({
          stockId: stock.stockId,
          name,
          description,
          quantity: Number(quantity),
          userId: uuid,
        });

        if (response instanceof Error) {
          throw response;
        }

        return window.location.reload();
      }
    } catch (err) {
      if (err instanceof Yup.ValidationError) {
        console.error("Erros de validação:", err);
        const newErrors: { [key: string]: string } = {};
        err.inner.forEach((error) => {
          if (error.path) {
            newErrors[error.path] = error.message;
          }
        });
        setErrors(newErrors);
      } else {
        console.error("Erro desconhecido:", err);
      }
    }
  };

  return (
    <Modal open={open} onClose={handleClose} aria-labelledby="modal-title">
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          width: isMiniTablet ? "70%" : "40%",
          height: "70%",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 3,
          justifyContent: "space-between",
        }}
      >
        {/* Cabeçalho do Modal */}
        <Typography
          variant={isMobile ? "h5" : "h4"}
          noWrap
          fontWeight="bold"
          color={themeName === "dark" ? "white" : "primary.main"}
          sx={{
            width: "100%",
            textAlign: "center",
            mt: 6,
            mb: 2,
          }}
        >
          Update Stock
        </Typography>

        {/* Campos de Entrada */}
        <Box
          sx={{
            width: "100%",
            display: "flex",
            flexDirection: "column",
            gap: 2,
            mt: 8,
          }}
        >
          <DynamicSuggestionsInput
            value={name}
            onChange={(newName) => setName(newName)} // Atualiza o estado `name` com o novo valor
            error={!!errors.name}
            helperText={errors.name}
          />
          <TextField
            label="Description"
            fullWidth
            multiline
            rows={3}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            error={!!errors.description}
            helperText={errors.description}
          />
          <TextField
            label="Quantity"
            type="number"
            fullWidth
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            error={!!errors.quantity}
            helperText={errors.quantity}
          />
        </Box>

        {/* Botões */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 2,
            marginTop: "auto",
            width: "100%",
          }}
        >
          <Button
            variant="contained"
            color="primary"
            fullWidth
            onClick={() => handleSave()}
            sx={{
              backgroundColor: "primary.main",
              "&:hover": {
                backgroundColor: "primary.dark",
              },
              height: "40px",
              color: "white",
              paddingTop: 1.4,
            }}
          >
            Atualizar
          </Button>
          <Button
            variant="outlined"
            color="error"
            fullWidth
            onClick={handleClose}
            sx={{
              backgroundColor: "transparent",
              "&:hover": {
                backgroundColor: "red",
                color: "white",
              },
              height: "40px",
              paddingTop: 1.4,
            }}
          >
            Cancelar
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};
