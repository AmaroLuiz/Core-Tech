import { api } from './api.js';
export async function login(usuario) {
    const response = await api.post('/usuario/login', usuario);
    return response.data;
}
export async function salvaUsuario(usuario) {
    const response = await api.post('/usuario/criar', usuario);
    return response.data;
}
export async function atualizarUsuario(nome, gmail, id) {
    const response = await api.put(`/usuario?id=${id}`, { nome, gmail });
    return response.data;
}
export async function buscarUsuarioPorGmail(gmail) {
    const response = await api.get(`/usuario?email=${gmail}`);
    return response.data;
}
export async function salvarTelefone(telefone) {
    const response = await api.post("/usuario/criar/telefone", telefone);
    return response.data;
}
export async function deletarUsuairPorGmail(gmail) {
    const response = await api.delete(`/usuario/${gmail}`);
    return response.data;
}
export async function atualizarTelefone(telefone, telefoneId) {
    const response = await api.put(`/usuario/telefone?id=${telefoneId}`, telefone);
    return response.data;
}
export async function salvarEndereco(endereco) {
    const response = await api.post("/usuario/criar/endereco", endereco);
    return response.data;
}
export async function atualizarEndereco(endereco, enderecoId) {
    const response = await api.put(`/usuario/endereco?id=${enderecoId}`, endereco);
    return response.data;
}
