import { Categoria } from "../types/categoria.js";
import {exibirTodasCategorias} from "../services/catalogo.service.js";
import {Produto} from "../types/produto.js";
import {buscarTodosOsProduto} from "../services/produto.service.js";

async function criarCardCategoria(categoria: Categoria){
    const categorias = document.querySelector(".categorias") as HTMLDivElement;
    const div = document.createElement("div");
    div.className = `${categoria.slug}`;

    const a = document.createElement("a");
    a.className = "animation";
    a.href = `../../../templates/produtos.html?slug=${categoria.slug}`;

    const img = document.createElement("img");
    img.className = "categoria-image";
    img.src = categoria.imagemURL || "../static/imagens/home.webp";


    const h3 = document.createElement("h3");
    h3.textContent = categoria.nome;


    categorias.appendChild(div);
    div.appendChild(a);
    a.appendChild(img);
    a.appendChild(h3);
}

async function categoria(){
    const categorias: Categoria[] = await exibirTodasCategorias();
    categorias.forEach(categoria => criarCardCategoria(categoria));
}
await categoria();


function criarCardProduto(produto: Produto){
    const container = document.querySelector(".outros-produtos") as HTMLDivElement;

    const div = document.createElement("div");
    div.className = "product-card";

    const img = document.createElement("img");
    img.src = "../static/imagens/gato.webp";

    const h3 = document.createElement("h3");
    h3.textContent = produto.nome;

    const p = document.createElement("p");
    p.textContent = `R$ ${produto.preco}`;

    const a = document.createElement("a");
    a.href = `produto.html?id=${produto.id}`;
    a.textContent = "Ver mais";

    container.appendChild(div);
    div.appendChild(img);
    div.appendChild(h3);
    div.appendChild(p);
    div.appendChild(a);
}
async function produto(){
    const produtos: Produto[] = await buscarTodosOsProduto();
    const produtosAleatorios = selecionarProdutosAleatorios(produtos, 10);
    produtosAleatorios.forEach(produto => criarCardProduto(produto));
}

await produto();

function selecionarProdutosAleatorios(produtos: Produto[], quantidade: number = 10) {

    const copia: Produto[] = [...produtos];

    for (let i:number = copia.length - 1; i > 0; i--) {

        const j: number = Math.floor(Math.random() * (i + 1));

        [copia[i], copia[j]] = [copia[j]!, copia[i]!];
    }

    return copia.slice(0, quantidade);
}