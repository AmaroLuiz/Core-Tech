import { mostrarErro } from "./erroDiv.js";
export function validarUsuario(gmail) {
    const erroEmail = document.querySelector(".erro-gmail");
    const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRe.test(gmail)) {
        mostrarErro(erroEmail, "adicione um gmail válido");
        return false;
    }
    return true;
}
