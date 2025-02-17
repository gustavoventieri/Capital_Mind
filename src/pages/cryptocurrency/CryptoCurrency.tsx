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
import {
  CryptoCurrencyService,
  getUserIdFromJWT,
  ICryptoCurrency,
} from "../../shared/services";
import { useDebounce } from "../../shared/hooks";

export const CryptocCurrency = () => {
  const { token } = useAuthContext();
  const { debounce } = useDebounce();
  const [cryptos, setCryptos] = useState<ICryptoCurrency[]>([]);
  const [filteredCryptos, setFilteredCryptos] = useState<ICryptoCurrency[]>([]);
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
        CryptoCurrencyService.getAll(userId).then((result) => {
          setIsLoading(false);
          if (result instanceof Error) {
            
          } else {
            setCryptos(result);
            setFilteredCryptos(result); // Inicializa os dados filtrados com todos os resultados
          }
        });
      });
    }
  }, [userId]);

  useEffect(() => {
    if (searchTerm === "") {
      setIsLoading(true);
      setFilteredCryptos(cryptos);
      setIsLoading(false);
    } else {
      setIsLoading(true);
      const filtered = cryptos.filter((crypto) =>
        crypto.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setFilteredCryptos(filtered);
      setIsLoading(false);
    }
  }, [searchTerm, cryptos]);

  return (
    <BaseLayout
      title="Crypto Currency"
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
            {filteredCryptos.length > 0 ? (
              filteredCryptos.map((crypto) => (
                <Grid item xs={12} sm={6} md={4} lg={3} key={crypto.cryptoId}>
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
                      {crypto.name.toUpperCase()}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Descrição:</strong> {crypto.description}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Quantidade:</strong> {crypto.quantity}
                    </Typography>
                    <Typography variant="body2">
                      <strong>Preço:</strong> R$ {crypto.price.toFixed(2)}
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
