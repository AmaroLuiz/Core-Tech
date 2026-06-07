import { api } from "../service/api.js";
export async function buscarTodosOsProduto() {
    const response = await api.get("/produto/categoria-all");
    return response.data;
}
export async function buscarProdutoPorId(id) {
    const response = await api.get(`/produto/${id}`);
    return response.data;
}
export async function buscarProdutoPorCategoria(categoria) {
    const response = await api.get(`/produto/categoria/${categoria}`);
    return response.data;
}
