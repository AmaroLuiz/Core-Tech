import {Usuario} from "../types/usuario.js";
import {mostrarErro} from "./erroDiv.js";

export function validarUsuario(gmail: string) {

    const erroEmail = document.querySelector(".erro-gmail") as HTMLDivElement;

    const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRe.test(gmail)) {
        mostrarErro(erroEmail, "adicione um gmail válido");
        return false;
    }
    return true;
}