import { produtos, categorias } from "./allproducts.js";


const divElements = document.getElementById("product-page");

let produto;

try {
    const params = new URLSearchParams(window.location.search);

    const id = Number(params.get("id"));

    produto = produtos.find(p => p.id === id);

    if (!produto) {
        mostrarErro();
    } else {
        const categoria = categorias.find(c => c.id === produto.categoriaId);
        verificarProduto(produto, categoria);
    }

    function verificarProduto(produto, categoria) {

        document.querySelectorAll(".categoria").forEach(el => {
            el.textContent = categoria.nome;
        });
        document.getElementById("nome").textContent = produto.nome;
        document.getElementById("preco").textContent = `R$ ${produto.preco.toFixed(2)}`;
        document.getElementById("descricao").textContent = produto.descricao;
        document.getElementById("imagem").src = produto.imagem;

    }

    function mostrarErro() {

        console.log("mostrarErro foi chamada");

        document.querySelector(".especificacoes").style.display = "none";
        document.querySelector(".descricao-produto").style.display = "none";
        document.querySelector(".produto-hero").style.display = "none";

        const main = document.querySelector("main");

        const div = document.createElement("div");
        div.style.display = "flex";
        div.style.flexDirection = "column";
        div.style.alignItems = "center";

        const aviso = document.createElement("p");
        aviso.textContent = "Produto não encontrado.";

        aviso.style.textAlign = "center";
        aviso.style.color = "#ff8b3d";
        aviso.style.fontSize = "1.5rem";

        const voltar = document.createElement("a");
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

} catch (error) {
    divElements.innerHTML = "<p>Produto não encontrado.</p>";
}


const btnComprar = document.getElementById("comprar");

if (btnComprar) {
    btnComprar.addEventListener("click", () => {

        const telefone = "5544920011084";

        const mensagem =
            `Olá, gostei do ${produto.nome} por R$ ${produto.preco.toFixed(2).replace('.', ',')} e gostaria de comprá-lo.`;

        const url =
            `https://wa.me/${telefone}?text=${encodeURIComponent(mensagem)}`;

        window.open(url, "_blank");
    });
}

