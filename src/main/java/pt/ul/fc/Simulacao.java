package pt.ul.fc;

/**
 * Simulação de uma bola numa caixa retangular, vista de cima (sem gravidade).
 * A bola desloca-se em linha reta e ressalta nas paredes sem perder energia.
 * Unidades: metros e segundos. A origem é o canto inferior esquerdo da caixa
 * e o eixo y aponta para cima.
 */
public class Simulacao {
    /** Largura da caixa (m). */
    public static final double LARGURA = 8.0;
    /** Altura da caixa (m). */
    public static final double ALTURA = 8.0;
    /** Raio da bola (m). */
    public static final double RAIO = 0.3;
    /**
     * Fator de velocidade da simulação: 1.0 corresponde a tempo real
     * (2.0 seria o dobro da velocidade, 0.5 metade).
     */
    public static final double VELOCIDADE_SIMULACAO = 1.0;

    private final Bola bola;

    /**
     * Cria uma simulação com a bola dada.
     */
    public Simulacao(Bola bola) {
        this.bola = bola;
    }

    /**
     * Devolve a bola simulada.
     */
    public Bola getBola() {
        return bola;
    }

    /**
     * Avança a simulação dt segundos: a bola desloca-se com a sua velocidade
     * e, se tocar numa parede, ressalta.
     *
     * @param dt duração do passo, em segundos
     */
    public void passo(double dt) {
        bola.mover(dt * VELOCIDADE_SIMULACAO);
        ressaltarNasParedes();
    }

    /**
     * Mantém a bola dentro da caixa: quando toca numa parede, a componente
     * da velocidade perpendicular a essa parede troca de sentido.
     */
    private void ressaltarNasParedes() {
        if (bola.getX() < RAIO) {
            bola.setPosicao(RAIO, bola.getY());
            bola.setVelocidade(-bola.getVx(), bola.getVy());
        } else if (bola.getX() > LARGURA - RAIO) {
            bola.setPosicao(LARGURA - RAIO, bola.getY());
            bola.setVelocidade(-bola.getVx(), bola.getVy());
        }
        if (bola.getY() < RAIO) {
            bola.setPosicao(bola.getX(), RAIO);
            bola.setVelocidade(bola.getVx(), -bola.getVy());
        } else if (bola.getY() > ALTURA - RAIO) {
            bola.setPosicao(bola.getX(), ALTURA - RAIO);
            bola.setVelocidade(bola.getVx(), -bola.getVy());
        }
    }
}
