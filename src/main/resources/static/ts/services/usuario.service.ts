import {api} from './api.js';
import {Login} from '../types/usuario.js';
import {Endereco} from "../types/endereco.js";
import {Telefone} from "../types/telefone.js";

export async function login(usuario: Login){

    const response = await api.post('/usuario/login', usuario);

    return response.data;
}

export async function salvaUsuario(usuario: Login) {
    const response = await api.post('/usuario/criar', usuario);

    return response.data;
}
export async function atualizarUsuario(nome: string,gmail: any ,id: number) {
    const response = await api.put(
        `/usuario?id=${id}`, {nome, gmail}
    );
    return response.data;
}
export async function buscarUsuarioPorGmail(gmail: string){

    const response = await api.get(`/usuario?email=${gmail}`)
    return response.data;
}

export async function salvarTelefone(telefone: Telefone) {

    const response = await api.post(
        "/usuario/criar/telefone", telefone
    );
    return response.data;
}


export async function atualizarTelefone(telefone: Telefone, telefoneId: number) {
    const response = await api.put(
        `/usuario/telefone?id=${telefoneId}`, telefone
    );
    return response.data;
}

export async function salvarEndereco(endereco: Endereco) {
    const response = await api.post(
        "/usuario/criar/endereco",
        endereco
    );
    return response.data;
}

export async function atualizarEndereco(endereco: Endereco, enderecoId: number) {
    const response = await api.put(
        `/usuario/endereco?id=${enderecoId}`, endereco
    );
    return response.data;
}