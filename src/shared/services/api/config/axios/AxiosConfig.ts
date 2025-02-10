import axios from "axios";
import { Enviroment } from "../../../../environment/Environment";

// Criando a instância do axios com baseURL configurada
const api = axios.create({
  baseURL: Enviroment.URL_BASE,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // Adiciona o token no cabeçalho
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export { api };
