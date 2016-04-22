package Modelo;

import Enumeracao.Alinhamento;
import Modelo.Gomoku.ContextoDoJogo;
import Primitiva.*;

public class Humano extends Jogador{
	
	public Humano(String _identificacao, Alinhamento _alinhamento){
		super(_identificacao, _alinhamento);
	}
	
	//FUNCOES
	
	protected void ingressarJogo(Gomoku gomoku){
		CONTROLE.TABULEIRO_CONTROLE = gomoku.TABULEIRO.CONTROLE;
	}
	
	public Jogada definirJogada(ContextoDoJogo contextoDoJogo){
		try{
			return new Jogada(
					this,
					CONTROLE.coletarPosicaoDoTabuleiro(),
					gerarPeca()
			);
		}
		catch(NullPointerException e){
			System.out.println("ERRO: Jogador " + IDENTIFICACAO + " não introduzido a um jogo");
		}
		return null;
	}
	
}