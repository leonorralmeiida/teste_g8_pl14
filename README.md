# Ramo `65618`: depuração

Este ramo é só seu. Contém um pequeno projeto Java / Maven que simula uma
**bola numa caixa, vista de cima (sem gravidade)**.

## O projeto

| Ficheiro | Conteúdo |
|---|---|
| `src/main/java/pt/ul/fc/Bola.java` | a bola: posição, velocidade, movimento |
| `src/main/java/pt/ul/fc/Simulacao.java` | a caixa e as regras da simulação (`passo(dt)`) |
| `src/main/java/pt/ul/fc/Main.java` | corre a simulação e imprime a posição da bola em cada passo |
| `src/main/java/pt/ul/fc/Gui.java` | interface gráfica **opcional** (ver abaixo) |
| `src/test/java/pt/ul/fc/SimulacaoTest.java` | dois testes que já existem (e passam) |

## Especificação

A caixa é vista de cima: **não há gravidade**.

- A caixa tem 10 m de largura e 8 m de altura; a origem é o canto inferior esquerdo e o eixo y aponta para **cima**.
- A bola tem 0,25 m de raio e nunca sai da caixa.
- `Simulacao.passo(dt)` avança a simulação `dt` segundos.
- Em cada passo, a bola desloca-se em linha reta com a sua velocidade, em tempo real (`VELOCIDADE_SIMULACAO` é 1,0): num passo de `dt` segundos, `x` aumenta `vx * dt` e `y` aumenta `vy * dt`.
- A velocidade da bola é a que foi dada ao construtor, até a bola bater numa parede.
- Quando toca numa parede, a bola ressalta sem perder energia: a componente da velocidade perpendicular à parede troca de sentido.

## Relatório de erro

Um utilizador abriu o seguinte *issue*:

> **A bola não se mexe**
>
> Corri a simulação (classe `Main`) com a bola na posição (4,4; 4,3) e velocidade inicial (-1,8; -1,4) m/s. Esperava que a bola se deslocasse em linha reta nessa direção e ressaltasse nas paredes, mas a posição da bola nunca muda: fica sempre em (4,4; 4,3).

O utilizador tem razão: há **um** erro no código. A correção é pequena.

## O que fazer

1. **Reproduzir o erro.** Leia a descrição do erro e depois corra o programa (tem duas formas
   de o fazer: no terminal, ou com interface gráfica; veja a secção *Comandos úteis*).
   Confirme o erro e como ele se manifesta. Escreva um teste JUnit (um MRE) em `src/test/java/pt/ul/fc/`
   que reproduza o problema descrito no relatório. Use os testes de `SimulacaoTest.java`
   como modelo. O teste tem de **falhar** no código atual e **passar** depois de o erro
   ser corrigido (um teste que falha sempre não conta).
2. **Encontrar e corrigir o erro.** Use o depurador (*debugger*) do seu IDE:
   coloque *breakpoints* nos métodos chamados pelo seu teste e veja os valores de
   `x`, `y`, `vx` e `vy` passo a passo. Quando encontrar a causa, corrija-a em `src/main`.
   Todos os testes (`mvn test`) devem passar.
3. **Submeter.** Faça `git push origin 65618` (para o seu *fork*) e abra um
   *Pull Request* cuja base é o ramo `65618` do repositório original.

### Regras

- Não mude nomes de classes nem assinaturas de métodos.
- Em `src/main`, altere **apenas** o necessário para corrigir o erro (não acrescente
  métodos nem classes): na avaliação, o seu teste é executado sobre a versão original
  de `src/main` (onde tem de falhar) e sobre uma versão corrigida (onde tem de passar).
- A correção é avaliada com testes nossos, que verificam a especificação acima.

## Comandos úteis

Para correr o programa no terminal:

```bash
mvn test                                   # compilar e correr os testes
mvn -q compile exec:java                   # correr a simulação (imprime as posições)
mvn -q compile exec:java > saida.log 2> erros.log   # guardar a saída num ficheiro
```

Também pode correr `Main` diretamente no IDE (útil para usar o depurador).

**Interface gráfica (opcional):** para ver a bola a mexer-se, corra

```bash
mvn -q compile exec:java -Dexec.mainClass=pt.ul.fc.Gui
```

Se a janela não abrir no seu computador, não faz mal: não precisa dela para o trabalho.
Use `Main` e os testes. A seta verde representa a velocidade da bola.
