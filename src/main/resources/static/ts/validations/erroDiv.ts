export function mostrarErro(container: HTMLElement, mensagem: string) {
    container.querySelector(".erro-texto")?.remove();

    const erro = document.createElement("p");
    erro.textContent = mensagem;
    erro.classList.add("erro-texto");
    erro.style.color = "red";

    container.appendChild(erro);
}