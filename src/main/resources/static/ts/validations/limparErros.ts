const erroEmail = document.querySelector(".input-email") as HTMLDivElement;
const erroSenha = document.querySelector(".input-senha") as HTMLDivElement;

export function limparErro(container: HTMLElement, classe: string ) {
    container.querySelector(classe)?.remove();
}