package Primitiva;

import Modelo.Jogador;

public class Jogada {
	public final Jogador JOGADOR;
	public final Posicao POSICAO;
	public final Peca PECA;
	
	public Jogada(Jogador _jogador, Posicao _posicao, Peca _peca){
		JOGADOR = _jogador;
		POSICAO = _posicao;
		PECA = _peca;
	}
}
