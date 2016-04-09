package Controle;

import java.awt.Point;

import Modelo.Tabuleiro;
import Primitiva.Peca;
import Visao.TabuleiroVisao;

public class TabuleiroControle {
	private final Tabuleiro TABULEIRO;
	private TabuleiroVisao VISAO;

	public TabuleiroControle(Tabuleiro _tabuleiro){
		TABULEIRO = _tabuleiro;
		VISAO = new TabuleiroVisao(this);
	}
	
	//ACESSO

	public int getTamanho() {
		return TABULEIRO.TAMANHO;
	}
	
	//FUNCOES

	public Peca getPeca(int linha, int coluna) {
		return TABULEIRO.getPeca(new Point(linha, coluna));
	}

	public Point coletarPosicaoDoTabuleiro() {
		return VISAO.coletarPosicaoDoTabuleiro();
	}

	public void atualizarVisualizacao() {
		VISAO.repaint();
		
	}
}
