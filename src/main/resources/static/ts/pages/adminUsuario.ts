import {Usuario} from "../types/usuario.js";
import {buscarUsuarioPorGmail} from "../services/usuario.service.js";
import {api} from "../services/api.js";


export async function buscarUsuario(gmail:string){
    try{

        const usuario: Usuario = await buscarUsuarioPorGmail(gmail);
        preencherDadosUsuario(usuario);

    } catch (error: any) {
        console.log(error);
        if (error.response?.status === 403) {
            alert("Você não tem permissão para acessar essa área");
            window.location.href = "../../../templates/index.html";
        }
        if (error.response?.status === 404) {
            window.alert("Verifique se o gmail esta correto")
        }
        else{
            window.alert("Erro no servidor")
        }
    }
}

export function preencherDadosUsuario(usuario: Usuario){

    const nome = document.querySelector("#usuario-nome") as HTMLParagraphElement;
    const email = document.querySelector("#usuario-email") as HTMLParagraphElement;
    const perfil =  document.querySelector("#usuario-role") as HTMLParagraphElement;
    const id = document.querySelector("#usuario-id") as HTMLParagraphElement;

    const estado = document.querySelector("#usuario-estado") as HTMLParagraphElement;
    const cidade = document.querySelector("#usuario-cidade") as HTMLParagraphElement;
    const cep = document.querySelector("#usuario-cep") as HTMLParagraphElement;
    const bairro = document.querySelector("#usuario-bairro") as HTMLParagraphElement;
    const rua = document.querySelector("#usuario-rua") as HTMLParagraphElement;
    const numero = document.querySelector("#usuario-numero") as HTMLParagraphElement;

    const ddd = document.querySelector("#usuario-ddd") as HTMLParagraphElement;
    const telefone = document.querySelector("#usuario-telefone") as HTMLParagraphElement;

    if (usuario.nome?.trim()) {
        nome.textContent = usuario.nome;
    } else {
        nome.textContent = "Nome não informado";
    }

    if (usuario.email?.trim()) {
        email.textContent = usuario.email;
    } else {
        email.textContent = "Email não informado";
    }

    if (usuario.role?.trim()) {
        perfil.textContent = usuario.role;
    } else {
        perfil.textContent = "Perfil não definido";
    }

    if (usuario.id != null) {
        id.textContent = usuario.id.toString();
    } else {
        id.textContent = "ID inexistente";
    }
    if (!usuario.enderecos?.length) {
        estado.textContent = "Estado não definido";
        cidade.textContent = "Cidade não definida";
        cep.textContent = "CEP não definido";
        bairro.textContent = "Bairro não definido";
        rua.textContent = "Rua não definida";
        numero.textContent = "Número não definido";
    }

    for (const endereco of usuario.enderecos){
        if (endereco.estado?.trim()){
            estado.textContent = endereco.estado;
        } else {
            estado.textContent = "Estado não definido";
        }
        if (endereco.cidade?.trim()){
            cidade.textContent = endereco.cidade;
        } else {
            cidade.textContent = "Cidade não definida";
        }
        if (endereco.cep?.trim()){
            cep.textContent = endereco.cep;
        } else{
            cep.textContent = "Cep não definido";
        }
        if (endereco.complemento?.trim()){
            bairro.textContent = endereco.complemento;
        } else {
            bairro.textContent = "Bairro não definido";
        }
        if (endereco.rua?.trim()){
            rua.textContent = endereco.rua;
        } else {
            rua.textContent = "Rua não definida";
        }
        if (endereco.numero != null){
            numero.textContent = endereco.numero.toString();
        } else {
            numero.textContent = "Numero não definido";
        }

    }
    if (!usuario.telefones?.length) {
        ddd.textContent = "DDD não definido";
        telefone.textContent = "Telefone não definido";
    }
    for (const telefoneObject of usuario.telefones){
        if (telefoneObject.ddd?.trim() != null){
            ddd.textContent = telefoneObject.ddd;
        } else {
            ddd.textContent = "DDD não definido";
        }
        if (telefoneObject.telefone?.trim() != null){
            telefone.textContent = telefoneObject.telefone;
        } else {
            telefone.textContent = "Telefone não definido";
        }
    }
}



document.addEventListener("click", async (e) => {
    const target = e.target as HTMLElement;

    if (target.classList.contains('btn-pesquisar')) {
        const login = await api.get("/usuario/me")
        const emailPesquisa = document.querySelector("#email-pesquisa") as HTMLInputElement;
        await buscarUsuario(emailPesquisa.value);
    }

})
