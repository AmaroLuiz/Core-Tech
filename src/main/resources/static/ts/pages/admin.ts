import {Usuario} from "../types/usuario.js";
import {api} from "../services/api";
import {Dashboard} from "../types/dashboard.js";

const dadosUsuario = await api.get("/usuario/me")
const usuario: Usuario = dadosUsuario.data;

const dadosDashboard = await api.get("/produtos/dashboard")
const dashboard:Dashboard = dadosDashboard.data;


export function preencherUsuario(usuario: Usuario) {
    const nome = document.querySelector(".admin-nome") as HTMLInputElement;
    const gmail = document.querySelector(".admin-email") as HTMLInputElement;

    if (usuario.nome != null || usuario.nome != "") {
        nome.textContent = usuario.nome;
    }
    if (usuario.email != null || usuario.email != "") {
        gmail.textContent = usuario.email;
    }

}

preencherUsuario(usuario);

export function preencherCards(dashboard: Dashboard){
    const usuarios = document.querySelector(".usuarios") as HTMLSpanElement;
    const produtos = document.querySelector(".produtos") as HTMLSpanElement;
    const categorias = document.querySelector(".categorias") as HTMLSpanElement;
    const visitas = document.querySelector(".visitas") as HTMLSpanElement;

    if (dashboard.totalContas != null) {
        usuarios.textContent = dashboard.totalContas;
    }
    if (dashboard.totalProdutos != null) {
        produtos.textContent = dashboard.totalProdutos;
    }
    if (dashboard.totalCategorias != null) {
        categorias.textContent = dashboard.totalCategorias;
    }
    if (dashboard.totalVisitas != null) {
        visitas.textContent = dashboard.totalVisitas;
    }
}
preencherCards(dashboard);