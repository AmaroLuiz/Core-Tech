const erroEmail = document.querySelector(".input-email") as HTMLDivElement;
const erroSenha = document.querySelector(".input-senha") as HTMLDivElement;

export function limparErro() {
    erroEmail.querySelector(".erro-texto")?.remove();
    erroSenha.querySelector(".erro-texto")?.remove();
}