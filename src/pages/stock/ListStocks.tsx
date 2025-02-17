import {
  Box,
  CircularProgress,
  Typography,
  Paper,
  Grid,
  TextField,
  Button,
  Snackbar,
  Alert,
} from "@mui/material";
import { ConfirmDialog, ToolsBar } from "../../shared/components";
import { BaseLayout } from "../../shared/layouts";
import { useState, useEffect } from "react";
import { useAuthContext } from "../../shared/contexts";
import { getUserIdFromJWT, IStock, StockService } from "../../shared/services";
import { useDebounce } from "../../shared/hooks";

import { CreateStockModal } from "./modal/CreateStockModal";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { UpdateStockModal } from "./modal/UpdateStockModal";

export const ListStocks = () => {
  const { token } = useAuthContext();
  const { debounce } = useDebounce();

  const [deleteId, setDeleteId] = useState<number | null>(null); // Armazena o ID do item a ser excluído
  const [openDialog, setOpenDialog] = useState(false);
  const [openCreateStockModal, setOpenCreateStockModal] = useState(false);
  const [openUpdateStockModal, setOpenUpdateStockModal] = useState(false);
  const [stock, setStock] = useState<IStock | null>(null);
  const [stocks, setStocks] = useState<IStock[]>([]);
  const [filteredStocks, setFilteredStocks] = useState<IStock[]>([]);

  const [searchTerm, setSearchTerm] = useState(""); // Estado do campo de pesquisa
  const [userId, setUserId] = useState<string | undefined>(undefined);
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
        StockService.getAll(userId).then(async (result) => {
          setIsLoading(false);
          if (result instanceof Error) {
           
          } else {
            setStocks(result);
          }
        });
      });
    }
  }, [userId]);

  useEffect(() => {
    if (searchTerm === "") {
      setFilteredStocks(stocks);
    } else {
      const filtered = stocks.filter((stock) =>
        stock.name.toLowerCase().includes(searchTerm.toLowerCase())
      );

      setFilteredStocks(filtered);
    }
  }, [searchTerm, stocks]);

  const handleOpenCreate = () => setOpenCreateStockModal(true);
  const handleOpenUpdate = (stock: IStock) => {
    setStock(stock);
    setOpenUpdateStockModal(true);
  };
  const handleCloseCreate = () => setOpenCreateStockModal(false);
  const handleCloseUpdate = () => setOpenUpdateStockModal(false);

  const handleDelete = (id: number) => {
    setDeleteId(id);
    setOpenDialog(true);
  };

  const handleConfirmDelete = () => {
    if (deleteId === null) return;

    StockService.deleteById(deleteId).then((result) => {
      if (result instanceof Error) {
       
      } else {
        window.location.reload();
      }
      setOpenDialog(false);
      setDeleteId(null); // Limpa o ID após a exclusão
    });
  };

  return (
    <BaseLayout
      title="Stocks"
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
            {filteredStocks.length > 0 ? (
              filteredStocks.map((stock) => (
                <Grid item xs={12} sm={6} md={4} lg={3} key={stock.stockId}>
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
                      {stock.name?.toUpperCase()}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Descrição:</strong> {stock.description}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Quantidade:</strong> {stock.quantity}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Preço:</strong> R$ {stock.price?.toFixed(2)}
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
                        onClick={() => handleOpenUpdate(stock)}
                        fullWidth
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
                        onClick={() => handleDelete(stock.stockId)}
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
                  No stocks found.
                </Typography>
              </Grid>
            )}
          </Grid>
        )}
        <CreateStockModal
          open={openCreateStockModal}
          handleClose={handleCloseCreate}
        />
        <UpdateStockModal
          open={openUpdateStockModal}
          handleClose={handleCloseUpdate}
          stock={stock}
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
