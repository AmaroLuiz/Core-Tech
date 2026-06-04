const categorias = [
    {
        id: 1,
        nome: "chaveiro"
    },

    {
        id: 2,
        nome: "articulado"
    },

    {
        id: 3,
        nome: "agro"
    },

    {
        id: 4,
        nome: "minimalista"
    },

    {
        id: 5,
        nome: "funkopop"
    },
    {
        id: 6,
        nome: "suporte"
    },
    {
        id: 7,
        nome: "todos"
    }
];
const produtos = [

    {
        id: 1,
        categoriaId: 1,
        nome: "Chaveiro Cachorro",
        preco: 19.99,
        descricao: "Chaveiro impresso em 3D com formato de cachorro.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 2,
        categoriaId: 1,
        nome: "Chaveiro Gato",
        preco: 19.99,
        descricao: "Chaveiro impresso em 3D com formato de gato.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 3,
        categoriaId: 1,
        nome: "Chaveiro Capivara",
        preco: 24.99,
        descricao: "Chaveiro personalizado com formato de capivara.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 4,
        categoriaId: 2,
        nome: "Dragão Articulado",
        preco: 49.99,
        descricao: "Dragão flexível com múltiplas articulações.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 5,
        categoriaId: 2,
        nome: "Tubarão Articulado",
        preco: 39.99,
        descricao: "Tubarão articulado impresso em 3D.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 6,
        categoriaId: 2,
        nome: "Axolote Articulado",
        preco: 34.99,
        descricao: "Axolote articulado colorido e flexível.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 7,
        categoriaId: 3,
        nome: "Vaso Geométrico",
        preco: 29.99,
        descricao: "Vaso decorativo com design geométrico minimalista.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 8,
        categoriaId: 3,
        nome: "Suporte para Celular",
        preco: 24.99,
        descricao: "Suporte minimalista para smartphones.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 9,
        categoriaId: 3,
        nome: "Porta-Lápis Moderno",
        preco: 34.99,
        descricao: "Organizador de mesa moderno e funcional.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 10,
        categoriaId: 4,
        nome: "Funko Robô",
        preco: 49.99,
        descricao: "Miniatura inspirada em robótica no estilo Funko.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 11,
        categoriaId: 4,
        nome: "Funko Astronauta",
        preco: 54.99,
        descricao: "Astronauta colecionável produzido em impressora 3D.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 12,
        categoriaId: 4,
        nome: "Funko Programador",
        preco: 59.99,
        descricao: "Boneco inspirado no universo da programação.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 13,
        categoriaId: 5,
        nome: "Trator Agrícola",
        preco: 79.99,
        descricao: "Miniatura detalhada de trator agrícola.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 14,
        categoriaId: 5,
        nome: "Colheitadeira",
        preco: 89.99,
        descricao: "Modelo colecionável de colheitadeira agrícola.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 15,
        categoriaId: 5,
        nome: "Pulverizador Agrícola",
        preco: 74.99,
        descricao: "Miniatura agrícola produzida em impressora 3D.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 16,
        categoriaId: 6,
        nome: "Suporte para ESP32",
        preco: 14.99,
        descricao: "Base impressa em 3D para fixação e organização de placas ESP32.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 17,
        categoriaId: 6,
        nome: "Caixa para Arduino Uno",
        preco: 24.99,
        descricao: "Case protetora para Arduino Uno com acesso às portas e conexões.",
        imagem: "../static/imagens/gato.webp"
    },

    {
        id: 18,
        categoriaId: 6,
        nome: "Suporte para Sensor",
        preco: 12.99,
        descricao: "Suporte para montagem de sensores ultrassônicos em projetos de robótica.",
        imagem: "../static/imagens/gato.webp"
    }

];


export { produtos, categorias };
