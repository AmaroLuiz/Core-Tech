export async function coletarProduto() {
    const nomeInput = document.querySelector("#nome");
    let skuInput = document.querySelector("#sku");
    let descricaoInput = document.querySelector("#descricao");
    let precoInput = document.querySelector("#preco");
    let imagemUrlInput = document.querySelector("#imagemUrl");
    let ativoInput = document.querySelector("#ativo");
    let categoriaIdInput = document.querySelector("#categoriaId");
    return {
        nome: nomeInput.value,
        sku: skuInput.value,
        descricao: descricaoInput.value,
        preco: Number(precoInput.value),
        imagemUrl: imagemUrlInput.value,
        ativo: ativoInput.checked,
        categoria: categoriaIdInput.value
    };
}
