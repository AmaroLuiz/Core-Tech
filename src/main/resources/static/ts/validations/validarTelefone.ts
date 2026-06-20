import {Telefone} from "../types/telefone.js";
import {mostrarErro} from "./erroDiv.js";
import {limparErro} from "./limparErros.js";


export function validarTefone(telefone: Telefone): boolean {

    const erroDDD = document.querySelector(".erro-ddd") as HTMLDivElement;
    const erroTelefone = document.querySelector(".erro-telefone") as HTMLDivElement;

    if (telefone.ddd == null || telefone.ddd.length > 2 || telefone.ddd.length < 2 ) {
        mostrarErro(erroDDD,  "preencha DDD com apenas 2 caracteres")
        return false;
    }
    if (telefone.ddd.length == 2){
        limparErro(erroDDD, ".erro-ddd")
        return true;
    }

    if (telefone.telefone == null || telefone.telefone.length > 9 || telefone.telefone.length < 8) {
        mostrarErro(erroTelefone, "preencha telefone com 8 ou 9 caracteres");
        return false;
    }
    if (telefone.telefone.length == 9 || telefone.telefone.length == 8){
        limparErro(erroTelefone, ".erro-telefone")
        return true;
    }

    return true;
}