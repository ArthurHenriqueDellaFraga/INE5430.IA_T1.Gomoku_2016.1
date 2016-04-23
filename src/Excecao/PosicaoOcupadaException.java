package Excecao;

import Primitiva.Peca;

public class PosicaoOcupadaException extends Exception {
	public final Peca PECA;
	
	public PosicaoOcupadaException(Peca _peca) {
		super("A posicao escolhida esta ocupada. Selecione outra.");
		PECA = _peca;
	}
}
