import * as React from "react";
import { styled } from "@mui/material/styles";
import Checkbox, { CheckboxProps } from "@mui/material/Checkbox";
import { Box, Typography } from "@mui/material";

const BpIcon = styled("span")(({ theme }) => ({
  borderRadius: 3,
  width: 18,
  height: 18,
  boxShadow:
    "inset 0 0 0 1px rgba(16,22,26,.2), inset 0 -1px 0 rgba(16,22,26,.1)",
  backgroundColor: "#f5f8fa",
  backgroundImage:
    "linear-gradient(180deg,hsla(0,0%,100%,.8),hsla(0,0%,100%,0))",
  ".Mui-focusVisible &": {
    outline: "2px auto rgba(19,124,189,.6)",
    outlineOffset: 2,
  },
  "input:hover ~ &": {
    backgroundColor: "#ebf1f5",
    ...theme.applyStyles("dark", {
      backgroundColor: "#30404d",
    }),
  },
  "input:disabled ~ &": {
    boxShadow: "none",
    background: "rgba(206,217,224,.5)",
    ...theme.applyStyles("dark", {
      background: "rgba(57,75,89,.5)",
    }),
  },
  ...theme.applyStyles("dark", {
    boxShadow: "0 0 0 1px rgb(16 22 26 / 40%)",
    backgroundColor: "#394b59",
    backgroundImage:
      "linear-gradient(180deg,hsla(0,0%,100%,.05),hsla(0,0%,100%,0))",
  }),
}));

const BpCheckedIcon = styled(BpIcon)({
  backgroundColor: "#F97316",
  backgroundImage:
    "linear-gradient(180deg,hsla(0,0%,100%,.1),hsla(0,0%,100%,0))",
  "&::before": {
    display: "block",
    width: 18,
    height: 18,
    backgroundImage:
      "url(\"data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3E%3Cpath" +
      " fill-rule='evenodd' clip-rule='evenodd' d='M12 5c-.28 0-.53.11-.71.29L7 9.59l-2.29-2.3a1.003 " +
      "1.003 0 00-1.42 1.42l3 3c.18.18.43.29.71.29s.53-.11.71-.29l5-5A1.003 1.003 0 0012 5z' fill='%23fff'/%3E%3C/svg%3E\")",
    content: '""',
  },
  "input:hover ~ &": {
    backgroundColor: "#F97316",
  },
});

// Checkbox Customizado
const BpCheckbox = (props: CheckboxProps) => {
  return (
    <Checkbox
      sx={{
        "&:hover": { bgcolor: "transparent" },
        // Ajustando marginLeft diretamente no sx
        marginLeft: 0, // Verifica se marginLeft foi passado
      }}
      disableRipple
      color="default"
      checkedIcon={<BpCheckedIcon />}
      icon={<BpIcon />}
      inputProps={{ "aria-label": "Checkbox demo" }}
      {...props}
    />
  );
};

export const CustomizedCheckbox = ({
  onChange,
  marginLeft = 0, // Definindo marginLeft padrão
  error = false, // Definindo error como padrão
  errorMessage = "", // Mensagem de erro do Yup
  text = "",
}: {
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  marginLeft?: number;
  error?: boolean;
  errorMessage?: string; // Mensagem de erro para exibição
  text: string;
}) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "flex-start",
      }}
    >
      <Box display="flex" flexDirection="row">
        <BpCheckbox
          onChange={onChange}
          sx={{
            marginLeft,
            borderColor: error ? "error.main" : undefined, // Aplicando borda de erro se houver
          }}
        />
        <Typography variant="body2" color="textSecondary" sx={{ mt: 1.4 }}>
          {text}
        </Typography>
      </Box>
      {error && (
        <Typography variant="body2" color="error" sx={{ mt: 1 }}>
          {errorMessage}
        </Typography>
      )}
    </Box>
  );
};
