import {buscarTodosOsProduto,
    buscarProdutoPorId,
    buscarProdutoPorCategoria}
    from "./services/produto.service";

async function carregar() {

    const todos = await buscarTodosOsProduto();
    const produto = await buscarProdutoPorId(25);
    const categoria = await buscarProdutoPorCategoria(2);
    console.log(categoria);
}

carregar();