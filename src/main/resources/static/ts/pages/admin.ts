import {Usuario} from "../types/usuario.js";
import {api} from "../services/api.js";
import {exibirDashboard} from "../services/produto.service.js";

const dadosUsuario = await api.get("/usuario/me")
const usuario: Usuario = dadosUsuario.data;


export function preencherUsuario(usuario: Usuario) {
    const nome = document.querySelector("#admin-nome") as HTMLElement;
    const gmail = document.querySelector("#admin-email") as HTMLElement;

    if (usuario.nome != null || usuario.nome != "") {
        nome.textContent = usuario.nome;
    }
    if (usuario.email != null || usuario.email != "") {
        gmail.textContent = usuario.email;
    }

}

preencherUsuario(usuario);

export async function preencherCards(){
    const usuarios = document.querySelector("#usuarios") as HTMLSpanElement;
    const produtos = document.querySelector("#produtos") as HTMLSpanElement;
    const categorias = document.querySelector("#categorias") as HTMLSpanElement;
    const visitas = document.querySelector("#visitas") as HTMLSpanElement;

    const dashboard = await exibirDashboard();

    console.log(dashboard.totalCategorias);

    if (dashboard.totalContas != null) {
        usuarios.textContent = dashboard.totalContas.toString();
    }
    if (dashboard.totalProdutos != null) {
        produtos.textContent = dashboard.totalProdutos.toString();
    }
    if (dashboard.totalCategoria != null) {
        categorias.textContent = dashboard.totalCategoria.toString();
    }
    if (dashboard.totalVisitas != null) {
        visitas.textContent = dashboard.totalVisitas.toString();
    }
}

preencherCards();
