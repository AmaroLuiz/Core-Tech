import {Produto} from "../types/produto.js";
import {Categoria} from "../types/categoria.js";
import {
    atualizarProduto, deletarProduto,
    exibirProdutoPorCategoria,
    exibirProdutoPorId,
    exibirProdutoPorNome,
    exibirProdutoPorSku,
    salvarProduto
} from "../services/produto.service.js";

const grid = document.querySelector(".lista-produtos") as HTMLElement;
const cardProduto = document.querySelector(".exibir-produto-unico") as HTMLDivElement;
let produtosCarregados: Produto[] = [];

export async function criarProduto() {

    const nomeInput = document.querySelector("#criacao-nome") as HTMLInputElement;
    const precoInput = document.querySelector("#criacao-preco") as HTMLInputElement;
    const skuInput = document.querySelector("#criacao-sku") as HTMLInputElement;
    const categoriaInput = document.querySelector("#criacao-categoria") as HTMLSelectElement;
    const imagemInput = document.querySelector("#criacao-imagem") as HTMLInputElement;
    const descricaoInput = document.querySelector("#criacao-descricao") as HTMLTextAreaElement;

    const categoriaa: Categoria = {
        nome: categoriaInput.textContent?.toString() || "",
        slug: categoriaInput.value.toString()
    }

    if (nomeInput.value.trim() && precoInput.value.trim()
        && skuInput.value.trim() && categoriaInput.value.trim()
        && imagemInput.value.trim() && descricaoInput.value.trim()) {
        const produto: Produto = {
            nome: nomeInput.value,
            descricao: descricaoInput.value,
            sku: skuInput.value,
            preco: Number(precoInput.value),
            imagemUrl: imagemInput.value,
            categoria:  {
                slug: categoriaInput.value,
                nome: categoriaInput.textContent?.toString()
            }
        };
        await salvarProduto(produto);
    }
}



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

function pegarDadosAtualizarProduto(): Produto{
    const nomeInput = document.querySelector("#editar-nome") as HTMLInputElement;
    const precoInput = document.querySelector("#editar-preco") as HTMLInputElement;
    const categoriaInput = document.querySelector("#editar-categoria") as HTMLSelectElement;
    const imagemInput = document.querySelector("#editar-imagem") as HTMLInputElement;
    const descricaoInput = document.querySelector("#editar-descricao") as HTMLTextAreaElement;

    return {
        nome: nomeInput.value || null,
        preco: Number(precoInput.value.trim()) || null,
        descricao: descricaoInput.value.trim() || null,
        nomeCategoria: categoriaInput.value.trim() || null,
        imagemUrl: imagemInput.value.trim() || null
    }
}

async function criarAtualizacaoProduto(produto: Produto,id: number){

    

    if(produto.nome != null || produto.preco != null || produto.nomeCategoria != null
        || produto.descricao != null){
        await atualizarProduto(produto, id)
    }

}

function criarCardDeProduto(produto: Produto): HTMLElement {

    const article = document.createElement("article");
    article.className = "produto-card";

    const menu = document.createElement("button");
    menu.className = "menu-produto";
    menu.textContent = "⋮";
    menu.dataset.id = produto.id?.toString();

    const img = document.createElement("img");
    img.className = "produto-imagem";
    img.src = produto.imagemUrl || "";


    const span = document.createElement("span");
    span.className = "produto-categoria";
    span.textContent = produto.nomeCategoria ?? "" ;
    

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
                <p><strong>Descrição</strong> ${produto.descricao}</>

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
                    placeholder="Novo nome"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >


                <input
                    id="editar-preco"
                    value="${produto.preco}"
                    placeholder="Novo preço"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >


                <input
                    id="editar-categoria"
                    value="${produto.nomeCategoria}"
                    placeholder="Nova categoria"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >

                <input
                    id="editar-descricao"
                    value="${produto.descricao}"
                    placeholder="Nova descrição"
                    style="width:100%;padding:10px;margin-bottom:10px"
                >

                <input
                    id="editar-imagem"
                    value="${produto.imagemUrl}"
                    placeholder="Nova imagem"
                    style="width:100%;padding:10px;margin-bottom:20px"
                >

                <button
                    class="btn-atualizar-produto"
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
                    class="btn-apagar-produto"
                    data-id="${produto.id}"
                    style="
                        width:100%;
                        padding:12px;
                        margin: 10px 0;
                        background:#ff5c00;
                        border:none;
                        border-radius:10px;
                        font-weight:bold;
                        cursor:pointer;
                    "
                >
                    Apagar
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

    if (target.classList.contains("criacao-enviar")) {
        try{
            const container = document.querySelector(".criacao-card") as HTMLElement;
            const carregando = document.createElement("div");
            carregando.className = "carregando";
            const carregandoTexto = document.createTextNode("Carregando...");
            carregando.style.width = "100%";
            carregando.style.height = "100%";
            carregando.style.position = "fixed";
            carregando.style.top = "0";
            carregando.style.left = "0";
            carregando.style.display = "flex";
            carregando.style.justifyContent = "center";
            carregando.style.alignItems = "center";
            carregando.style.background = "rgba(0,0,0,.8)";
            carregando.style.zIndex = "9999";
            carregando.appendChild(carregandoTexto);
            container.appendChild(carregando);
            await criarProduto();
        } catch (error: any) {
            window.alert("Erro ao salvar produto");
        }
        finally {
            await new Promise(resolve =>
                setTimeout(resolve, 1000)
            );
            const fechar = document.querySelector(".carregando") as HTMLElement;
            fechar.remove();
        }
    }

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

    if (target.classList.contains("btn-atualizar-produto")) {
        try{
            const produtoId = Number(target.dataset.id);

            const produto: Produto = pegarDadosAtualizarProduto();
            console.log(produto)

            await criarAtualizacaoProduto(produto, produtoId);
            window.alert("Produto atualizado com sucesso")
        } catch(error: any){
            if(error.status == 403){
                window.alert("Usuario não tem permição")
            }
            if (error.status == 404) {
                window.alert("Produto não encontrado")
            }
            else{
                window.alert("Ocorreu um erro inesperado")
            }
        }

    }
    if (target.classList.contains("btn-apagar-produto")) {

        try{
            const produtoId = Number(target.dataset.id);
            await deletarProduto(produtoId)
            window.alert("Produto apagado com sucesso")
            
        } catch (error: any) {
            if(error.status == 403){
                window.alert("Usuario não tem permição")
            }
            if (error.status == 404) {
                window.alert("Produto não encontrado")
            }
            else{
                window.alert("Ocorreu um erro inesperado")
            }
        }
    }

    if (target.classList.contains("btn-fechar-modal")) {

        const overlay =
            document.querySelector(".produto-overlay");

        overlay?.remove();

        document.body.style.overflow = "";
    }
});
