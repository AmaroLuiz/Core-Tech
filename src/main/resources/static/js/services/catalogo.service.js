import { api } from "./api.js";
export async function exibirTodasCategorias() {
    const response = await api.get("/produto/todas-categoria");
    return response.data;
}
