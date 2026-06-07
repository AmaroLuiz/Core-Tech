import {Usuario} from "../types/usuario.js";

export async function coletarUsuario() {

    let emailInput = document.querySelector("#email") as HTMLInputElement;
    let nomeInput = document.querySelector("#nome") as HTMLInputElement;
    let senhaInput = document.querySelector("#senha") as HTMLInputElement;

    return {

        email: emailInput.value,
        nome: nomeInput.value,
        senha: senhaInput.value

    }

}