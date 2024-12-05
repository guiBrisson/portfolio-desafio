## Introdução

Este documento registra minhas reflexões, ideias e decisões tomadas durante o desenvolvimento.
O objetivo é fornecer um histórico claro das minhas abordagens e raciocínios.

---

## Reflexões e Decisões

### 1. Onde começar?

Decidi iniciar o desenvolvimento do projeto pela primeira regra de negócio definida no `README`,
visando estabelecer uma camada de dados antes de criar as interfaces. Essa abordagem gerou a
necessidade de outros módulos, como `:core:network` e `:core:model`. Considerei alternativas como a
criação do design no Figma e a definição completa da arquitetura e bibliotecas, mas optei por adiar
essas etapas para evitar overengineering e priorizar a simplicidade, buscando entender completamente
os requisitos e dados disponíveis.

### 2. Documentando módulos

Para facilitar o entendimento das minhas decisões, estou criando um arquivo `README.md` para cada
módulo do projeto. Além disso, essa abordagem ajudará a manter a organização do projeto e servirá
como um guia útil para possíveis futuras manutenções e expansões.

### 3. Devo usar DI?

Optei por não utilizar injeção de dependências no desenvolvimento da aplicação, dado seu pequeno
escopo, e declarei manualmente as dependências no entry point, a `MainActivity` e passei essas
dependências para o navegador. Essa decisão visa simplificar a arquitetura do projeto, reduzindo a
complexidade e facilitando a manutenção, especialmente em aplicações menores, onde a sobrecarga de
configuração da injeção de dependências pode não justificar seus benefícios. Considerei a injeção de
dependência, que oferece vantagens como a atribuição de instâncias em ciclos de vida específicos,
como o `ViewModel`, mas decidi que, neste caso, não era a melhor abordagem.

---

## Melhorias ou ideias

1. Esconder informações sensíveis, como a base url.
2. Desenvolver uma tela de onboarding que permita ao usuário selecionar seus temas favoritos,
   possibilitando a criação de uma seção "For You", proporcionando uma experiência mais
   personalizada.
