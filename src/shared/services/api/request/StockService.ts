import { api } from "../config/axios/AxiosConfig";

export interface IStock {
  stockId: number;
  name: string;
  description: string;
  quantity: number;
  price: number;
  userData: string;
}

const getAll = async (id: string): Promise<IStock[] | Error> => {
  try {
    const { data } = await api.get(`/stock/all/${id}`);

    if (data) {
      return data;
    }

    return [];
  } catch (error) {
    console.error(error);
    return new Error((error as { message: string }).message || "Error 404.");
  }
};

export const StockService = {
  getAll,
};
