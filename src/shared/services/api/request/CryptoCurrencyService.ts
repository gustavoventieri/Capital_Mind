import { api } from "../config/axios/AxiosConfig";

export interface ICryptoCurrency {
  cryptoId: number;
  name: string;
  description: string;
  quantity: number;
  price: number;
  userData: string;
}

const getAll = async (id: string): Promise<ICryptoCurrency[] | Error> => {
  try {
    const { data } = await api.get(`/crypto/all/${id}`);

    if (data) {
      return data;
    }

    return [];
  } catch (error) {
    console.error(error);
    return new Error((error as { message: string }).message || "Error 404.");
  }
};

export const CryptoCurrencyService = {
  getAll,
};
