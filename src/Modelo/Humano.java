package Modelo;

import Enumeracao.Alinhamento;
import Primitiva.*;

public class Humano extends Jogador{
	
	public Humano(String _identificacao, Alinhamento _alinhamento){
		super(_identificacao, _alinhamento);
	}
	
	//FUNCOES
	
	public Jogada definirJogada(ContextoDoJogo contextoDoJogo){
		try{
			return new Jogada(
					this,
					CONTROLE.coletarPosicaoDoTabuleiro(),
					gerarPeca()
			);
		}
		catch(NullPointerException e){
			System.out.println("ERRO: Jogador não introduzido a um jogo");
		}
		return null;
	}
	
}