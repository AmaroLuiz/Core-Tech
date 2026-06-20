import {Endereco} from "./endereco.js";
import {Telefone} from "./telefone.js";
export interface Login {

    email: string;
    senha: string;

}
export interface Usuario {
    id?: number;
    nome: string;
    email: string;
    senha: string;
    enderecos: Endereco[];
    telefones: Telefone[];

}