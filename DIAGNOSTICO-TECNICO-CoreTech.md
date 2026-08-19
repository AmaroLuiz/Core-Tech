# Diagnóstico Técnico — Core Tech (coretech-api)

> Análise somente de leitura. Nenhum arquivo do projeto foi modificado.
> Data: 2026-08-18. Baseada no código presente no repositório.
> Convenção: **[FATO]** = observado diretamente no código (com citação de arquivo). **[INFERÊNCIA]** = dedução com base em evidências. **[INDETERMINADO]** = não foi possível determinar com segurança a partir do código analisado.

---

## 1. Resumo executivo

Core Tech é uma **API REST de e-commerce** (loja de produtos de tecnologia) construída em **Java 17 + Spring Boot**, com um **frontend estático** (HTML + TypeScript compilado para JavaScript + Axios) servido pela própria aplicação a partir de `src/main/resources/static` e `templates`. **[FATO/INFERÊNCIA]**

O domínio cobre: cadastro/login de usuários com JWT, gestão de produtos e categorias (área administrativa), e criação de pedidos com itens e cálculo de valores. **[FATO]**

Estado atual: **protótipo funcional em estágio inicial**, monolito único. O código demonstra conhecimento das ferramentas (Spring Security, JPA, MapStruct, tratamento global de exceções), mas apresenta **problemas objetivos de segurança e corretude**, ausência quase total de testes, ausência de migrations, ausência de containerização, e **inconsistências entre frontend e backend** (endpoints que não batem). Não é um projeto pronto para produção. **[INFERÊNCIA]**

---

## 2. Stack tecnológica

Identificada em `build.gradle`, `package.json`, `tsconfig.json`, `application.properties`:

**Backend** **[FATO]**
- Java 17 (toolchain declarada em `build.gradle`).
- Spring Boot **4.0.6** (observação: versão declarada; ver §13 — pode ser uma versão pré-release/incomum).
- Spring Data JPA, Spring Security, Spring Web MVC, Spring Validation.
- Lombok.
- MapStruct 1.5.3.Final (+ binding lombok-mapstruct 0.2.0).
- JJWT (io.jsonwebtoken) 0.13.0 (api/impl/jackson).
- PostgreSQL driver (runtime).
- Build: Gradle (wrapper 9.5.1).

**Frontend** **[FATO]**
- TypeScript ^6.0.3 (dev), compilado de `static/ts` → `static/js` (`tsconfig.json`).
- Axios ^1.17.0 — porém em runtime o Axios é carregado via CDN (`cdn.jsdelivr.net`) nos templates, não via bundler.
- Tailwind via CDN (`cdn.tailwindcss.com`) em algumas telas (`index.html`, `login.html`, `profile.html`, `register.html`).
- Sem framework SPA (sem React/Vue/Angular). Páginas HTML servidas como estáticos com scripts `type="module"`.

**Banco** **[FATO]**
- PostgreSQL (`spring.datasource.url=jdbc:postgresql://localhost:5432/db_usuario`, dialeto PostgreSQL).
- Schema gerado por Hibernate (`spring.jpa.hibernate.ddl-auto=update`) — **não há migrations**.

**CI** **[FATO]**
- GitHub Actions (`.github/workflows/gradle.yml`): build + test em PRs para `main`.

---

## 3. Arquitetura atual

Monolito Spring Boot em camadas, servindo API REST **e** os arquivos estáticos do frontend no mesmo artefato. **[FATO]**

