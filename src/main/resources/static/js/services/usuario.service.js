import { api } from './api.js';
// import {coletarUsuario} from "../pages/usuario.js";
export async function login(usuario) {
    const response = await api.post('/usuario/login', usuario);
    return response.data;
}
;
export async function salvaUsuario(usuario) {
    const response = await api.post('/usuario/criar', usuario);
    return response.data;
}
