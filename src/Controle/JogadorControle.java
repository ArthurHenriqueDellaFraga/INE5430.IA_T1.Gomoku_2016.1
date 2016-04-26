package Controle;

import Primitiva.Posicao;

public class JogadorControle {
	public TabuleiroControle TABULEIRO_CONTROLE;
	
	// FUNCOES

	public Posicao coletarPosicaoDoTabuleiro() {
		return TABULEIRO_CONTROLE.coletarPosicaoDoTabuleiro();
	}
}
