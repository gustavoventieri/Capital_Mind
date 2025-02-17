import React, { useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import { useThemeContext } from "../../../shared/contexts";

const suggestionData = {
  categorias: [
    "Moradia",
    "Aluguel",
    "Condomínio",
    "Energia",
    "Água",
    "Internet",
    "Manutenção",
    "Transporte",
    "Combustível",
    "Transporte Público",
    "Uber/Taxi",
    "Estacionamento",
    "Manutenção Veicular",
    "Alimentação",
    "Supermercado",
    "Restaurantes",
    "Cafeterias",
    "Delivery",
    "Saúde e Bem-estar",
    "Plano de Saúde",
    "Medicamentos",
    "Academia",
    "Terapia",
    "Exames",
    "Educação",
    "Mensalidade Escolar",
    "Cursos",
    "Livros e Materiais",
    "Lazer e Entretenimento",
    "Cinema/Teatro",
    "Eventos",
    "Viagens",
    "Passeios",
    "Financeiro",
    "Investimentos",
    "Empréstimos",
    "Juros",
    "Taxas Bancárias",
    "Vestuário e Beleza",
    "Roupas",
    "Calçados",
    "Acessórios",
    "Cuidados Pessoais",
    "Outros",
    "Presentes",
    "Doações",
    "Assinaturas e serviços",
    "Streaming",
    "Revistas",
    "Software",
    "Animais de estimação",
    "Veterinário",
   
  ],
};

// Recebe 'value', 'onChange', 'error' e 'helperText' como props
interface DynamicSuggestionsInputProps {
  value: string;
  onChange: (value: string) => void;
  error?: boolean;
  helperText?: string;
}
export const combinedSuggestions = [...suggestionData.categorias];
export const DynamicSuggestionsInput = ({
  value,
  onChange,
  error,
  helperText,
}: DynamicSuggestionsInputProps) => {
  const [inputValue, setInputValue] = useState(value);
  const { themeName } = useThemeContext();
  // Combine indexes and stocks into one list for suggestions

  return (
    <Autocomplete
      freeSolo
      options={combinedSuggestions.filter((option) =>
        option.toLowerCase().includes(inputValue.toLowerCase())
      )}
      onInputChange={(event, newInputValue) => {
        setInputValue(newInputValue);
        onChange(newInputValue); // Passa o novo valor para o parent
      }}
      value={value} // Usando o valor do pai (name)
      renderInput={(params) => (
        <TextField
          {...params}
          label="Category"
          variant="outlined"
          fullWidth
          error={error}
          helperText={helperText}
        />
      )}
      sx={{
        ".MuiAutocomplete-clearIndicator": {
          color: themeName === "dark" ? "white" : "black", // Muda a cor para laranja
        },
      }}
    />
  );
};
