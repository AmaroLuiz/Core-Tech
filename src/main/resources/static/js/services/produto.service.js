import { api } from "./api.js";
export async function exibirDashboard() {
    const response = await api.get("/produto/dashboard");
    return response.data;
}
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
export async function salvarProduto(produto) {
    const response = await api.post("/produto", produto);
    return response.data;
}
export async function salvarCategoria(categoria) {
    const response = await api.post("/produto/categoria", categoria);
    return response.data;
}
export async function deletarCategoria(id) {
    const response = await api.delete(`/produto/categoria/${id}`);
}
export async function deletarProduto(id) {
    const response = await api.delete(`/produto/${id}`);
}
export async function atualizarProduto(produto, id) {
    const response = await api.put(`/produto/${id}`, produto);
    return response.data;
}
export async function atualizarCategoria(categoria, id) {
    const response = await api.put(`/produto/categoria/${id}`, categoria);
    return response.data;
}
