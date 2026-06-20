const erroEmail = document.querySelector(".input-email");
const erroSenha = document.querySelector(".input-senha");
export function limparErro(container, classe) {
    container.querySelector(classe)?.remove();
}
