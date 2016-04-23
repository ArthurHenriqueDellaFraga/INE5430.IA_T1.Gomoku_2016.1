package Enumeracao;

public enum Alinhamento {
	Branco,
	Preto;
	
	//FUNCOES
		
	public Alinhamento oposto(){
		switch(this){
			case Branco:	return Preto;
			case Preto:		return Branco;
		}
		return null;
	}
}
