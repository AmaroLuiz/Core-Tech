import {Categoria} from "../types/categoria.js";

export async function coletarCategorias() {

    let categoriaInput = document.querySelector("#categorias") as HTMLInputElement;
    let slugInput = document.querySelector("#slug") as HTMLInputElement;

    return {

        nome: categoriaInput.value,
        slug: slugInput.value

    }

}
