package pt.ul.fc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Interface gráfica (opcional) da simulação: uma bola branca numa caixa preta,
 * com uma seta verde que representa a velocidade. Usa as mesmas condições iniciais que {@link Main}.
 *
 * Se a interface gráfica não funcionar no seu computador, use {@link Main},
 * que imprime as posições na consola. Não é preciso a interface gráfica
 * para fazer o trabalho.
 */
public class Gui extends JPanel {
    private static final int ATRASO_MS = 16;
    private static final double LADO_MAXIMO_PX = 600.0;
    private static final double ESCALA =
        LADO_MAXIMO_PX / Math.max(Simulacao.LARGURA, Simulacao.ALTURA);
    /** A seta da velocidade mostra o deslocamento da bola neste número de segundos. */
    private static final double SEGUNDOS_POR_SETA = 0.25;

    private final Simulacao simulacao;
    private long ultimoInstante = System.nanoTime();

    public Gui(Simulacao simulacao) {
        this.simulacao = simulacao;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(
            (int) Math.round(Simulacao.LARGURA * ESCALA),
            (int) Math.round(Simulacao.ALTURA * ESCALA)));

        new Timer(ATRASO_MS, e -> {
            long agora = System.nanoTime();
            double dt = Math.min((agora - ultimoInstante) / 1e9, 0.05);
            ultimoInstante = agora;
            simulacao.passo(dt);
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        Bola bola = simulacao.getBola();
        double d = 2 * Simulacao.RAIO * ESCALA;
        double px = bola.getX() * ESCALA;
        double py = getHeight() - bola.getY() * ESCALA; // o eixo y do ecrã aponta para baixo
        g2.fillOval((int) Math.round(px - d / 2), (int) Math.round(py - d / 2),
            (int) Math.round(d), (int) Math.round(d));

        desenharVelocidade(g2, px, py, bola.getVx(), bola.getVy());
    }

    /**
     * Desenha uma seta verde, a partir do centro da bola, que representa a
     * velocidade (vx, vy): aponta no sentido do movimento e o comprimento é
     * proporcional à rapidez.
     */
    private static void desenharVelocidade(Graphics2D g2, double px, double py,
                                           double vx, double vy) {
        double dx = vx * ESCALA * SEGUNDOS_POR_SETA;
        double dy = -vy * ESCALA * SEGUNDOS_POR_SETA; // o eixo y do ecrã aponta para baixo
        double comprimento = Math.hypot(dx, dy);
        if (comprimento < 1) {
            return; // bola parada: não há seta
        }
        double fx = px + dx;
        double fy = py + dy;
        double angulo = Math.atan2(dy, dx);
        double ponta = Math.min(10, comprimento / 2);

        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new Line2D.Double(px, py, fx, fy));
        g2.draw(new Line2D.Double(fx, fy,
            fx - ponta * Math.cos(angulo - Math.PI / 6), fy - ponta * Math.sin(angulo - Math.PI / 6)));
        g2.draw(new Line2D.Double(fx, fy,
            fx - ponta * Math.cos(angulo + Math.PI / 6), fy - ponta * Math.sin(angulo + Math.PI / 6)));
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            semInterface(null);
            return;
        }
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame janela = new JFrame("Simulação da bola");
                janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janela.setContentPane(new Gui(Main.criarSimulacao()));
                janela.pack();
                janela.setResizable(false);
                janela.setLocationRelativeTo(null);
                janela.setVisible(true);
            });
        } catch (Exception | Error e) {
            // p.ex. HeadlessException ou AWTError (sem ecrã disponível)
            semInterface(e.getCause() != null ? e.getCause() : e);
        }
    }

    private static void semInterface(Throwable causa) {
        System.err.println("Não foi possível abrir a interface gráfica neste computador"
            + (causa != null ? " (" + causa + ")" : "") + ".");
        System.err.println("Não faz mal: a interface gráfica é opcional.");
        System.err.println("Use a classe Main, que imprime as posições na consola.");
    }
}
