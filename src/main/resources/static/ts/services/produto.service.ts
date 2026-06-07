import { api } from "./api";
import {coletarCategorias} from "../pages/categoria.js";
import {coletarProduto} from "../pages/produto.js";


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