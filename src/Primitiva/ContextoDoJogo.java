package Primitiva;

import Modelo.Tabuleiro;

public class ContextoDoJogo {
	public final Tabuleiro TABULEIRO;
	public final int TURNO;

	public ContextoDoJogo(Tabuleiro _tabuleiro, int _turno){
		TABULEIRO = _tabuleiro;
		TURNO = _turno;
	}

	private ContextoDoJogo(ContextoDoJogo contextoDoJogo){
		TABULEIRO = contextoDoJogo.TABULEIRO.clone();
		TURNO = contextoDoJogo.TURNO;
	}

	// OUTROS

	public ContextoDoJogo clone() {
		return new ContextoDoJogo(this);
	}
}
