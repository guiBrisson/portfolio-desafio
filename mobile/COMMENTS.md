## Introdução

Este documento registra minhas reflexões, ideias e decisões tomadas durante o desenvolvimento.
O objetivo é fornecer um histórico claro das minhas abordagens e raciocínios.

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

### 3. Devo usar DI?

Optei por não utilizar injeção de dependências no desenvolvimento da aplicação, dado seu pequeno
escopo, e declarei manualmente as dependências no entry point, a `MainActivity` e passei essas
dependências para o navegador. Essa decisão visa simplificar a arquitetura do projeto, reduzindo a
complexidade e facilitando a manutenção, especialmente em aplicações menores, onde a sobrecarga de
configuração da injeção de dependências pode não justificar seus benefícios. Considerei a injeção de
dependência, que oferece vantagens como a atribuição de instâncias em ciclos de vida específicos,
como o `ViewModel`, mas decidi que, neste caso, não era a melhor abordagem.

### 4. Declaração de interface

Optei por utilizar o Jetpack Compose para a criação da interface do usuário, pois é uma biblioteca
mais atual e flexível, que permite a construção de componentes customizados de forma mais eficiente.
Além disso, o Jetpack Compose oferece uma sintaxe declarativa que simplifica a gestão do estado da
interface, promovendo uma melhor experiência de desenvolvimento e manutenção ao longo do ciclo de
vida do projeto.

### 5. Webview

Como esta é a minha primeira experiência implementando uma webview, inicialmente considerei utilizar
uma biblioteca, facilitando o controle do estado da tela e gerenciando aspectos como a navegação ao
pressionar o botão 'voltar'. No entanto, após analisar o código-fonte de algumas dessas bibliotecas,
optei por desenvolver uma implementação própria. Concluí que o que eu desejava realizar não
demandaria muito tempo e resultaria em um código com menos dependências.

---

## Melhorias e/ou ideias

1. Esconder informações sensíveis, como a base url.
2. Módulo de design system (`:core:design-system`) para o `MaterialTheme` e os componentes.
3. Uma paginação do feed mais suave. 
4. Manter o estado (ou pelo menos a atual `url`) da webview em caso de alguma recomposição da tela.
