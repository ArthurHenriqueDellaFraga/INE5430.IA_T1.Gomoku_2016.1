package Excecao;

import Modelo.Jogador;

public class VitoriaAtingidaException extends Exception {
	
	public final Jogador JOGADOR;
	
	public VitoriaAtingidaException(Jogador _jogador){
		super("Vitoria atingida por " + _jogador.IDENTIFICACAO + " com a cor " + _jogador.ALINHAMENTO);
		JOGADOR = _jogador;
	}
}
