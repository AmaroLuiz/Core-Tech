import {Telefone} from "../types/telefone.js";

export function coletarTelefone(): Telefone{

    let telefoneInput: HTMLInputElement | null = document.querySelector(".telefone-input") as HTMLInputElement;
    let dddInput: HTMLInputElement | null = document.querySelector(".ddd-input") as HTMLInputElement;

    return {

        telefone: telefoneInput.value.trim() || null,
        ddd: dddInput.value.trim() || null

    }

}