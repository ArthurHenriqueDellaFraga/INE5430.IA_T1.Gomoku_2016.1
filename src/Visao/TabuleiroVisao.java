package Visao;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import Controle.TabuleiroControle;
import Enumeracao.Alinhamento;
import Primitiva.Peca;

public class TabuleiroVisao extends JPanel {
	private final TabuleiroControle CONTROLE;
	
	private final int MARGIN = 5;
	private final double PIECE_FRAC = 0.9;
	
	public TabuleiroVisao(TabuleiroControle _controle) {
		super();
		CONTROLE = _controle;
		
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.add(this);
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void paintComponent(Graphics g) {
		int tamanho = CONTROLE.getTamanho();
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double panelWidth = getWidth();
		double panelHeight = getHeight();

		g2.setColor(new Color(0.925f, 0.670f, 0.34f)); // light wood
		g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));

		double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
		double squareWidth = boardWidth / tamanho;
		double gridWidth = (tamanho -1) * squareWidth;
		double pieceDiameter = PIECE_FRAC * squareWidth;
		boardWidth -= pieceDiameter;
		double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
		double yTop = (panelHeight - boardWidth) / 2 + MARGIN;

		g2.setColor(Color.BLACK);
		for (int i = 0; i < tamanho; i++) {
			double offset = i * squareWidth;
			g2.draw(new Line2D.Double(xLeft, yTop + offset, xLeft + gridWidth, yTop + offset));
			g2.draw(new Line2D.Double(xLeft + offset, yTop, xLeft + offset, yTop + gridWidth));
		}

		for (int row = 0; row < tamanho; row++)
			for (int col = 0; col < tamanho; col++) {
				Peca peca = CONTROLE.getPeca(row, col);
				if (peca != null) {
					Color c = (peca.ALINHAMENTO == Alinhamento.Preto) ? Color.BLACK : Color.WHITE;
					g2.setColor(c);
					double xCenter = xLeft + col * squareWidth;
					double yCenter = yTop + row * squareWidth;
					Ellipse2D.Double circle = new Ellipse2D.Double(xCenter - pieceDiameter / 2,
							yCenter - pieceDiameter / 2, pieceDiameter, pieceDiameter);
					g2.fill(circle);
					g2.setColor(Color.black);
					g2.draw(circle);
				}
			}
	}

	public Point coletarPosicaoDoTabuleiro(){
		TabuleiroListener listener = new TabuleiroListener();
		addMouseListener(listener);
		
		while(listener.posicaoPressionada == null){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		removeMouseListener(listener);
		return listener.posicaoPressionada;
	}
	
	// SUBCLASSES
	
	class TabuleiroListener extends MouseAdapter {
		protected Point posicaoPressionada;
		
		public void mouseReleased(MouseEvent e) {
			int tamanho = CONTROLE.getTamanho();
			
			double panelWidth = getWidth();
			double panelHeight = getHeight();
			double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
			double squareWidth = boardWidth / tamanho;
			double pieceDiameter = PIECE_FRAC * squareWidth;
			double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
			double yTop = (panelHeight - boardWidth) / 2 + MARGIN;
			
			int col = (int) Math.round((e.getX() - xLeft) / squareWidth - 0.5);
			int row = (int) Math.round((e.getY() - yTop) / squareWidth - 0.5);
			
			posicaoPressionada = new Point(row, col);
			
			//repaint();
		}
	}
}
