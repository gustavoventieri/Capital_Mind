import { api } from "../config/axios/AxiosConfig";

export interface IExpense {
  expenseId: number;
  name: string;
  description: string;
  category: string;
  price: number;
  userId: string;
}

const getAll = async (id: string): Promise<IExpense[] | Error> => {
  try {
    const { data } = await api.get(`/expense/all/${id}`);

    if (data) {
      return data;
    }

    return [];
  } catch (error) {
    return new Error((error as { message: string }).message || "Error 404.");
  }
};

const create = async (
  dados: Omit<IExpense, "expenseId">
): Promise<string | Error> => {
  try {
    const { data } = await api.post<IExpense>("/expense/create", dados);

    if (typeof data === "string") return data;

    return new Error("Erro ao criar o registro.");
  } catch (error) {
    return new Error(
      (error as { message: string }).message || "Erro ao criar o registro."
    );
  }
};

const update = async (dados: IExpense): Promise<string | Error> => {
  try {
    const { data } = await api.put<IExpense>(
      `/expense/update/${dados.expenseId}`,
      dados
    );

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
    await api.delete(`/expense/delete/${id}`);
  } catch (error) {
    return new Error(
      (error as { message: string }).message || "Erro ao apagar o registro."
    );
  }
};

export const ExpenseService = {
  getAll,
  create,
  deleteById,
  update,
};
