import {Produto} from "../types/produto.js";
import {Categoria} from "../types/categoria.js";
import {
    exibirProdutoPorCategoria,
    exibirProdutoPorId,
    exibirProdutoPorNome,
    exibirProdutoPorSku
} from "../services/produto.service.js";

const grid = document.querySelector(".lista-produtos") as HTMLElement;
const cardProduto = document.querySelector(".exibir-produto-unico") as HTMLDivElement;
let produtosCarregados: Produto[] = [];

export async function buscarProduto() {
    const tipo = document.querySelector("#tipo-pesquisa") as HTMLSelectElement;
    const valorPesquisa = document.querySelector("#valor-pesquisa") as HTMLInputElement;

    try {
        if (tipo.value === "id" && Number(valorPesquisa.value)) {
            const produto: Produto =
                await exibirProdutoPorId(Number(valorPesquisa.value))
            exibirProduto(produto);
        }
        if (tipo.value === "sku") {
            const produto: Produto =
                await exibirProdutoPorSku(valorPesquisa.value);
            exibirProduto(produto);
        }
        if (tipo.value === "nome") {
            const produto: Produto =
                await exibirProdutoPorNome(valorPesquisa.value);
            exibirProduto(produto);
        }

    } catch (error: any) {
        if (error.response?.status === 404) {
            window.alert("Produto não encontrado")
        } else {
            window.alert("Erro no servidor")
        }
    }
}
function criarCardDeProduto(produto: Produto): HTMLElement {
    console.log(produto);
    console.log(produto.id);

    const article = document.createElement("article");
    article.className = "produto-card";

    const menu = document.createElement("button");
    menu.className = "menu-produto";
    menu.textContent = "⋮";
    menu.dataset.id = produto.id?.toString();

    const img = document.createElement("img");
    img.className = "produto-imagem";
    img.src = produto.imagemUrl;

    const span = document.createElement("span");
    span.className = "produto-categoria";
    span.textContent = produto.nomeCategoria;

    const h3 = document.createElement("h3");
    h3.className = "produto-nome";
    h3.textContent = produto.nome;
    const p = document.createElement("p");
    p.className = "produto-preco";
    p.textContent = `R$ ${produto.preco}`;

    cardProduto.appendChild(article);
    article.appendChild(menu);
    article.appendChild(img);
    article.appendChild(span);
    article.appendChild(h3);
    article.appendChild(p);
    return article;
}
export function exibirProduto(produto: Produto) {

    produtosCarregados = [produto];

    grid.innerHTML = "";
    cardProduto.innerHTML = "";

    cardProduto.appendChild(criarCardDeProduto(produto));

}
export function exibirProdutos(produtos: Produto[]) {

    produtosCarregados = produtos;

    grid.innerHTML = "";
    cardProduto.innerHTML = "";

    for (const produto of produtos) {
        grid.appendChild(criarCardDeProduto(produto));
    }
}

function criarOverlayProduto(produto: Produto) {

    const overlay = document.createElement("div");

    overlay.style.position = "fixed";
    overlay.style.top = "0";
    overlay.style.left = "0";
    overlay.style.width = "100vw";
    overlay.style.height = "100vh";
    overlay.style.background = "rgba(0,0,0,.8)";
    overlay.style.display = "flex";
    overlay.style.justifyContent = "center";
    overlay.style.alignItems = "center";
    overlay.style.zIndex = "9999";

    const modal = document.createElement("div");

    modal.style.background = "#161b22";
    modal.style.padding = "30px";
    modal.style.borderRadius = "20px";
    modal.style.width = "900px";
    modal.style.maxWidth = "95%";

    modal.innerHTML = `
        <h2 style="margin-bottom:20px">
            Editar Produto
        </h2>

        <div style="
            display:grid;
            grid-template-columns:1fr 1fr;
            gap:30px;
        ">

            <div>
                <h3>Dados atuais</h3>

                <p><strong>ID:</strong> ${produto.id}</p>
                <p><strong>Nome:</strong> ${produto.nome}</p>
                <p><strong>Preço:</strong> R$ ${produto.preco}</p>
                <p><strong>Categoria:</strong> ${produto.nomeCategoria}</p>

                <img
                    src="${produto.imagemUrl}"
                    style="
                        width:100%;
                        max-height:250px;
                        object-fit:cover;
                        border-radius:12px;
                        margin-top:15px;
                    "
                >
            </div>

            <div>
                <h3>Novos dados</h3>

                <input
                    id="editar-nome"
                    value="${produto.nome}"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >

                <input
                    id="editar-preco"
                    value="${produto.preco}"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >


                <input
                    id="editar-categoria"
                    value="${produto.nomeCategoria}"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >

                <input
                    id="editar-imagem"
                    value="${produto.imagemUrl}"
                    style="width:100%;padding:10px;margin-bottom:20px"
                >

                <button
                    class="btn-salvar-produto"
                    data-id="${produto.id}"
                    style="
                        width:100%;
                        padding:12px;
                        background:#ff5c00;
                        border:none;
                        border-radius:10px;
                        font-weight:bold;
                        cursor:pointer;
                    "
                >
                    Salvar
                </button>

                <button
                    class="btn-fechar-modal"
                    style="
                        width:100%;
                        margin-top:10px;
                        padding:12px;
                        border:none;
                        border-radius:10px;
                        cursor:pointer;
                    "
                >
                    Cancelar
                </button>

            </div>

        </div>
    `;

    overlay.className = "produto-overlay";

    overlay.appendChild(modal);

    document.body.appendChild(overlay);
}

document.addEventListener("click", async (e) => {
    const target = e.target as HTMLElement;

    if (target.classList.contains("btn-pesquisar")) {
        await buscarProduto();
    }
    if (target.classList.contains("btn-categoria")) {
        try {
            const categoria = document.querySelector("#categoria-select") as HTMLSelectElement;

            if (categoria.value) {
                const produtos: Produto[] =
                    await exibirProdutoPorCategoria(categoria.value);
                exibirProdutos(produtos);
            }
        } catch (error: any) {
            if (error.response?.status === 404) {
                window.alert("Produto não encontrado")
            } else {
                window.alert("Erro no servidor")
            }
        }
    }
    if (target.classList.contains("menu-produto")) {

        const produtoId = Number(target.dataset.id);

        const produto = produtosCarregados.find(
            p => p.id === produtoId
        );

        if (produto) {
            criarOverlayProduto(produto);
        }

    }
    if (target.classList.contains("btn-fechar-modal")) {

        const overlay =
            document.querySelector(".produto-overlay");

        overlay?.remove();

        document.body.style.overflow = "";
    }
});
