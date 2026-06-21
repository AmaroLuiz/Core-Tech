import {api} from "./api.js";

const login = document.querySelector("#login") as HTMLFormElement;

export async function isAuthenticated() {
    const token = localStorage.getItem("token");
    const login = document.querySelector("#login") as HTMLAnchorElement;

    login.textContent = "Login";
    login.href = "../../../templates/login.html";

    if (!token) {
        redirectToLogin();
        return;
    }

    try {
        const response = await api.get("/usuario/me");
        const user = response.data;
        const rolesUser: string = user.role.toString();
        localStorage.setItem("user", rolesUser);


        login.textContent = "Perfil";
        login.href = "../../../templates/profile.html";
        if (localStorage.getItem("user") != null
            && localStorage.getItem("user") === "ADMIN") {
            enableAdminAccess();
        } else{
            enableUserAccess();
        }
    } catch (e) {
        console.log("Erro na autenticação", e);
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        redirectToLogin();
    }
}

function enableAdminAccess() {
    const adminLinks = document.querySelectorAll(".admin-only");
    adminLinks.forEach(el => {
        (el as HTMLElement).style.display = "block";
    });
}

function enableUserAccess() {
    const adminPages = document.querySelectorAll(".admin-only");

    adminPages.forEach(el => {
        (el as HTMLElement).style.display = "none";
    });

}

function redirectToLogin() {
    if (login) {
        login.textContent = "login";
        login.href = "../../../templates/login.html";
    }
}

isAuthenticated();