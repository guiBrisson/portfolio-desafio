## Introdução

Este documento registra minhas reflexões, ideias e decisões tomadas durante o desenvolvimento,
sempre com um foco na simplicidade. O objetivo é fornecer um histórico claro e acessível das minhas
abordagens e raciocínios, facilitando a compreensão e a continuidade do projeto.

*obs.: Os commits também contém informações/comentários do que está sendo implementado.*

---

## Reflexões e Decisões

### 1. App Modularizado

Eu acredito que a criação de um aplicativo modularizado é impotante para garantir uma melhor
organização e manutenibilidade do código. Essa abordagem permite que diferentes partes do aplicativo
sejam desenvolvidas e testadas de forma independente, facilitando a identificação de problemas e a
implementação de novas funcionalidades.
Para facilitar o entendimento das minhas decisões, estou criando um arquivo `README.md` para cada
módulo do projeto.

### 2. Onde começar?

Decidi iniciar o desenvolvimento do projeto pela primeira regra de negócio definida no `README`,
visando estabelecer uma camada de dados antes de criar as interfaces. Essa abordagem gerou a
necessidade de outros módulos, como `:core:network` e `:core:model`. Considerei alternativas como a
criação do design no Figma e a definição completa da arquitetura e bibliotecas, mas optei por adiar
essas etapas para evitar overengineering e priorizar a simplicidade, buscando entender completamente
os requisitos e dados disponíveis.

### 3. Network Layer

Escolhi usar o Ktor em vez do Retrofit para a camada de rede do meu projeto. A diferença principal é
que o Ktor é mais direto e flexível. Enquanto o Retrofit usa anotações e interfaces, o que pode
deixar as coisas um pouco confusas, o Ktor permite que eu escreva o código de forma mais clara e
fácil de entender. Com o Ktor, tenho mais controle sobre as requisições e posso personalizá-las
facilmente. Essa simplicidade e flexibilidade foram decisivas para a minha escolha.

### 4. Devo usar DI?

Optei por não utilizar injeção de dependências no desenvolvimento da aplicação, dado seu pequeno
escopo, e declarei manualmente as dependências no entry point, a `MainActivity` e passei essas
dependências para o navegador. Essa decisão visa simplificar a arquitetura do projeto, reduzindo a
complexidade e facilitando a manutenção, especialmente em aplicações menores, onde a sobrecarga de
configuração da injeção de dependências pode não justificar seus benefícios. Considerei a injeção de
dependência, que oferece vantagens como a atribuição de instâncias em ciclos de vida específicos,
como o `ViewModel`, mas decidi que, neste caso, não era a melhor abordagem.

### 5. Declaração de interface

Optei por utilizar o Jetpack Compose para a criação da interface do usuário, pois é uma biblioteca
mais atual e flexível, que permite a construção de componentes customizados de forma mais eficiente.
Além disso, o Jetpack Compose oferece uma sintaxe declarativa que simplifica a gestão do estado da
interface, promovendo uma melhor experiência de desenvolvimento e manutenção ao longo do ciclo de
vida do projeto.

### 6. Webview

Como esta é a minha primeira experiência implementando uma webview, inicialmente considerei utilizar
uma biblioteca, facilitando o controle do estado da tela e gerenciando aspectos como a navegação ao
pressionar o botão 'voltar'. No entanto, após analisar o código-fonte de algumas dessas bibliotecas,
optei por desenvolver uma implementação própria. Concluí que o que eu desejava realizar não
demandaria muito tempo e resultaria em um código com menos dependências.

### 7. Erro como valor

Eu sempre dou preferência a tratar erros como valor, em vez de usar `exceptions` ou `throwables`.
Nesse projeto, decidi criar uma classe para lidar com os erros, chamada de `Result`.
O `Result` pode conter o valor ou o erro, sendo este uma interface selada que representa a base para
todos os erros do projeto. Para ser bem sincero, não estou 100% contente com o resultado dessa
abordagem, e talvez haja uma escolha melhor, como a biblioteca [Arrow](https://arrow-kt.io/). Porém,
para um projeto pequeno, acredito que é o suficiente.

---

## Melhorias e/ou ideias

1. Esconder informações sensíveis, como a base url.
2. Módulo de design system (`:core:design-system`) para o `MaterialTheme` e os componentes.
3. Uma paginação do feed mais suave.
4. Criação do módulo de dados locais (`:core:database`, talvez com
   o [Room](https://developer.android.com/training/data-storage/room/)) para implementar a abordagem
   [*offline-first*](https://developer.android.com/topic/architecture/data-layer/offline-first).
5. Manter o estado (ou pelo menos a atual `url`) da webview em caso de alguma recomposição da tela.
6. Implementação da biblioteca [Arrow](https://arrow-kt.io/) para realizar o tratamento dos erros no
   projeto.
7. Escrever testes unitários para o módulo de dados (`:core:data`).

## Sugestão de melhoria do desafio

1. A documentação referente à paginação está incorreta ou incompleta. A `URL` de paginação para URI
   é: https://native-leon.globo.com/feed/page/{tenant}/{offer}/{page}.
2. No arquivo `menu.json`, a maioria dos campos URL não está com o protocolo `HTTPS`, e sim `HTTP`.