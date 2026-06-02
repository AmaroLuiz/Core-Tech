import { produtos, categorias } from "../js/allproducts.js";


const divProducts = document.querySelector(".products");

const produtosAleatorios = selecionarProdutosAleatorios(produtos, 10);

produtosAleatorios.forEach(produto => {

    divProducts.innerHTML += `
        <div class="product-card">

            <img src="${produto.imagem}" alt="${produto.nome}">

            <h3>${produto.nome}</h3>

            <p>R$ ${produto.preco.toFixed(2)}</p>

            <a href="produto.html?id=${produto.id}">
                Ver Produto
            </a>

        </div>
    `;
});



function selecionarProdutosAleatorios(produtos, quantidade = 10) {

    const copia = [...produtos];

    for (let i = copia.length - 1; i > 0; i--) {

        const j = Math.floor(Math.random() * (i + 1));

        [copia[i], copia[j]] = [copia[j], copia[i]];
    }

    return copia.slice(0, quantidade);
}
