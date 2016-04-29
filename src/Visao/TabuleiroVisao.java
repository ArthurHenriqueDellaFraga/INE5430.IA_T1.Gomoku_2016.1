package Visao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import Controle.TabuleiroControle;
import Enumeracao.Alinhamento;
import PadraoDeProjeto.Propagador;
import Primitiva.Peca;
import Primitiva.Posicao;

//Adaptado de http://cs.gettysburg.edu/~tneller/cs111/gomoku/
public class TabuleiroVisao extends JPanel {
	private final TabuleiroControle CONTROLE;

	public final Propagador<Posicao> PROPAGADOR = new Propagador<Posicao>();

	private final int MARGIN = 5;
	private final double PIECE_FRAC = 0.9;

	public TabuleiroVisao(TabuleiroControle _controle) {
		super();
		this.CONTROLE = _controle;

		this.addMouseListener(new TabuleiroMouseListener());

		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.add(this);
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.setVisible(true);
	}

	// FUNCOES

	@Override
	public void paintComponent(Graphics g) {
		int tamanho = this.CONTROLE.getTamanho();

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double panelWidth = this.getWidth();
		double panelHeight = this.getHeight();

		g2.setColor(new Color(0.925f, 0.670f, 0.34f)); // light wood
		g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));

		double boardWidth = Math.min(panelWidth, panelHeight) - 2 * this.MARGIN;
		double squareWidth = boardWidth / tamanho;
		double gridWidth = (tamanho - 1) * squareWidth;
		double pieceDiameter = this.PIECE_FRAC * squareWidth;
		boardWidth -= pieceDiameter;
		double xLeft = (panelWidth - boardWidth) / 2 + this.MARGIN;
		double yTop = (panelHeight - boardWidth) / 2 + this.MARGIN;

		g2.setColor(Color.BLACK);
		for (int i = 0; i < tamanho; i++) {
			double offset = i * squareWidth;
			g2.draw(new Line2D.Double(xLeft, yTop + offset, xLeft + gridWidth, yTop + offset));
			g2.draw(new Line2D.Double(xLeft + offset, yTop, xLeft + offset, yTop + gridWidth));
		}

		for (int row = 0; row < tamanho; row++) {
			for (int col = 0; col < tamanho; col++) {
				Peca peca = this.CONTROLE.getPeca(row, col);
				if (!peca.ALINHAMENTO.equals(Alinhamento.Vazio)) {
					Color c = (peca.ALINHAMENTO == Alinhamento.Preto) ? Color.BLACK : Color.WHITE;
					g2.setColor(c);
					double xCenter = xLeft + col * squareWidth;
					double yCenter = yTop + row * squareWidth;
					Ellipse2D.Double circle = new Ellipse2D.Double(xCenter - pieceDiameter / 2, yCenter - pieceDiameter / 2, pieceDiameter, pieceDiameter);
					g2.fill(circle);
					g2.setColor(Color.black);
					g2.draw(circle);
				}
			}
		}
	}

	// SUBCLASSES

	class TabuleiroMouseListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			int tamanho = TabuleiroVisao.this.CONTROLE.getTamanho();

			double panelWidth = TabuleiroVisao.this.getWidth();
			double panelHeight = TabuleiroVisao.this.getHeight();
			double boardWidth = Math.min(panelWidth, panelHeight) - 2 * TabuleiroVisao.this.MARGIN;
			double squareWidth = boardWidth / tamanho;
			double pieceDiameter = TabuleiroVisao.this.PIECE_FRAC * squareWidth;
			double xLeft = (panelWidth - boardWidth) / 2 + TabuleiroVisao.this.MARGIN;
			double yTop = (panelHeight - boardWidth) / 2 + TabuleiroVisao.this.MARGIN;

			int col = (int) Math.round((e.getX() - xLeft) / squareWidth - 0.5);
			int row = (int) Math.round((e.getY() - yTop) / squareWidth - 0.5);

			TabuleiroVisao.this.PROPAGADOR.propagar(new Posicao(row, col));
		}
	}
}
