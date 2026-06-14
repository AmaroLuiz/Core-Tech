const erroEmail = document.querySelector(".input-email");
const erroSenha = document.querySelector(".input-senha");
export function limparErro() {
    erroEmail.querySelector(".erro-texto")?.remove();
    erroSenha.querySelector(".erro-texto")?.remove();
}
