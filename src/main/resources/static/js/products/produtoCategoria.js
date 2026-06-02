import { produtos, categorias } from "./allproducts.js";


const divProducts = document.getElementById("product-page");

try {
    const params = new URLSearchParams(window.location.search);

    const categoriaUrl = params.get("categoria");


    if (categoriaUrl == "todos") {

        let html = "";
        produtos.forEach(produto => {

            html += `
            <div class="product-card">

                <img src="${produto.imagem}" alt="${produto.nome}">

                <h3>${produto.nome}</h3>

                <p>R$ ${produto.preco.toFixed(2)}</p>

                <a href="produto.html?id=${produto.id}">
                    Ver Produto
                </a>

            </div>
        `;

            divProducts.innerHTML = html;

        });
    } else {

        const categoria = categorias.find(c => c.nome === categoriaUrl);


        if (!categoria) {
            divProducts.style.textAlign = "center";
            divProducts.style.display = "flex";
            divProducts.style.flexDirection = "column";
            divProducts.style.alignItems = "center";
            divProducts.style.color = "#ff8b3d";
            divProducts.innerHTML = "<h2>Categoria não encontrada.</h2>";
            divProducts.appendChild(document.createElement("a")).textContent = "Voltar para o catálogo";
            divProducts.querySelector("a").href = "catalogo.html";
            divProducts.querySelector("a").style.display = "inline-block";
            divProducts.querySelector("a").style.marginTop = "20px";
            divProducts.querySelector("a").style.padding = "10px 20px";
            divProducts.querySelector("a").style.backgroundColor = "#ff6b00";
            divProducts.querySelector("a").style.width = "200px";
            divProducts.querySelector("a").style.color = "#fff";
            divProducts.querySelector("a").style.borderRadius = "5px";
        }
        else {
            const produtosCategoria = produtos.filter(p => p.categoriaId === categoria.id);

            buscarProdutosRelacionados(produtosCategoria);
        }
    }
    function buscarProdutosRelacionados(produtosCategoria) {

        if (produtosCategoria.length > 0) {

            let html = "";
            produtosCategoria.forEach(produto => {

                html += `
                <div class="produto-card">
                    <img src="${produto.imagem}" alt="${produto.nome}" class="produto-image">

                    <h2>${produto.nome}</h2>

                    <p>
                        R$ ${produto.preco.toFixed(2)}
                    </p>

                    <a href="produto.html?id=${produto.id}">
                        Ver Produto
                    </a>
                </div>
            `;
            });

            divProducts.innerHTML = html;
        }
        else {

            divProducts.innerHTML =
                "<p>Nenhum produto encontrado para esta categoria.</p>";

        }
    }

} catch (error) {
    console.error("Erro ao buscar produtos relacionados:", error);
    divProducts.innerHTML = "<p>Ocorreu um erro ao carregar os produtos. Por favor, tente novamente mais tarde.</p>";
    divProducts.style.textAlign = "center";
    divProducts.style.display = "flex";
    divProducts.style.flexDirection = "column";
    divProducts.style.alignItems = "center";
    divProducts.style.color = "#ff8b3d";
    divProducts.innerHTML = "<h2>Categoria não encontrada.</h2>";
    divProducts.appendChild(document.createElement("a")).textContent = "Voltar para o catálogo";
    divProducts.querySelector("a").href = "catalogo.html";
    divProducts.querySelector("a").style.display = "inline-block";
    divProducts.querySelector("a").style.marginTop = "20px";
    divProducts.querySelector("a").style.padding = "10px 20px";
    divProducts.querySelector("a").style.backgroundColor = "#ff6b00";
    divProducts.querySelector("a").style.width = "200px";
    divProducts.querySelector("a").style.color = "#fff";
    divProducts.querySelector("a").style.borderRadius = "5px";
}





