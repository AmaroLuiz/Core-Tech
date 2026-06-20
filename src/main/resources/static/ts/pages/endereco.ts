import {Endereco} from "../types/endereco.js";

export function coletarEndereco(): Endereco {

    let estadoInput: HTMLInputElement | null = document.querySelector(".estado-input") as HTMLInputElement;
    let cepInput: HTMLInputElement | null  = document.querySelector(".cep-input") as HTMLInputElement;
    let cidadeInput: HTMLInputElement | null  = document.querySelector(".cidade-input") as HTMLInputElement;
    let bairroInput: HTMLInputElement | null  = document.querySelector(".bairro-input") as HTMLInputElement;
    let ruaInput: HTMLInputElement | null  = document.querySelector(".rua-input") as HTMLInputElement;
    let numeroInput: HTMLInputElement | null  = document.querySelector(".numero-input") as HTMLInputElement;
    const vazio = null;


    
    return {
        estado: estadoInput.value.trim() || null,
        cidade: cidadeInput.value.trim() || null,
        cep: cepInput.value.trim() || null,
        rua: ruaInput.value.trim() || null,
        numero: numeroInput.value.trim()
            ? Number(numeroInput.value)
            : null,
        complemento: bairroInput.value.trim() || null
    };

}