import {Endereco} from "../types/endereco.js";

export async function coletarEndereco() {

    let estadoInput = document.querySelector("#estado") as HTMLInputElement;
    let cidadeInput = document.querySelector("#cidade") as HTMLInputElement;
    let cepInput = document.querySelector("#cep") as HTMLInputElement;
    let ruaInput = document.querySelector("#rua") as HTMLInputElement;
    let numeroInput = document.querySelector("#numero") as HTMLInputElement;
    let complementoInput = document.querySelector("#complemento") as HTMLInputElement;

    return {

        estado: estadoInput.value,
        cidade: cidadeInput.value,
        cep: cepInput.value,
        rua: ruaInput.value,
        numero: Number(numeroInput.value),
        complemento: complementoInput.value

    }

}