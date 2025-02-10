import {
  Box,
  CircularProgress,
  Typography,
  Paper,
  Grid,
  TextField,
} from "@mui/material";
import { ToolsBar } from "../../shared/components";
import { BaseLayout } from "../../shared/layouts";
import { useState, useEffect } from "react";
import { useAuthContext } from "../../shared/contexts";
import { getUserIdFromJWT, IStock, StockService } from "../../shared/services";
import { useDebounce } from "../../shared/hooks";

export const Stock = () => {
  const { token } = useAuthContext();
  const { debounce } = useDebounce();
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
            console.log(result.message);
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

  return (
    <BaseLayout
      title="Stocks"
      toolsBar={
        <ToolsBar
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

          overflowX: "hidden",
          overflowY: "auto", // Permite rolagem vertical se necessário
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
                      minHeight: 150,
                      display: "flex",
                      flexDirection: "column",
                      justifyContent: "space-between",
                    }}
                  >
                    <Typography variant="h6">
                      {stock.name.toUpperCase()}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Descrição:</strong> {stock.description}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Quantidade:</strong> {stock.quantity}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Preço:</strong> R$ {stock.price.toFixed(2)}
                    </Typography>
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
                  Nenhuma criptomoeda encontrada.
                </Typography>
              </Grid>
            )}
          </Grid>
        )}
      </Box>
    </BaseLayout>
  );
};