Camadas observadas (pacote `br.com.coretech.coretech_api`) **[FATO]**:
- **controller/** — `PedidoController`, `ProdutoController`, `UsuarioControlller` (nome com erro de digitação). Recebem HTTP, delegam a services, retornam `ResponseEntity<DTO>`.
- **service/** — `AuthService`, `UsuarioService`, `PedidoService`, `ProdutoService`. Regras de negócio e orquestração; `@Transactional` na maioria dos métodos de escrita.
- **service/mapper/** — MapStruct: `UsuarioConverter`, `ProdutoConverter`, `PedidoConverter`.
- **service/dto/** — DTOs de request/response e de erro.
- **infraestructure/repository/** — interfaces Spring Data (`JpaRepository`/`CrudRepository`).
- **infraestructure/entity/** — entidades JPA.
- **infraestructure/security/** — `SecurityConfig`, `JwtUtil`, `JwtRequestFilter`, `UserDetailsServiceImpl`.
- **infraestructure/exceptions/** — exceções custom + `GlobalExceptionHandler`.
- **infraestructure/Enums/** — `Role`, `FormaPagamento`, `PagamentoStatus`.

Fluxo real de uma operação autenticada **[FATO]**:
`HTTP Request → JwtRequestFilter (valida token, popula SecurityContext) → Controller → Service (obtém e-mail via AuthService.getUsuarioAutenticadoEmail() a partir do SecurityContext) → Repository → PostgreSQL → Converter (entity→DTO) → ResponseEntity`.

Observação de camadas **[FATO]**: `PedidoController` injeta `PedidoRepository` diretamente além do service (acoplamento indevido controller→repository, embora o repositório não seja usado nos métodos — dependência morta).

Inconsistência arquitetural relevante **[FATO]**: o pacote é nomeado `infraestructure` (grafia incorreta) e mistura conceitos — `security`, `exceptions` e `entity` estão em "infraestrutura", enquanto `repository` (que seria infraestrutura de persistência) também está lá. Não é um problema funcional, mas gera confusão.

---

## 4. Estrutura do projeto

```
coretech-api/
├── build.gradle, settings.gradle, gradlew…      # build backend (Gradle + Spring Boot)
├── package.json, tsconfig.json, node_modules/   # toolchain do frontend (TS → JS)
├── .github/workflows/gradle.yml                 # CI (build+test em PR)
├── HELP.md                                       # boilerplate gerado pelo Spring Initializr
├── src/main/java/br/com/coretech/coretech_api/
│   ├── CoretechApiApplication.java              # entrypoint
│   ├── controller/                              # 3 controllers REST
│   ├── service/ (+ dto/, mapper/)               # regras de negócio, DTOs, MapStruct
│   └── infraestructure/
│       ├── entity/  repository/  security/
│       ├── exceptions/  Enums/
├── src/main/resources/
│   ├── application.properties                   # config (⚠ credenciais em texto)
│   ├── templates/*.html                         # 11 páginas HTML
│   └── static/{css,js,ts,imagens}/              # frontend (fonte TS + JS compilado)
└── src/test/java/…/CoretechApiApplicationTests.java  # 1 teste (contextLoads)
```

Observações **[FATO]**:
- `static/js` e `static/ts` coexistem: `ts/` é a fonte e `js/` é a saída compilada. Há **arquivos JS versionados** apesar de `.gitignore` tentar ignorar parte deles — o ignore é parcial/inconsistente (`static/js/pages`, `static/js/main.js`, `static/js/service` ignorados, mas `static/js/services`, `static/js/components`, `static/js/validations`, `static/js/types` versionados).
- Existem **dois diretórios de service quase idênticos**: `static/js/service/api.js` e `static/js/services/api.js` — duplicação.

---

## 5. Fluxos principais

**Autenticação / cadastro** **[FATO]**
1. `POST /usuario/signup` (público) → `UsuarioService.salvaUsuario(LoginDTO)` → verifica e-mail duplicado, codifica senha com BCrypt, salva `Usuario` (role default `USER` via `@PrePersist`).
2. `POST /usuario/login` (público) → `AuthService.login` → `AuthenticationManager.authenticate` → `JwtUtil.generateToken` → retorna string `"Bearer <jwt>"`. Token expira em 1 hora; claim `role` incluída.
3. Requisições autenticadas enviam header `Authorization`; `JwtRequestFilter` valida e popula o contexto.

**Produtos / categorias (admin)** **[FATO]**
- Criar/editar/apagar produto e categoria: `ProdutoController` com `@PreAuthorize("hasRole('ADMIN')")` + regra em `SecurityConfig` (`hasRole("ADMIN")` para POST/PUT/DELETE `/produto/**`).
- Consultas públicas: `GET /produto/**` liberado (listar, por id, por sku, por nome, por categoria, dashboard, categorias).

**Pedidos** **[FATO]**
- `POST /pedido/criar-pedido` (autenticado): monta `Pedido`, resolve cada `Produto` por id, recalcula `subTotal` de cada item (`preco × quantidade`) e o total do pedido (soma dos subtotais + frete fixo **R$10,00** — hardcoded em `Pedido.recalcValores()`), associa ao usuário autenticado, salva. `dataCriacao` via `@PrePersist`.
- Itens: adicionar/buscar/atualizar/apagar via `/pedido/*-pedido-item`, sempre validando ownership (`pedido.usuario.email == email autenticado`).
- Ao atualizar pedido para status `PAGO`, grava `dataPagamento`.

**Usuário logado** **[FATO]**
- `GET /usuario/me`, `POST /usuario/me/telefone`, `POST /usuario/me/endereco`, `PUT /usuario`, `PUT /usuario/endereco`, `PUT /usuario/telefone`. Endereço/telefone verificam ownership antes de atualizar.

**Frontend → backend** **[FATO]**: o Axios usa `baseURL: http://localhost:8080` (`ts/services/api.ts`) com um interceptor que injeta `Authorization` a partir de `localStorage.token`. Não há interceptor de resposta (nenhum tratamento central de 401/refresh).

---

## 6. Modelo de dados

SGBD: **PostgreSQL**, schema `db_usuario`, gerado por Hibernate (`ddl-auto=update`) — **sem scripts SQL/migrations no repositório**. **[FATO]**

Entidades JPA e relações **[FATO]**:

- **Usuario** (`usuario`) — id, email (unique, not null), nome, senha (not null), role (enum STRING). Implementa `UserDetails`. Relações `@OneToMany` (cascade ALL, orphanRemoval) para `Endereco`, `Telefone`, `Pedido`.
- **Endereco** (`endereco`) — estado(2), cidade, cep(8), rua, numero(Integer), complemento(not null), `@ManyToOne` → Usuario (not null).
- **Telefone** (`telefone`) — telefone(10), ddd(2), `@ManyToOne` → Usuario (not null).
- **Categoria** (`categoria`) — nome (unique, not null), imagemURL, slug (not null, **não-unique na entidade** apesar de `existsBySlug` ser usado como se fosse único), `@OneToMany` → Produto (cascade ALL, orphanRemoval).
- **Produto** (`produto`) — nome(100), sku (unique, not null), descricao(300), preco (BigDecimal), imagem_url, ativo (Boolean, default true via `@PrePersist`), criadoEm, atualizadoEm, `@ManyToOne` → Categoria (nullable).
- **Pedido** (`pedido`) — `@ManyToOne` → Usuario (not null), status (enum STRING, not null), valorProduto, valorFrete, valorTotal (BigDecimal), formaPagamento (enum STRING, not null), dataCriacao, dataPagamento, `@OneToMany` → PedidoItem (cascade ALL, orphanRemoval).
- **PedidoItem** (`pedido_item`) — `@ManyToOne` → Pedido (not null), `@ManyToOne` → Produto (not null), quantidade, precoUnitario, subTotal (BigDecimal, not null).
- **Dashboard** (`dashboard`) — total_contas, total_produtos, total_categoria, total_visitas. **Entidade existe mas parece não ser usada como tabela real**: `ProdutoService.exibirDashboard()` monta um `DashboardDTO` calculado em tempo de execução via `count()` e **`totalVisitas` fixo em 1349** — `DashboardRepository` é injetado mas não é usado. **[FATO]**

ERD textual (cardinalidades) **[FATO]**:
```
Usuario 1 ──< N Endereco
Usuario 1 ──< N Telefone
Usuario 1 ──< N Pedido
Pedido  1 ──< N PedidoItem
Produto 1 ──< N PedidoItem
Categoria 1 ──< N Produto
Dashboard  (isolada; sem FK; provavelmente não populada)
```

Campos monetários: usam `BigDecimal` (correto para dinheiro). Porém **sem `precision`/`scale` definidos** nas colunas — o Hibernate criará `numeric` sem escala controlada, o que pode gerar imprecisão/arredondamento inconsistente. **[FATO]**

Inconsistências entre entidade e banco/uso **[FATO]**:
- `Categoria.slug` é tratado como único no código (`existsBySlug`, `findBySlug`) mas **não tem `unique=true`** na entidade → o banco permite slugs duplicados; a unicidade só é garantida por checagem em código (sujeita a corrida).
- `Endereco.numero` é `Integer` com `length=10` (length é ignorado para tipos numéricos).
- Colunas `Produto.CriadoEm`/`AtualizadoEm` usam nomes com maiúsculas — em PostgreSQL isso pode causar atrito de identificadores e fica inconsistente com o restante do schema snake_case.

---

## 7. Backend — análise detalhada

**Linguagem/framework**: Java 17, Spring Boot 4.0.6. **[FATO]**

**Segurança / JWT** **[FATO]**
- `JwtUtil`: HS256, segredo de `${jwt.secret}` (variável de ambiente — **bom**), expiração 1h, subject = e-mail, claim `role`. Usa API depreciada do JJWT (`setSubject`, `signWith(Key, SignatureAlgorithm)`, `parser().setSigningKey`) — estilo 0.11.x em uma dependência 0.13.0.
- `JwtRequestFilter`: extrai `Bearer`, valida, popula `SecurityContext`. Em exceção, apenas loga e segue — deixando o Spring rejeitar depois (comportamento aceitável).
- `SecurityConfig`: stateless, CSRF desativado, filtro JWT antes do `UsernamePasswordAuthenticationFilter`, `@EnableMethodSecurity`, BCrypt. **CORS `allowedOrigins("*")`** (comentário do autor reconhece que precisa mudar em produção).
- Autorização baseada em `requestMatchers` + `@PreAuthorize` nos endpoints de produto. Regra final `anyRequest().hasRole("ADMIN")`.

**Regras de negócio** **[FATO]**
- Cálculo de pedido centralizado nas entidades (`Pedido.recalcValores`, `PedidoItem.recalcValores`) — **preço é sempre re-derivado do `Produto` no servidor** (o cliente não consegue forjar preço; ponto positivo). Frete fixo R$10.
- Ownership de pedidos/itens/endereços/telefones checado no service comparando e-mail autenticado.

**Problemas objetivos identificados** (detalhados em §13):
- **Broken Access Control / IDOR** em `DELETE /usuario/{email}` → `UsuarioService.deletaUsuarioPorEmail`: **não há checagem de ownership nem `@PreAuthorize`**. Como `SecurityConfig` mapeia `/usuario/**` apenas como `authenticated()`, **qualquer usuário autenticado pode apagar a conta de qualquer outro usuário** informando o e-mail. **[FATO]** — CRÍTICO.
- **Comparação de String com `==`** em `ProdutoService.atualizaProduto` (`produtoDTO.getSku() == produtoAtualizado.getSku()`) e `atualizaCategoria` (slug). A lógica de checagem de duplicidade na atualização está incorreta (compara referências). **[FATO]** — bug de corretude.
- **N+1 / Lazy fora de transação**: `PedidoService.buscarTodosPedidos()` **não é `@Transactional`** e acessa `usuario.getPedidos()` (LAZY) e depois o converter percorre itens/produtos → risco de `LazyInitializationException` e/ou múltiplas queries N+1. Vários `paraXResponseDTO` disparam lazy loads de `produto`/`categoria`. **[FATO/INFERÊNCIA]**.
- **Sem paginação**: `GET /produto/todos` e `listarTodosOsProdutos()` fazem `findAll()` sem paginação. **[FATO]**.
- `emailExiste()` em `UsuarioService` tem `try/catch` que captura a própria exceção que acabou de lançar e a relança — lógica confusa/redundante. **[FATO]**.
- `@ExceptionHandler(Exception.class)` genérico está **comentado** no `GlobalExceptionHandler` → exceções inesperadas retornam o erro padrão do Spring (vazamento de detalhes internos). **[FATO]** — ver §9.
- Import não utilizado / dependências mortas: `PedidoController` injeta `PedidoRepository` sem usar; imports OSGi/`PublicKey` em `UsuarioControlller`.

**Validação** **[FATO]**: Bean Validation (`@Valid`) presente em signup, login, criar-produto, criar-categoria, criar-pedido. Porém **ausente** em vários endpoints de atualização (`PUT /usuario`, `PUT /produto/{id}`, `atualizar-pedido`, itens) — os `@RequestBody` de update **não** têm `@Valid`, então DTOs de update passam sem validação. **[FATO]**

**Transações** **[FATO]**: escrita majoritariamente `@Transactional` (mistura `jakarta.transaction.Transactional` e `org.springframework.transaction.annotation.Transactional` entre classes — inconsistente, mas funcional).

---

## 8. Frontend — análise detalhada

**Estrutura** **[FATO]**: HTML em `templates/`, lógica em `static/ts` (fonte) → `static/js` (compilado). Organização por `pages/`, `components/`, `services/`, `types/`, `validations/`. Sem framework SPA; cada página carrega seus módulos via `<script type="module">` e o Axios via CDN.

**Comunicação HTTP** **[FATO]**: `ts/services/api.ts` cria instância Axios com `baseURL: http://localhost:8080` **hardcoded** e interceptor de request injetando `Authorization` do `localStorage`. **Não há interceptor de resposta** (sem tratamento central de 401/expiração/refresh).

**Armazenamento de token** **[FATO]**: JWT salvo em `localStorage` (`token`) e o papel do usuário em `localStorage` (`user`) — `ts/pages/usuario.ts`, `ts/services/auth.ts`. Vulnerável a XSS (ver §9).

**Proteção de rotas** **[FATO]**: apenas **cosmética/cliente**. `auth.ts` chama `GET /usuario/me`; se `role==="ADMIN"` faz `display:block` em elementos `.admin-only`, senão `display:none`. Isso **não protege** nada — qualquer um pode abrir as páginas admin; a proteção real depende do backend (que de fato exige ADMIN nos endpoints de escrita de produto). Decisão de admin baseada em valor de `localStorage` (manipulável) apenas afeta UI.

**Tratamento de erros / loading** **[INFERÊNCIA/INDETERMINADO]**: há diretório `validations/` (validação de formulários de usuário, endereço, telefone, login) e utilitários `erroDiv`/`limparErros`. Estados de loading dedicados: não identificados de forma consistente. Não foi possível confirmar tratamento de erro uniforme em todas as telas a partir dos arquivos amostrados.

**Inconsistências frontend ↔ backend** **[FATO]** — endpoints chamados pelo frontend que **não existem** no backend atual:
- `usuario.service.ts` → `POST /usuario/criar` (backend expõe `POST /usuario/signup`).
- `usuario.service.ts` → `POST /usuario/criar/telefone`, `POST /usuario/criar/endereco` (backend: `/usuario/me/telefone`, `/usuario/me/endereco`).
- `catalogo.service.ts` → `GET /produto/todas-categoria` (backend: `GET /produto/categorias`).
- `PUT /usuario?id=` e payload `{nome, gmail}` no service diverge do backend (`PUT /usuario` lê usuário autenticado, sem `id`, campo `email` não `gmail`).

Isso indica que **partes do frontend estão desincronizadas do backend** (provavelmente refatorações de endpoint não propagadas). Algumas telas devem estar quebradas. **[INFERÊNCIA]**

**Telas identificadas** (`templates/`) e mapeamento aproximado **[FATO]**:
- `index.html` → auth.js (GET /usuario/me), navbar.
- `login.html` / `register.html` → usuario.js (login/signup), componentes de login/registro.
- `catalogo.html` → catalogo.service (categorias/produtos).
- `produtos.html` → produtoCategoria.js (produtos por categoria).
- `produtoIndividual.html` → produtoIndividual.js + produtoAleatorio.js.
- `profile.html` → profile.js + usuario.js (GET /usuario/me, atualização de dados/endereço/telefone).
- `admin.html`, `adminProduto.html`, `adminUsuario.html` → páginas administrativas (dashboard, CRUD produto, busca/gestão usuário).
- `about.html` → estática.

---

## 9. Segurança — análise detalhada

Classificação: **CRÍTICO / ALTO / MÉDIO / BAIXO / INFORMATIVO**.

**CRÍTICO**
1. **Senha do banco em texto puro versionada** — `application.properties`: `spring.datasource.password=luizmamarop`. Não está no `.gitignore`, portanto está no histórico do Git. **[FATO]** Impacto: comprometimento do banco. Recomendação: externalizar para variável de ambiente, rotacionar a senha, e purgar do histórico.
2. **Broken Access Control em exclusão de usuário** — `DELETE /usuario/{email}` sem ownership nem restrição de role (`UsuarioService.deletaUsuarioPorEmail` + `SecurityConfig` só `authenticated()`). Qualquer usuário logado apaga qualquer conta. **[FATO]** Impacto: exclusão maliciosa em massa.

**ALTO**
3. **JWT em `localStorage`** — `ts/services/api.ts`, `usuario.ts`. Exposto a roubo via XSS. **[FATO]** Recomendação: cookie `HttpOnly`+`Secure`+`SameSite` ou, no mínimo, mitigação de XSS e expiração curta (já é 1h).
4. **CORS liberado para `*`** — `SecurityConfig.corsConfigurationSource()` e `@CrossOrigin(origins="*")` em `UsuarioControlller`. **[FATO]** Com API stateless por token isso é menos grave que com cookies, mas ainda indevido para produção; o próprio autor anotou "tenho que mudar".
5. **Comunicação sem HTTPS** — frontend fixa `http://localhost:8080`; nenhuma configuração de TLS. **[FATO]** (esperado em dev, mas é dívida para produção).

**MÉDIO**
6. **Handler genérico de exceções desativado** — o `@ExceptionHandler(Exception.class)` está comentado no `GlobalExceptionHandler`. Erros não mapeados retornam resposta padrão do Spring, podendo **vazar stacktrace/detalhes internos**. **[FATO]**
7. **Validação ausente em endpoints de atualização** — `PUT`/`PATCH` sem `@Valid` (§7). Permite dados inconsistentes. **[FATO]**
8. **`ddl-auto=update` em uso** — schema gerenciado pelo Hibernate; risco de alterações não controladas de schema e divergência entre ambientes. **[FATO]**
9. **Unicidade de `slug` de categoria só em código** (sem constraint no banco) → condição de corrida pode inserir duplicatas. **[FATO]**
10. **`show-sql=true`** — SQL logado no console; ruído e potencial exposição de dados em logs. **[FATO]**

**BAIXO / INFORMATIVO**
11. **[INFORMATIVO]** SQL Injection: **não identificado**. Todo acesso é via Spring Data (query methods derivados) e MapStruct; não há concatenação de SQL nem `@Query` nativa. **[FATO]**
12. **[INFORMATIVO]** Exposição de senha em respostas: **mitigada** — `UsuarioDTO.senha` tem `@JsonProperty(access = WRITE_ONLY)`, então não é serializada em respostas. **[FATO]**
13. **[BAIXO]** Exposição de entidade JPA na borda: `PedidoController` importa entidades, e `ProdutoResponseDTO` importa `Categoria` (entidade) sem usá-la — acoplamento; risco de vazar entidade se reintroduzido. **[FATO]**
14. **[BAIXO]** Mensagens de erro concatenam identificadores (e-mail/ids) — vazamento mínimo de informação, mas ajuda enumeração. **[FATO]**
15. **[INFORMATIVO]** CSRF desativado — correto para API stateless por token; só se torna relevante se migrarem para cookies. **[FATO]**

---

## 10. Testes — estado atual e lacunas

**[FATO]**: existe **um único teste** — `CoretechApiApplicationTests.contextLoads()` (smoke test do contexto Spring). `package.json` tem `test` que apenas falha com "no test specified". A CI roda `./gradlew test`, mas efetivamente não há cobertura de lógica.

**Não coberto** (tudo, na prática): autenticação/JWT, autorização por role, ownership de pedidos/endereços/telefones, cálculo de valores de pedido, duplicidade de e-mail/sku/slug, mappers, tratamento de exceções, os fluxos de produto/pedido/usuário. **[FATO]**

**Testes que deveriam existir (prioridade)** **[INFERÊNCIA]**: (1) segurança — matriz de acesso por endpoint/role e ownership; (2) `PedidoService`/`recalcValores` (corretude monetária); (3) validações de DTO; (4) integração de repositórios (`@DataJpaTest`) para relações lazy/N+1; (5) `GlobalExceptionHandler`.

---

## 11. Infraestrutura e deploy — estado atual

**[FATO]**
- **Docker/Docker Compose: não existem** (nenhum `Dockerfile`/`docker-compose.yml`).
- **CI**: GitHub Actions em PR (build + test). **Sem CD/deploy**.
- **Config de produção**: inexistente — só `application.properties` apontando para `localhost` com credenciais fixas. Sem perfis (`application-prod.properties`).
- **Variáveis de ambiente**: apenas `JWT_SECRET` é externalizado; DB user/senha/URL estão fixos no arquivo.
- **Hospedagem / domínio / HTTPS**: não configurados/indeterminados.
- **Observabilidade / logs**: sem Actuator, sem métricas, sem logging estruturado; apenas `show-sql` e `logger.error` no filtro JWT.
- Build produz jar Spring Boot (bootJar); HELP.md menciona possibilidade de imagem OCI, mas não está configurado.

---

## 12. Mapa de funcionalidades

Legenda de status: **IMPLEMENTADO** / **PARCIAL** / **INCOMPLETO** / **NÃO IDENTIFICADO**.

| Funcionalidade | Usuário | Frontend | Endpoint | Service | Entidades | Status |
|---|---|---|---|---|---|---|
| Cadastro (signup) | Anônimo | register.html/usuario.ts | `POST /usuario/signup` | UsuarioService.salvaUsuario | Usuario | IMPLEMENTADO (backend); **frontend chama `/usuario/criar` → quebrado** → PARCIAL |
| Login (JWT) | Anônimo | login.html/usuario.ts | `POST /usuario/login` | AuthService.login | Usuario | IMPLEMENTADO |
| Ver perfil | Usuário | profile.html/profile.ts | `GET /usuario/me` | buscarUsuarioAutenticado | Usuario+Endereco+Telefone+Pedido | IMPLEMENTADO |
| Atualizar usuário | Usuário | profile.ts/usuario.service | `PUT /usuario` | atualizarUsuario | Usuario | PARCIAL (sem `@Valid`; frontend usa payload/rota divergente) |
| Add/editar telefone | Usuário | profile.ts | `POST /usuario/me/telefone`, `PUT /usuario/telefone` | salva/atualizarTelefone | Telefone | IMPLEMENTADO (backend); frontend com rota divergente → PARCIAL |
| Add/editar endereço | Usuário | profile.ts | `POST /usuario/me/endereco`, `PUT /usuario/endereco` | salva/atualizarEndereco | Endereco | IMPLEMENTADO (backend); frontend com rota divergente → PARCIAL |
| Excluir usuário | Usuário(!) | adminUsuario/usuario.service | `DELETE /usuario/{email}` | deletaUsuarioPorEmail | Usuario | IMPLEMENTADO mas **INSEGURO** (sem ownership/role) |
| Buscar usuário por e-mail | Admin | adminUsuario.ts | `GET /usuario?email=` | buscarUsuarioPorEmail | Usuario | IMPLEMENTADO |
| Listar/consultar produtos | Público | catalogo/produtos/produtoIndividual | `GET /produto/**` | Produto queries | Produto+Categoria | IMPLEMENTADO |
| Dashboard | Admin | admin.ts/produto.service | `GET /produto/dashboard` | exibirDashboard | (counts) | PARCIAL (`totalVisitas` fixo 1349; entidade Dashboard não usada) |
| CRUD produto | Admin | adminProduto.ts | `POST/PUT/DELETE /produto…` | salva/atualiza/apagaProduto | Produto+Categoria | IMPLEMENTADO (bug de comparação `==` no update) |
| CRUD categoria | Admin | adminProduto/categoria.ts | `POST/PUT/DELETE /produto/categoria(s)` | salva/atualiza/apagaCategoria | Categoria | IMPLEMENTADO (unicidade slug frágil; bug `==` no update) |
| Criar pedido | Usuário | INDETERMINADO | `POST /pedido/criar-pedido` | salvaPedido | Pedido+Item+Produto | IMPLEMENTADO (backend). Tela dedicada não identificada no frontend → PARCIAL |
| Gerir itens do pedido | Usuário | INDETERMINADO | `/pedido/*-pedido-item` | salva/atualiza/apagaPedidoItem | PedidoItem | IMPLEMENTADO (backend); frontend NÃO IDENTIFICADO |
| Listar meus pedidos | Usuário | INDETERMINADO | `GET /pedido/buscar-todos-pedidos` | buscarTodosPedidos | Pedido | IMPLEMENTADO (backend; risco lazy/N+1) |
| Carrinho | Usuário | — | — | — | (enum status CARRINHO existe) | NÃO IDENTIFICADO (só há o enum) |

Observação **[FATO]**: `PagamentoStatus` inclui `CARRINHO`, `PROCESSANDO`, `REEMBOLSADO` etc., mas **não há lógica de máquina de estados** que gerencie transições — apenas a gravação de `dataPagamento` quando vira `PAGO`.

---

## 13. Problemas encontrados (priorizado)

Formato: **Problema — Local — Evidência — Impacto — Severidade — Recomendação**. Nenhuma correção foi aplicada.

1. **Credenciais de banco versionadas** — `application.properties` — `spring.datasource.password=luizmamarop` (não ignorado) — comprometimento do banco / segredo no histórico Git — **CRÍTICO** — externalizar em env, rotacionar senha, remover do histórico.
2. **IDOR na exclusão de usuário** — `UsuarioControlller.deletarUsuarioPorEmail` + `UsuarioService.deletaUsuarioPorEmail` + `SecurityConfig` (`/usuario/**` = `authenticated`) — sem ownership nem role — qualquer usuário apaga qualquer conta — **CRÍTICO** — restringir a ADMIN e/ou ao próprio dono; nunca aceitar e-mail arbitrário sem verificação.
3. **Comparação de String com `==`** — `ProdutoService.atualizaProduto` e `atualizaCategoria` (`getSku() == …`, `getSlug() == …`) — compara referências, não valores — checagem de duplicidade no update é logicamente inválida — **ALTO** — usar `.equals()`/`Objects.equals` e rever a intenção da regra.
4. **JWT em localStorage** — `ts/services/api.ts`, `ts/pages/usuario.ts` — token acessível a JS/XSS — sequestro de sessão — **ALTO** — cookie HttpOnly ou hardening equivalente.
5. **CORS `*`** — `SecurityConfig`, `@CrossOrigin` no controller — origens irrestritas — abuso cross-site em produção — **ALTO** — restringir a domínios conhecidos.
6. **Lazy/N+1 em listagem de pedidos** — `PedidoService.buscarTodosPedidos` (não `@Transactional`) e converters que percorrem `pedidoItems`/`produto`/`categoria` — múltiplas queries e risco de `LazyInitializationException` — degradação/erro em runtime — **ALTO** — `@Transactional(readOnly=true)` + `JOIN FETCH`/EntityGraph.
7. **Handler global de Exception desativado** — `GlobalExceptionHandler` (bloco comentado) — respostas de erro não padronizadas para exceções inesperadas — vazamento de detalhes internos — **MÉDIO** — reativar com resposta 500 genérica e logging.
8. **`@Valid` ausente em updates** — `PUT/PATCH` em `UsuarioControlller`/`ProdutoController`/`PedidoController` — DTOs de update não validados — dados inválidos persistidos — **MÉDIO** — adicionar `@Valid`.
9. **Sem paginação** — `ProdutoController.listarTodosProdutos` / `findAll()` — carrega tudo em memória — escalabilidade — **MÉDIO** — `Pageable`.
10. **Frontend dessincronizado do backend** — `usuario.service.ts`, `catalogo.service.ts` (`/usuario/criar`, `/produto/todas-categoria`, etc.) — endpoints inexistentes no backend — telas quebradas — **MÉDIO** — alinhar rotas/contratos.
11. **`ddl-auto=update` + sem migrations** — `application.properties` — schema gerido implicitamente — divergência entre ambientes, risco de dados — **MÉDIO** — adotar Flyway/Liquibase e `validate`.
12. **Campos monetários sem precision/scale** — entidades `Pedido`/`PedidoItem`/`Produto` — colunas `numeric` sem escala — arredondamento/precisão inconsistente — **MÉDIO** — definir `@Column(precision, scale)`.
13. **Unicidade de slug só em código** — `Categoria` (sem `unique=true`) vs `existsBySlug` — corrida pode duplicar — **BAIXO/MÉDIO** — constraint no banco.
14. **Dashboard fake / entidade órfã** — `ProdutoService.exibirDashboard` (`totalVisitas=1349`), `DashboardRepository` injetado e não usado — métrica não confiável — **BAIXO** — remover entidade ou implementar de fato.
15. **`show-sql=true`, imports mortos, typo `UsuarioControlller`/`infraestructure`, api.js duplicado** — vários — ruído/manutenção — **BAIXO/INFORMATIVO** — limpeza.
16. **Spring Boot 4.0.6 / JJWT com API depreciada** — `build.gradle`, `JwtUtil` — versão de framework a validar e uso de API antiga do JJWT — risco de incompatibilidade/manutenção — **BAIXO** — confirmar versão-alvo suportada e migrar JJWT para API 0.12+.

---

## 14. Dívida técnica (por área, priorizada por impacto)

- **Segurança**: credenciais versionadas; IDOR de exclusão; token em localStorage; CORS `*`; sem HTTPS; handler de erro genérico desativado. *(maior prioridade)*
- **Backend/corretude**: comparação `==`; lazy/N+1; validação ausente em updates; máquina de estados de pagamento inexistente; `emailExiste` redundante.
- **Banco**: sem migrations; `ddl-auto=update`; monetários sem escala; unicidade de slug frágil; entidade Dashboard órfã.
- **Testes**: praticamente inexistentes.
- **Infraestrutura**: sem Docker; sem CD; sem perfis de ambiente; sem observabilidade.
- **Frontend**: contratos dessincronizados; proteção de rota só cosmética; baseURL hardcoded; duplicação `service/`/`services/`; ausência de tratamento central de 401.
- **Arquitetura**: nomenclatura (`infraestructure`, `UsuarioControlller`); controller acoplado a repository; DTO importando entidade.
- **Documentação**: inexistente além do HELP.md boilerplate.
- **Performance**: `findAll()` sem paginação; N+1.

---

## 15. Riscos técnicos atuais (maiores)

1. **Segurança de dados**: credenciais expostas + IDOR de exclusão permitem perda/comprometimento de dados reais se isso for a algum ambiente compartilhado. **[FATO]**
2. **Integridade financeira**: cálculo monetário sem escala definida + ausência de testes no fluxo de pedidos → risco de valores incorretos sem detecção. **[INFERÊNCIA]**
3. **Evolução travada por falta de migrations**: `ddl-auto=update` torna mudanças de schema imprevisíveis e migração para produção arriscada. **[FATO]**
4. **Regressões silenciosas**: sem testes de negócio, qualquer refatoração pode quebrar autorização/cálculos sem aviso. **[FATO]**
5. **Frontend quebrado/desalinhado**: contratos divergentes indicam que partes do produto não funcionam fim-a-fim hoje. **[INFERÊNCIA]**

---

## 16. Recomendações (ordem sugerida)

**Primeiro (contenção de risco crítico, sem refatorar arquitetura)**
1. Remover credenciais do `application.properties`, rotacionar a senha do banco, externalizar via env e purgar do histórico Git.
2. Corrigir o IDOR de `DELETE /usuario/{email}` (restringir a ADMIN e/ou dono).
3. Reativar um handler global de erro 500 seguro.

**Segundo (corretude e confiabilidade)**
4. Corrigir comparações `==` de String.
5. Tornar leituras de pedido transacionais e resolver N+1 (fetch/EntityGraph).
6. Adicionar `@Valid` nos updates; definir `precision/scale` monetários.
7. Introduzir testes de segurança e do fluxo de pedidos (base de regressão).

**Terceiro (fundação de evolução)**
8. Adotar Flyway/Liquibase e mudar `ddl-auto` para `validate`.
9. Containerizar (Dockerfile + compose com PostgreSQL) e criar perfis de ambiente.
10. Alinhar contratos frontend↔backend; centralizar tratamento de 401; revisar armazenamento de token; restringir CORS.

---

## 17. Plano de evolução (sequência racional)

1. **Estabilização de segurança** (recomendações 1–3) — antes de qualquer nova feature.
2. **Rede de segurança de testes** — testes de autorização, ownership e cálculo de pedidos; habilitar cobertura na CI.
3. **Fundação de dados** — migrations + validação de schema + tipos monetários corretos + constraints (slug único).
4. **Correções de corretude/performance** — `==`, N+1, paginação, validação de updates.
5. **Alinhamento do frontend** — sincronizar endpoints, tratamento de erros/401, revisão de storage de token e proteção real de páginas.
6. **Infra/entrega** — Docker Compose, perfis (`dev`/`prod`), HTTPS, Actuator/observabilidade, pipeline de deploy.
7. **Evolução de domínio** — máquina de estados de pagamento, carrinho real, dashboard verdadeiro (ou remoção da entidade órfã).
8. **Higiene de código** — nomenclatura, remoção de duplicações/imports mortos, separação clara de camadas.

---

## 18. Documentação recomendada (a criar depois)

Estrutura sugerida — **ainda não criada**:

```
docs/
├── architecture.md      # visão de camadas, monolito API+static, diagrama de componentes, decisões (ADRs)
├── database.md          # ERD, tabelas/colunas/constraints, política de migrations, tipos monetários
├── authentication.md    # fluxo JWT, expiração, claims, roles, storage no cliente, CORS
├── authorization.md     # matriz endpoint × role × ownership (fonte de verdade de acesso)
├── api.md               # contrato REST: rotas, params, DTOs de request/response, códigos de erro
├── frontend.md          # estrutura TS→JS, telas × endpoints, build, convenções, tratamento de erro
├── backend.md           # pacotes, services/regras de negócio, mappers, exceções, transações
├── business-rules.md    # regras de pedido (frete, recálculo), status de pagamento, unicidade
├── deployment.md        # Docker, perfis, variáveis de ambiente, HTTPS, CI/CD
└── development.md        # setup local, banco, rodar backend+frontend, gerar TS, padrões de commit
```

Prioridade inicial: `authorization.md` (dado o risco de acesso), `database.md` (fundação de migrations) e `api.md` (para realinhar o frontend).

---

### Partes verificadas / limites desta análise
Foram lidos: todos os `.java` de `src/main`, `application.properties`, `build.gradle`, `package.json`, `tsconfig.json`, workflow de CI, `.gitignore`, `HELP.md`, os `templates/*.html` (script wiring) e os `ts/services/*` + amostras de `ts/pages`. Não foram lidos byte a byte todos os `ts/pages/*` e CSS (baixo valor diagnóstico); afirmações sobre telas específicas do frontend são marcadas como **[INFERÊNCIA]**/**[INDETERMINADO]** quando não confirmadas no código. Nada foi executado, alterado, comitado ou instalado.
