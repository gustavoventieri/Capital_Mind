import { api } from "../config/axios/AxiosConfig";

export interface IStock {
  stockId: number;
  name: string;
  description: string;
  quantity: number;
  price?: number;
  userId: string;
}

const getAll = async (id: string): Promise<IStock[] | Error> => {
  try {
    const { data } = await api.get(`/stock/all/${id}`);

    if (data) {
      return data;
    }

    return [];
  } catch (error) {
    return new Error((error as { message: string }).message || "Error 404.");
  }
};

const create = async (
  dados: Omit<IStock, "stockId">
): Promise<string | Error> => {
  try {
    const { data } = await api.post<IStock>("/stock/create", dados);

    if (typeof data === "string") return data;

    return new Error("Erro ao criar o registro.");
  } catch (error) {
    return new Error(
      (error as { message: string }).message || "Erro ao criar o registro."
    );
  }
};

const update = async (dados: IStock): Promise<string | Error> => {
  try {
    console.log(dados);
    const { data } = await api.put<IStock>(`/stock/update/${dados.stockId}`, {
      name: dados.name,
      description: dados.description,
      quantity: dados.quantity,
      userId: dados.userId,
    });
    console.log("aqui" + data);
    if (typeof data === "string") return data;

    return new Error("Erro ao criar o registro.");
  } catch (error) {
    return new Error(
      (error as { message: string }).message || "Erro ao criar o registro."
    );
  }
};

const deleteById = async (id: number): Promise<void | Error> => {
  try {
    await api.delete(`/stock/delete/${id}`);
  } catch (error) {
    return new Error(
      (error as { message: string }).message || "Erro ao apagar o registro."
    );
  }
};

export const StockService = {
  getAll,
  create,
  deleteById,
  update,
};
