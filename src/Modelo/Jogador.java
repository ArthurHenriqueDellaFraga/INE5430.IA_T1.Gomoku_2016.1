package Modelo;

import Controle.JogadorControle;
import Enumeracao.Alinhamento;
import Modelo.Gomoku.ContextoDoJogo;
import Primitiva.Jogada;
import Primitiva.Peca;

public abstract class Jogador {
	protected final JogadorControle CONTROLE = new JogadorControle();
	
	public final String IDENTIFICACAO;
	public final Alinhamento ALINHAMENTO;

	protected Jogador(String _identificador, Alinhamento _alinhamento) {
		IDENTIFICACAO = _identificador;
		ALINHAMENTO = _alinhamento;
	}

	// FUNCOES
	
	protected abstract void ingressarJogo(Gomoku gomoku);

	public abstract Jogada definirJogada(ContextoDoJogo contextoDoJogo);

	protected Peca gerarPeca() {
		return new Peca(ALINHAMENTO);
	}
}
