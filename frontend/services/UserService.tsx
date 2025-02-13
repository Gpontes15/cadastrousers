import axios from "axios";

const API_URL = "http://localhost:8080/users"; // Ajuste para o endereço do seu backend

export interface User {
  id?: number;
  name: string;
  email: string;
  cpf?: string;
}

export const UserService = {
  getUsers: async () => {
    const response = await axios.get<User[]>(API_URL);
    return response.data;
  },
  createUser: async (user: User) => {
    const response = await axios.post<User>(API_URL, user);
    return response.data;
  },
  updateUser: async (id: number, user: User) => {
    const response = await axios.put<User>(`${API_URL}/${id}`, user);
    return response.data;
  },
  deleteUser: async (id: number) => {
    await axios.delete(`${API_URL}/${id}`);
  },
};
