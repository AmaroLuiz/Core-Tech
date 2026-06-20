import { api } from "./api.js";
import {coletarCategorias} from "../pages/categoria.js";
import {coletarProduto} from "../pages/produto.js";
import {Produto} from "../types/produto.js";
import {Categoria} from "../types/categoria.js";


export async function buscarTodosOsProduto() {

    const response = await api.get(
        "/produto/categoria-all"
    );

    return response.data;

}

export async function buscarProdutoPorId(id: number) {

    const response = await api.get(
        `/produto/${id}`
    );

    return response.data;

}

export async function buscarProdutoPorCategoria(categoria: number) {
    const response = await api.get(
        `/produto/categoria/${categoria}`
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
        `/produto/${id}`
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
