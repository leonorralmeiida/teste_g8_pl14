package pt.ul.fc;

import java.util.Locale;

/**
 * Executa a simulação e imprime, em cada passo, a posição e a velocidade da bola.
 */
public class Main {
    /** Duração de cada passo da simulação (s). */
    public static final double DT = 0.05;
    /** Número de passos a simular. */
    public static final int PASSOS = 100;

    /**
     * Cria a simulação com as condições iniciais do relatório de erro.
     */
    public static Simulacao criarSimulacao() {
        return new Simulacao(new Bola(3.0, 5.6, 0.0, 0.0));
    }

    public static void main(String[] args) {
        Simulacao simulacao = criarSimulacao();
        Bola bola = simulacao.getBola();
        System.out.println("Simulação: bola em queda livre, a ressaltar no chão");
        System.out.printf(Locale.ROOT, "Caixa: %.2f x %.2f m, raio da bola: %.2f m%n",
            Simulacao.LARGURA, Simulacao.ALTURA, Simulacao.RAIO);
        System.out.println();
        System.out.printf(Locale.ROOT, "%5s | %6s | %8s | %8s | %9s | %9s%n",
            "passo", "t (s)", "x (m)", "y (m)", "vx (m/s)", "vy (m/s)");
        for (int passo = 0; passo <= PASSOS; passo++) {
            System.out.printf(Locale.ROOT, "%5d | %6.2f | %8.3f | %8.3f | %9.3f | %9.3f%n",
                passo, passo * DT, bola.getX(), bola.getY(), bola.getVx(), bola.getVy());
            simulacao.passo(DT);
        }
    }
}
