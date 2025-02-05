import { api } from "../config/";

interface IAuth {
  token: string;
}

interface ILogin {
  email: string;
  password: string;
}

interface IRegister {
  name: string;
  email: string;
  password: string;
  salary: number;
}

const login = async (dados: ILogin): Promise<IAuth | Error> => {
  try {
    const { data } = await api.post("/auth/login", dados);

    if (data) {
      return data;
    }

    return new Error("Erro no login.");
  } catch (error) {
    console.error(error);
    return new Error(
      (error as { message: string }).message || "Erro no login."
    );
  }
};

const register = async (dados: IRegister): Promise<IAuth | Error> => {
  try {
    const { data } = await api.post("/auth/register", dados);

    if (data) {
      return data;
    }

    return new Error("Erro no login.");
  } catch (error) {
    console.error(error);
    return new Error(
      (error as { message: string }).message || "Erro no login."
    );
  }
};

export const AuthService = {
  login,
  register,
};
