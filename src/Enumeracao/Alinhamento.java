package Enumeracao;

public enum Alinhamento {
	Nenhum,
	Branco,
	Preto;
	
	//FUNCOES
		
	public static Alinhamento alinhamentoOposto(Alinhamento alinhamento){
		switch(alinhamento){
			case Nenhum:	return Nenhum;
			case Branco:	return Preto;
			case Preto:		return Branco;
		}
		return null;
	}
}
