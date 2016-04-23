package Modelo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import Enumeracao.Alinhamento;
import Enumeracao.Sentido;
import Enumeracao.Sentido.Direcao;
import Excecao.PosicaoOcupadaException;
import Modelo.Gomoku.ContextoDoJogo;
import Primitiva.Jogada;
import Primitiva.Peca;

public class InteligenciaArtificial extends Jogador{
	
		public final int PROFUNDIDADE = 5;
		
		public InteligenciaArtificial(String _identificacao, Alinhamento _alinhamento){
			super(_identificacao, _alinhamento);
		}
		
		//FUNCOES
		
		protected void ingressarJogo(Gomoku gomoku){
			//estrategia = new NodoMiniMax(gomoku.getContextoDoJogo());
		}
		
		public Jogada definirJogada(ContextoDoJogo contextoDoJogo){
			try{
				return new Jogada(
						this,
						refletirSobreAsPossibilidades(new NodoMiniMax(contextoDoJogo), PROFUNDIDADE).melhorJogada,
						gerarPeca()
				);
			}
			catch(NullPointerException e){
				System.out.println("ERRO: Jogador " + IDENTIFICACAO + " não introduzido a um jogo");
				e.printStackTrace();
			}
			return null;
		}
		
		public NodoMiniMax refletirSobreAsPossibilidades(NodoMiniMax nodo, int iteracao){
			
			ArrayList<Point> listaDePosicoes = new ArrayList<Point>(){{
				for(int distancia=1; distancia < 2; distancia++){
					addAll(nodo.CONTEXTO_DO_JOGO.TABULEIRO.getConjuntoDePosicoesRelativoAsPecas(nodo.MEU_TURNO ? ALINHAMENTO : ALINHAMENTO.oposto(), distancia, true));
				}
			}};
			
			if(listaDePosicoes.isEmpty()){
				nodo.melhorJogada = new Point(
						(int) (1 + Math.random()) * (nodo.CONTEXTO_DO_JOGO.TABULEIRO.TAMANHO / 3), 
						(int) (1 + Math.random()) * (nodo.CONTEXTO_DO_JOGO.TABULEIRO.TAMANHO / 3)
				);
				return nodo;
			}
			
			for (Point posicao : listaDePosicoes) {
				if(nodo.podaAlfaBeta[0] < nodo.podaAlfaBeta[1] && iteracao > 0){
					nodo.adicionarReferencia(
							refletirSobreAsPossibilidades(
									new NodoMiniMax(
											posicao,
											new ContextoDoJogo(
													nodo.CONTEXTO_DO_JOGO.TABULEIRO.clone(),
													nodo.CONTEXTO_DO_JOGO.TURNO + 1,
													nodo.CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ.oposto()
											){{
												try{
													TABULEIRO.adicionar(posicao, new Peca(ALINHAMENTO_DA_VEZ));
												} 
												catch(Exception e){ }
											}},
											nodo.podaAlfaBeta
									){{
										if(iteracao == 1){
											funcaoDeUtilidade();
										}
									}},
									iteracao -1
							)
					);
				}
				else{
					break;
				}
			}
			return nodo;
		}
		
		//SUBCLASSES
		
		private class NodoMiniMax {
			public final HashMap<Integer, NodoMiniMax> REFERENCIAS = new HashMap<Integer, NodoMiniMax>();
			
			public final Point ULTIMA_POSICAO_JOGADA;
			public final ContextoDoJogo CONTEXTO_DO_JOGO;
			public final boolean MEU_TURNO;
			
			public int[] podaAlfaBeta = { Integer.MIN_VALUE, Integer.MAX_VALUE };
			public int utilidadeDoJogo;
			public Point melhorJogada;
			
			public NodoMiniMax(ContextoDoJogo _contextoDoJogo){
				ULTIMA_POSICAO_JOGADA = null;
				CONTEXTO_DO_JOGO = _contextoDoJogo;
				MEU_TURNO = CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == ALINHAMENTO;
				utilidadeDoJogo = MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			}
			
			public NodoMiniMax(Point _ultimaPosicaoJogada, ContextoDoJogo _contextoDoJogo){
				ULTIMA_POSICAO_JOGADA = _ultimaPosicaoJogada;
				CONTEXTO_DO_JOGO = _contextoDoJogo;
				MEU_TURNO = CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == ALINHAMENTO;
				utilidadeDoJogo = MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			}
			
			public NodoMiniMax(Point _ultimaPosicaoJogada, ContextoDoJogo _contextoDoJogo, int[] _podaAlfaBeta){
				ULTIMA_POSICAO_JOGADA = _ultimaPosicaoJogada;
				CONTEXTO_DO_JOGO = _contextoDoJogo;
				MEU_TURNO = CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == ALINHAMENTO;
				podaAlfaBeta = _podaAlfaBeta.clone();
				utilidadeDoJogo = MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			}
			
			//FUNCOES
			
			public void adicionarReferencia(NodoMiniMax nodo){
				if(MEU_TURNO && nodo.utilidadeDoJogo > utilidadeDoJogo){
					utilidadeDoJogo = nodo.utilidadeDoJogo;
					podaAlfaBeta[0] = utilidadeDoJogo;
					melhorJogada = nodo.ULTIMA_POSICAO_JOGADA;
				}
				else if(!MEU_TURNO && nodo.utilidadeDoJogo < utilidadeDoJogo){
					utilidadeDoJogo = nodo.utilidadeDoJogo;
					podaAlfaBeta[1] = utilidadeDoJogo;
					melhorJogada = nodo.ULTIMA_POSICAO_JOGADA;
				}
				REFERENCIAS.put(nodo.ULTIMA_POSICAO_JOGADA.hashCode(), nodo);
			}
			
			//completar
			protected void funcaoDeUtilidade() {
				utilidadeDoJogo = 0;
				
				for(Alinhamento alinhamento : Alinhamento.values()){
					int utilidadeDoAlinhamento = 0;
					
					for(Point posicao : CONTEXTO_DO_JOGO.TABULEIRO.getConjuntoDePosicoesRelativoAsPecas(alinhamento, 0, false)){
						 
						for(Sentido sentido : Sentido.values()){
							for(Direcao direcao : sentido.DIRECOES){
								int modificadorPecasAliadas = 1;
								
								for(int distancia=1; distancia < PROFUNDIDADE; distancia++){
									int utilidadeDaPosicao = PROFUNDIDADE - (distancia-1);
									
									Point posicaoAuxiliar = direcao.transladar(posicao, distancia);
									
									try{
										CONTEXTO_DO_JOGO.TABULEIRO.validarPosicaoExistente(posicaoAuxiliar);
										CONTEXTO_DO_JOGO.TABULEIRO.validarPosicaoLivre(posicaoAuxiliar);
									}
									catch(IndexOutOfBoundsException e){
										break;
									}
									catch(PosicaoOcupadaException e){
										if(e.PECA.ALINHAMENTO == alinhamento){
											modificadorPecasAliadas *= utilidadeDaPosicao;
										}
										else{
											break;
										}
									}
									
									utilidadeDoAlinhamento += utilidadeDaPosicao * modificadorPecasAliadas;
								}
							}
						}
					}
					if(alinhamento == ALINHAMENTO){
						utilidadeDoJogo += utilidadeDoAlinhamento * PROFUNDIDADE;
					}
					else{
						utilidadeDoJogo -= utilidadeDoAlinhamento;
					}						
				}
				podaAlfaBeta[MEU_TURNO ? 0 : 1] = utilidadeDoJogo;
			}
		}
}

