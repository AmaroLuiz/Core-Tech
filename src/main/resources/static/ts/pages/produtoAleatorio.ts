import { buscarTodosOsProduto } from "../services/produto.service.js";
import { Produto } from "../types/produto.js";

const produtos: Produto[] = await buscarTodosOsProduto();

const divProducts = document.querySelector(".products") as HTMLDivElement;

if (divProducts) {

    const produtosAleatorios: Produto[] = selecionarProdutosAleatorios(produtos, 10);

    produtosAleatorios.forEach((produto: Produto) => {

        divProducts.innerHTML += `
            <div class="product-card">

                <img src="${produto.imagemUrl}" alt="${produto.nome}">

                <h3>${produto.nome}</h3>

                <p>R$ ${produto.preco?.toFixed(2)}</p>

                <a href="produtoIndividual.html?id=${produto.id}">
                    Ver Produto
                </a>

            </div>
        `;
    });

}


function selecionarProdutosAleatorios(
    produtos: Produto[],
    quantidade: number = 10
): Produto[] {

    const copia: Produto[] = [...produtos];

    for (let i = copia.length - 1; i > 0; i--) {

        const j: number = Math.floor(Math.random() * (i + 1));

        [copia[i]!, copia[j]!] = [copia[j]!, copia[i]!];
    }

    return copia.slice(0, quantidade);
}