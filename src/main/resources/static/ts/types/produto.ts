import {Categoria} from "./categoria.js";

export interface Produto {

    id?: number;
    nome: string | null;
    descricao: string  | null;
    sku?: string  | null;
    preco: number  | null;
    imagemUrl: string  | null;
    categoria?: Categoria  | null,
    nomeCategoria?: string  | null

}