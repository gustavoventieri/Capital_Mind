import { useEffect, useState } from "react";
import {
  Box,
  Button,
  CircularProgress,
  Grid,
  Paper,
  Typography,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import { BaseLayout } from "../../shared/layouts";
import { ConfirmDialog, ToolsBar } from "../../shared/components";
import { useAuthContext } from "../../shared/contexts";
import { useDebounce } from "../../shared/hooks";
import { getUserIdFromJWT } from "../../shared/services";
import {
  ExpenseService,
  IExpense,
} from "../../shared/services/api/request/ExpenseService";
import { CreateExpenseModal } from "./modal/CreateExpenseModal";
import { UpdateExpenseModal } from "./modal/UpdateExpenseModal";

export const ListExpenses = () => {
  const { token } = useAuthContext();
  const { debounce } = useDebounce();

  const [deleteId, setDeleteId] = useState<number | null>(null); // Armazena o ID do item a ser excluído
  const [openDialog, setOpenDialog] = useState(false);
  const [expense, setExpense] = useState<IExpense | null>(null);
  const [expenses, setExpenses] = useState<IExpense[]>([]);
  const [openCreateExpenseModal, setOpenCreateExpenseModal] = useState(false);
  const [openUpdateExpenseModal, setOpenUpdateExpenseModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [userId, setUserId] = useState<string | undefined>(undefined);
  const [filteredExpense, setFilteredExpense] = useState<IExpense[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (token) {
      try {
        const decodedId = getUserIdFromJWT(token);
        setUserId(decodedId);
      } catch (error) {
        console.error("Erro ao decodificar o JWT:", error);
      }
    }
  }, [token]);

  useEffect(() => {
    if (userId) {
      setIsLoading(true);
      debounce(() => {
        ExpenseService.getAll(userId).then(async (result) => {
          setIsLoading(false);
          if (result instanceof Error) {
          } else {
            setExpenses(result);
          }
        });
      });
    }
  }, [userId]);

  useEffect(() => {
    if (searchTerm === "") {
      setFilteredExpense(expenses);
    } else {
      const filtered = expenses.filter((expense) =>
        expense.name.toLowerCase().includes(searchTerm.toLowerCase())
      );

      setFilteredExpense(filtered);
    }
  }, [searchTerm, expenses]);

  const handleDelete = (id: number) => {
    setDeleteId(id);
    setOpenDialog(true);
  };

  const handleConfirmDelete = () => {
    if (deleteId === null) return;

    ExpenseService.deleteById(deleteId).then((result) => {
      if (result instanceof Error) {
      } else {
        window.location.reload();
      }
      setOpenDialog(false);
      setDeleteId(null);
    });
  };

  const handleOpenCreate = () => setOpenCreateExpenseModal(true);
  const handleCloseCreate = () => setOpenCreateExpenseModal(false);

  const handleOpenUpdate = (expense: IExpense) => {
    setExpense(expense);
    setOpenUpdateExpenseModal(true);
  };
  const handleCloseUpdate = () => setOpenUpdateExpenseModal(false);

  return (
    <BaseLayout
      title="Expenses"
      toolsBar={
        <ToolsBar
          onNewButtonClick={() => handleOpenCreate()}
          showSearchInput
          searchText={searchTerm}
          onSearchTextChange={(filter) => {
            setSearchTerm(filter);
          }}
        />
      }
    >
      <Box
        sx={{
          width: "100%",

          paddingBottom: 4,
          backgroundColor: "background.default", // Define uma cor de fundo fixa
        }}
      >
        {isLoading ? (
          <Box
            display="flex"
            justifyContent="center"
            alignItems="center"
            height="70vh"
          >
            <CircularProgress size={100} />
          </Box>
        ) : (
          <Grid container spacing={2} sx={{ px: 2, mt: 2 }}>
            {filteredExpense.length > 0 ? (
              filteredExpense.map((expense) => (
                <Grid item xs={12} sm={6} md={4} lg={3} key={expense.expenseId}>
                  <Paper
                    elevation={3}
                    sx={{
                      p: 2,
                      minHeight: 200,
                      display: "flex",
                      flexDirection: "column",
                      justifyContent: "space-between",
                      gap: 2,
                    }}
                  >
                    <Typography variant="h6">
                      {expense.name.toUpperCase()}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Description:</strong> {expense.description}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Category:</strong> {expense.category}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Price:</strong> R$ {expense.price?.toFixed(2)}
                    </Typography>
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
                        onClick={() => handleOpenUpdate(expense)}
                        sx={{
                          backgroundColor: "primary.main",
                          "&:hover": {
                            backgroundColor: "primary.dark",
                          },
                          height: "40px",
                          color: "white",
                        }}
                      >
                        <EditIcon />
                      </Button>
                      <Button
                        variant="outlined"
                        color="error"
                        fullWidth
                        onClick={() => handleDelete(expense.expenseId)}
                        sx={{
                          backgroundColor: "transparent",
                          "&:hover": {
                            backgroundColor: "red",
                            color: "white",
                          },
                          height: "40px",
                        }}
                      >
                        <DeleteIcon />
                      </Button>
                    </Box>
                  </Paper>
                </Grid>
              ))
            ) : (
              <Grid item xs={12}>
                <Typography
                  variant="body1"
                  color="textSecondary"
                  align="center"
                >
                  No expenses found.
                </Typography>
              </Grid>
            )}
          </Grid>
        )}

        <CreateExpenseModal
          open={openCreateExpenseModal}
          handleClose={handleCloseCreate}
        />

        <UpdateExpenseModal
          open={openUpdateExpenseModal}
          handleClose={handleCloseUpdate}
          expense={expense}
        />

        <ConfirmDialog
          open={openDialog}
          onClose={() => setOpenDialog(false)} // Fecha o modal sem fazer nada
          onConfirm={handleConfirmDelete} // Executa a exclusão
          title="Confirmation"
          content="Are you sure you want to delete this stock?"
        />
      </Box>
    </BaseLayout>
  );
};
