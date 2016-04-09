package Primitiva;

import java.awt.Point;

import Modelo.Jogador;

public class Jogada {
	public final Jogador JOGADOR;
	public final Point POSICAO;
	public final Peca PECA;
	
	public Jogada(Jogador _jogador, Point _posicao, Peca _peca){
		JOGADOR = _jogador;
		POSICAO = _posicao;
		PECA = _peca;
	}
}
