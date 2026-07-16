import {Produto} from "../types/produto.js";
import {exibirProdutoPorCategoria} from "../services/produto.service.js";

export async function exibirProdutosPorCategoria(slug: string | null) {
    const categoria = slug
    if (categoria !== null){

        const produtos = await exibirProdutoPorCategoria(categoria);
        produtos.forEach((produto: Produto) => {
            criarCardProduto(produto)
        })
    } else{
        let html = ""
        return html += `
        <h3> Produto não encontradoooo</h3>>
        `
    }



}

function criarCardProduto(produto:Produto){
    let divProducts = document.querySelector("#product-page") as HTMLDivElement;

    divProducts.innerHTML += `
    <div class="produto-card">
        <img src="${produto.imagemUrl}" alt="${produto.nome}" class="produto-image">

            <h2>${produto.nome}</h2>

        <p>R$ ${produto.preco?.toFixed(2)}</p>

        <a href="produto.html?id=${produto.id}">
            Ver Produto
        </a>
    </div>
`

}

function coletarURL(): string | null{
    const params = new URLSearchParams(window.location.search);
    const slug: string | null = params.get("slug");
    console.log(slug);
    return slug?.toString() || null;
}
await exibirProdutosPorCategoria(coletarURL());
