package Evento;

public class PosicaoOcupadaException extends Exception {
	
	public PosicaoOcupadaException() {
		super("A posicao escolhida esta ocupada. Selecione outra.");
	}
}
