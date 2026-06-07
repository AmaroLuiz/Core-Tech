import {Telefone} from "../types/telefone.js";

export function coletarTelefone(): Telefone{

    let telefoneInput = document.querySelector("#telefone") as HTMLInputElement;
    let dddInput = document.querySelector("#ddd") as HTMLInputElement;

    return {

        telefone: telefoneInput.value,
        ddd: dddInput.value

    }

}