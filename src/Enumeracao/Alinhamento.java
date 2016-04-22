package Enumeracao;

public enum Alinhamento {
	Nenhum,
	Branco,
	Preto;
	
	//FUNCOES
		
	public Alinhamento oposto(){
		switch(this){
			case Nenhum:	return Nenhum;
			case Branco:	return Preto;
			case Preto:		return Branco;
		}
		return null;
	}
}
