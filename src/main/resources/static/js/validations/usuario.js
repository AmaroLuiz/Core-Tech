import { mostrarErro } from "./erroDiv.js";
export function validarUsuario(usuario) {
    const inputEmail = usuario.email.trim();
    const inputSenha = usuario.senha.trim();
    const erroEmail = document.querySelector(".input-email");
    const erroSenha = document.querySelector(".input-senha");
    erroEmail.querySelector(".erro-texto")?.remove();
    erroSenha.querySelector(".erro-texto")?.remove();
    if (!inputEmail) {
        mostrarErro(erroEmail, "adicione um email válido");
        return false;
    }
    const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRe.test(inputEmail)) {
        mostrarErro(erroEmail, "adicione um email válido");
        return false;
    }
    if (!inputSenha) {
        mostrarErro(erroSenha, "adicione uma senha válida");
        return false;
    }
    if (inputSenha.length < 6) {
        mostrarErro(erroSenha, "senha deve ter 6 a 12 caracteres");
        return false;
    }
    return true;
}
