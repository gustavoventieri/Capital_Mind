import {
  Box,
  Button,
  Paper,
  TextField,
  useTheme,
} from "@mui/material";
import React from "react";
import AddIcon from "@mui/icons-material/Add";
interface IToolsbarProps {
  searchText?: string;
  showSearchInput?: boolean;
  onSearchTextChange?: (newText: string) => void;
  newButtonText?: string;
  showNewButton?: boolean;
  onNewButtonClick?: VoidFunction;
}

export const ToolsBar: React.FC<IToolsbarProps> = ({
  searchText = "",
  onSearchTextChange,
  showSearchInput = false,
  onNewButtonClick,
  newButtonText = "New",
  showNewButton = true,
}) => {
  const theme = useTheme();
  return (
    <Box
      gap={1}
      marginX={2}
      padding={1}
      paddingX={2}
      display="flex"
      alignItems="center"
      height={theme.spacing(5)}
      component={Paper}
    >
      {showSearchInput && (
        <TextField
          size="small"
          value={searchText}
          placeholder="Search..."
          onChange={(e) => onSearchTextChange?.(e.target.value)}
          sx={{
            "& .MuiOutlinedInput-root": {
              "& fieldset": {
                borderColor: "primary.main", // Cor da borda padrão
              },
              "&:hover fieldset": {
                borderColor: "primary.light", // Cor da borda ao passar o mouse
              },
            },
          }}
        />
      )}

      <Box flex={1} display="flex" justifyContent="end">
        {showNewButton && (
          <Button
            onClick={onNewButtonClick}
            variant="contained"
            sx={{
              backgroundColor: "primary.main",

              "&:hover": {
                backgroundColor: "primary.dark",
              },
              height: "40px",

              paddingTop: 1.4,
            }}
            endIcon={<AddIcon fontSize="small" sx={{ paddingBottom: 0.75 }} />}
          >
            {newButtonText}
          </Button>
        )}
      </Box>
    </Box>
  );
};
