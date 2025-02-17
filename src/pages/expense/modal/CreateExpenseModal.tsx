import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  useMediaQuery,
  useTheme,
} from "@mui/material";
import { useState } from "react";

import { useAuthContext } from "../../../shared/contexts";
import * as Yup from "yup";
import { getUserIdFromJWT, StockService } from "../../../shared/services";
import { useNavigate } from "react-router-dom";
import { ExpenseService } from "../../../shared/services/api/request/ExpenseService";
import {
  combinedSuggestions,
  DynamicSuggestionsInput,
} from "../dynamicinput/DynamicInput";

interface CreateExpenseModalProps {
  open: boolean;
  handleClose: () => void;
}

const validationSchema = Yup.object().shape({
  name: Yup.string()

    .min(2, "Name cannot be less than 2 characters.")
    .max(100, "Name cannot be more than 100 characters.")
    .required("Name cannot be empty."),

  description: Yup.string()
    .min(2, "Description cannot be less than 2 characters.")
    .max(255, "Description cannot be more than 255 characters.")
    .required("Description cannot be empty."),

  category: Yup.string()
    .oneOf(
      combinedSuggestions,
      "Invalid category. Please select from the list."
    )
    .min(2, "Category cannot be less than 2 characters.")
    .max(255, "Category cannot be more than 255 characters.")
    .required("Category cannot be empty."),

  price: Yup.number()
    .positive("Price must be a positive value.")
    .required("Price cannot be null."),

  userId: Yup.string().required("User ID cannot be null."),
});

export const CreateExpenseModal = ({
  open,
  handleClose,
}: CreateExpenseModalProps) => {
  const theme = useTheme();
  const { token } = useAuthContext();
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [category, setCategory] = useState("");
  const [errors, setErrors] = useState<any>({});

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const isMiniTablet = useMediaQuery(theme.breakpoints.down("md"));
  const navigate = useNavigate();

  const handleSave = async () => {
    setErrors({});

    try {
      if (typeof token === "string") {
        const uuid = getUserIdFromJWT(token);

        if (uuid === undefined) return navigate("/home");

        await validationSchema.validate(
          { name, description, category, price: Number(price), userId: uuid },
          { abortEarly: false }
        );

        const response = await ExpenseService.create({
          name,
          description,
          category,
          price: Number(price),
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
          width: isMiniTablet ? "70%" : "30%",
          height: "70%",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 3,
          justifyContent: "space-between",
        }}
      >
        <Typography
          variant={isMobile ? "h5" : "h4"}
          noWrap
          color={"primary.main"}
          fontWeight="bold"
          sx={{
            width: "100%",
            textAlign: "center",
          }}
          mt={5}
        >
          Create Expense
        </Typography>

        <Box
          sx={{
            width: "100%",
            display: "flex",
            flexDirection: "column",
            gap: 2,
            mt: 8,
          }}
        >
          <TextField
            label="Name"
            type="string"
            fullWidth
            multiline
            value={name}
            onChange={(e) => setName(e.target.value)}
            error={!!errors.name}
            helperText={errors.name}
          />
          <DynamicSuggestionsInput
            value={category}
            onChange={(category) => setCategory(category)}
            error={!!errors.category}
            helperText={errors.category}
          />
          <TextField
            label="Description"
            type="string"
            fullWidth
            multiline
            rows={3}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            error={!!errors.description}
            helperText={errors.description}
          />

          <TextField
            label="Price"
            type="number"
            fullWidth
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            error={!!errors.price}
            helperText={errors.price}
          />
        </Box>

        {/* Botões */}
        <Box
          sx={{
            display: "flex",

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
            Save
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
            Cancel
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};
