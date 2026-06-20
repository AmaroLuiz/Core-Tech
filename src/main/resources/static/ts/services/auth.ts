import {api} from "./api.js";

const login = document.querySelector("#login") as HTMLFormElement;

export async function isAuthenticated() {
    const token = localStorage.getItem("token");

    if (!token) return;

    try {
        await api.get("/usuario/me");

        if (token) {
            login.textContent = "página de usuario"
            login.href = "../../templates/profile.html"
        }
    } catch (e) {
        console.log("Erro na authenticação", e);
        localStorage.removeItem("token");
        if (login) {
            login.textContent = "login"
            login.href = "../../login.html"
        }
    }
}

isAuthenticated();