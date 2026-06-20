import {Endereco} from "../types/endereco.js";
import {mostrarErro} from "./erroDiv.js";

export function validarEndereco(endereco: Endereco) {

    const erroEstado = document.querySelector(".erro-estado") as HTMLDivElement;
    const erroCep = document.querySelector(".erro-cep") as HTMLDivElement;
    const erroCidade = document.querySelector(".erro-cidade") as HTMLDivElement;
    const erroBairro = document.querySelector(".erro-bairro") as HTMLDivElement;
    const erroRua = document.querySelector(".erro-rua") as HTMLDivElement;
    const erroNumero = document.querySelector(".erro-numero") as HTMLDivElement;

    if (endereco.estado == null || endereco.estado.length == 0) {
        mostrarErro(erroEstado, "preencha estado");
        return false;
    }
    if (endereco.estado.length != 2){
        mostrarErro(erroEstado, "preencha estado com 2 caracteres");
        return false;
    }
    if (endereco.estado.length == 2){
        return true;
    }
    if (endereco.cep == null || endereco.cep.length != 8) {
        mostrarErro(erroCep, "preencha CEP");
        return false;
    }

    if (endereco.cep.length != 8){
        mostrarErro(erroCep, "preencha CEP com 8 caracteres");
        return false;
    }
    if (endereco.cidade == null || endereco.cidade.length == 0) {
        mostrarErro(erroCidade, "preencha cidade");
        return false;
    }
    if (endereco.rua == null || endereco.rua.length == 0) {
        mostrarErro(erroRua, "preencha rua");
        return false;
    }
    if (endereco.numero == null) {
        mostrarErro(erroNumero, "preencha numero");
        return false;
    }
    if (endereco.numero <= 0){
        mostrarErro(erroNumero, "preencha numero com valor positivo");
        return false;
    }
    if (endereco.complemento == null || endereco.complemento.length == 0) {
        mostrarErro(erroBairro, "preencha bairro");
        return false;
    }
    return true;
}