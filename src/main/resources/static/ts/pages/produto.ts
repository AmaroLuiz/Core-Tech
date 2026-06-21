import {Produto} from "../types/produto.js";


export async function coletarProduto() {



    const nomeInput = document.querySelector("#nome") as HTMLInputElement;
    let skuInput = document.querySelector("#sku") as HTMLInputElement;
    let descricaoInput = document.querySelector("#descricao") as HTMLTextAreaElement;
    let precoInput =  document.querySelector("#preco") as HTMLInputElement;
    let imagemUrlInput = document.querySelector("#imagemUrl") as HTMLInputElement;
    let ativoInput = document.querySelector("#ativo") as HTMLInputElement;
    let categoriaIdInput = document.querySelector("#categoriaId") as HTMLSelectElement;

    return {

        nome: nomeInput.value,
        sku: skuInput.value,
        descricao: descricaoInput.value,
        preco: Number(precoInput.value),
        imagemUrl: imagemUrlInput.value,
        ativo: ativoInput.checked,
        categoria: categoriaIdInput.value

    }

}


