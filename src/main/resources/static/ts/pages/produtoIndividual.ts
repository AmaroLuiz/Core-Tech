import { exibirProdutoPorId } from "../services/produto.service.js";
import { Produto } from "../types/produto.js";


async function exibirProduto(id: number | null)  {

    if (id === null) {
        return;
    }

    try {
        const produto: Produto = await exibirProdutoPorId(id);
        if (!produto) {
            mostrarErro();
        } else {
            preencherDadosProduto(produto);
            const btnComprar = document.getElementById("comprar") as HTMLButtonElement;

            btnComprar?.addEventListener("click", () => {

                const telefone = "5544920011084";

                const mensagem =
                    `Olá, gostei do ${produto.nome} por R$ ${produto.preco?.toString().replace('.', ',')} e gostaria de comprá-lo.`;

                const url =
                    `https://wa.me/${telefone}?text=${encodeURIComponent(mensagem)}`;

                window.open(url, "_blank");

            });
        }

    } catch (error) {

        mostrarErro();

    }
}


function mostrarErro(): void {

    const especificacoes = document.querySelector(".especificacoes") as HTMLElement;
    const descricao = document.querySelector(".descricao-produto") as HTMLElement;
    const produto = document.querySelector(".produto-hero") as HTMLElement;

    especificacoes.style.display = "none";
    descricao.style.display = "none";
    produto.style.display = "none";


    const main = document.querySelector("main") as HTMLElement;


    const div: HTMLDivElement = document.createElement("div");

    div.style.display = "flex";
    div.style.flexDirection = "column";
    div.style.alignItems = "center";


    const aviso: HTMLParagraphElement = document.createElement("p");

    aviso.textContent = "Produto não encontrado.";

    aviso.style.textAlign = "center";
    aviso.style.color = "#ff8b3d";
    aviso.style.fontSize = "1.5rem";


    const voltar: HTMLAnchorElement = document.createElement("a");

    voltar.style.backgroundColor = "#ff6b00";
    voltar.style.display = "inline-block";
    voltar.style.marginTop = "20px";
    voltar.style.padding = "10px 20px";

    voltar.textContent = "Voltar para o catálogo";
    voltar.href = "catalogo.html";

    voltar.style.color = "#fff";
    voltar.style.borderRadius = "5px";
    voltar.style.textDecoration = "none";
    voltar.style.fontSize = "1rem";
    voltar.style.textAlign = "center";


    main.appendChild(div);

    div.appendChild(aviso);
    div.appendChild(voltar);

    main.prepend(div);

}


function coletarURL(): number | null {

    const params = new URLSearchParams(window.location.search);

    const idString = params.get("id");

    if (!idString) {
        return null;
    }

    return Number(idString);
}


function preencherDadosProduto(produto: Produto) {


    const nome = document.getElementById("nome") as HTMLElement;

    nome.textContent = produto.nome;


    const categoria = document.getElementById("categoria") as HTMLSpanElement;

    categoria.textContent = produto.nomeCategoria ?? "";


    const categoria2 = document.querySelector(".categoria") as HTMLParagraphElement;

    categoria2.textContent = produto.nomeCategoria ?? "";


    const preco = document.getElementById("preco") as HTMLParagraphElement;

    preco.textContent = `R$ ${produto.preco?.toFixed(2)}`;


    const descricao = document.getElementById("descricao") as HTMLParagraphElement;

    descricao.textContent = produto.descricao;


    const imagem = document.getElementById("imagem") as HTMLImageElement;

    if (produto.imagemUrl != null) {

        imagem.src = produto.imagemUrl;

    }

}


await exibirProduto(coletarURL());