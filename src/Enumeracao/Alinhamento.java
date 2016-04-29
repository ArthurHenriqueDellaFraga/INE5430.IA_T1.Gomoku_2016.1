package Enumeracao;

public enum Alinhamento {
	Branco, Preto, Vazio;

	// FUNCOES

	public Alinhamento oposto() {
		switch (this) {
		case Branco:
			return Preto;
		case Preto:
			return Branco;
		}
		return null;
	}
}
