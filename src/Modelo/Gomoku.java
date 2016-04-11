package Modelo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import Enumeracao.ConfiguracaoDeJogo;
import Enumeracao.Sentido;
import Enumeracao.Sentido.*;
import Excecao.PosicaoOcupadaException;
import Excecao.VitoriaAtingidaException;
import Primitiva.*;

public class Gomoku {
	public final Tabuleiro TABULEIRO;
	public final ArrayList<Jogador> LISTA_DE_JOGADORES;
	public final HashMap<Jogada, ContextoDoJogo> HISTORICO_DE_JOGADAS;
	
	public final int TAMANHO_SEQUENCIA_VITORIA = 5;
	
	public Gomoku(ConfiguracaoDeJogo configuracao){
		TABULEIRO = new Tabuleiro();
		LISTA_DE_JOGADORES = new ArrayList<Jogador>(configuracao.LISTA_DE_JOGADORES);
			for(Jogador jogador : LISTA_DE_JOGADORES){
				jogador.ingressarJogo(this);
			}
		HISTORICO_DE_JOGADAS = new HashMap<Jogada, ContextoDoJogo>();
	}
	
	//ACESSO
	
	public Jogador getJogadorDaVez(){
		return LISTA_DE_JOGADORES.get(HISTORICO_DE_JOGADAS.size() % LISTA_DE_JOGADORES.size());
	}
	
	public ContextoDoJogo getContextoDoJogo() {
		return new ContextoDoJogo(TABULEIRO.clone(), HISTORICO_DE_JOGADAS.size());
	}
	
	//FUNCOES
	
	public void iniciar() throws VitoriaAtingidaException{
		while(true){
			Jogada jogada = getJogadorDaVez().definirJogada(getContextoDoJogo());
			
			try{
				TABULEIRO.adicionar(jogada.POSICAO, jogada.PECA);
				HISTORICO_DE_JOGADAS.put(jogada, getContextoDoJogo());
				validarVitoria(jogada);					
			}
			catch(PosicaoOcupadaException e){
				System.out.println("Posicao Ocupada");
			}
		}
	}
	
	//VALIDACOES
	
	private void validarVitoria(Jogada jogada) throws VitoriaAtingidaException{		
		for (Sentido sentido : Sentido.values()){
			int tamanhoSequencia = 1;
			for (Direcao direcao : sentido.DIRECOES) {
				Point posicaoAtual = (Point) jogada.POSICAO.clone();
				
				for(int i = 0; i < TAMANHO_SEQUENCIA_VITORIA; i++){
					posicaoAtual.translate(
						direcao.REFERENCIA_CARTESIANA.x,
						direcao.REFERENCIA_CARTESIANA.y
					);
					
					if(TABULEIRO.getPeca(posicaoAtual) != null && TABULEIRO.getPeca(posicaoAtual).ALINHAMENTO == jogada.PECA.ALINHAMENTO){
						tamanhoSequencia += 1;
					}
					else{
						break;
					}
				}
			}
			
			if(tamanhoSequencia >= TAMANHO_SEQUENCIA_VITORIA){
				throw new VitoriaAtingidaException(jogada.JOGADOR);
			}
		}
		
	}
}
