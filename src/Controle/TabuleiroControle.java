package Controle;

import Modelo.Tabuleiro;
import Primitiva.Peca;
import Primitiva.Posicao;
import Visao.TabuleiroVisao;

public class TabuleiroControle {
	private final Tabuleiro TABULEIRO;
	private TabuleiroVisao VISAO;

	public TabuleiroControle(Tabuleiro _tabuleiro) {
		this.TABULEIRO = _tabuleiro;
		this.VISAO = new TabuleiroVisao(this);
	}

	// ACESSO

	public int getTamanho() {
		return this.TABULEIRO.TAMANHO;
	}

	// FUNCOES

	public Peca getPeca(int linha, int coluna) {
		return this.TABULEIRO.getPeca(new Posicao(linha, coluna));
	}

	public Posicao coletarPosicaoDoTabuleiro() {
		return this.VISAO.PROPAGADOR.sintonizar();
	}

	public void atualizarVisualizacao() {
		this.VISAO.repaint();

	}

	// SUBCLASSES
}
