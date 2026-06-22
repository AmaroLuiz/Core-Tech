import { api } from "./api.js";
import {Produto} from "../types/produto.js";
import {Categoria} from "../types/categoria.js";


export async function exibirDashboard(){

    const response = await api.get("/produto/dashboard");

    return response.data;

}

export async function buscarTodosOsProduto() {

    const response = await api.get(
        "/produto/categoria-all"
    );

    return response.data;

}

export async function exibirProdutoPorId(id: number) {
    const response = await api.get(
        `/produto/id-${id}`
    );
    return response.data;

}

export async function exibirProdutoPorSku(sku: string) {
    const response = await api.get(
        `/produto/sku-${sku}`
    );

    return response.data;

}

export async function exibirProdutoPorNome(nome: string) {
    const response = await api.get(
        `/produto/nome-${nome}`
    );

    return response.data;

}

export async function exibirProdutoPorCategoria(slug: string) {
    const response = await api.get(
        `/produto/slug/${slug}`
    );

    return response.data;

}


export async function salvarProduto(produto: Produto) {
    const response = await api.post(
        "/produto",
        produto
    );

    return response.data;

}

export async function salvarCategoria(categoria: Categoria) {
    const response = await api.post(
        "/produto/categoria",
        categoria
    )

    return response.data;

}

export async function deletarCategoria(id: number) {
    const response = await api.delete(
        `/produto/categoria/${id}`
    )
}

export async function deletarProduto(id: number) {
    const response = await api.delete(
        `/produto?id=${id}`
    )
}


export async function atualizarProduto(produto: Produto, id: number) {
    const response = await api.put(
        `/produto/${id}`,
        produto
    )

    return response.data;

}

export async function atualizarCategoria(categoria: Categoria, id: number) {
    const response = await api.put(
        `/produto/categoria/${id}`,
        categoria
    )

    return response.data;

}

