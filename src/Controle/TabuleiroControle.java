package Controle;

import Modelo.Tabuleiro;
import Primitiva.Peca;
import Primitiva.Posicao;
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
		return TABULEIRO.getPeca(new Posicao(linha, coluna));
	}

	public Posicao coletarPosicaoDoTabuleiro() {
		return VISAO.PROPAGADOR.sintonizar();
	}

	public void atualizarVisualizacao() {
		VISAO.repaint();
		
	}
	
	//SUBCLASSES
}
