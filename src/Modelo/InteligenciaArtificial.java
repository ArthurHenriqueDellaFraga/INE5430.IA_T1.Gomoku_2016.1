package Modelo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import Enumeracao.Alinhamento;
import Enumeracao.Sentido;
import Enumeracao.Sentido.Direcao;
import Modelo.Gomoku.ContextoDoJogo;
import Primitiva.Jogada;
import Primitiva.Peca;

public class InteligenciaArtificial extends Jogador{
		
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
						refletirSobreAsPossibilidades(new NodoMiniMax(contextoDoJogo), Gomoku.TAMANHO_SEQUENCIA_VITORIA).melhorJogada,
						gerarPeca()
				);
			}
			catch(NullPointerException e){
				System.out.println("ERRO: Jogador " + IDENTIFICACAO + " não introduzido a um jogo");
				e.printStackTrace();
			}
			return null;
		}
		
		public NodoMiniMax refletirSobreAsPossibilidades(NodoMiniMax nodo, int intensidade){
			ArrayList<Point> listaDePosicoes = new ArrayList<Point>(){{
				for(int i=1; i < Gomoku.TAMANHO_SEQUENCIA_VITORIA; i++){
					addAll(identificarPosicoesProximas(nodo.CONTEXTO_DO_JOGO.TABULEIRO, i));
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
				if(nodo.podaAlfaBeta[0] < nodo.podaAlfaBeta[1] && intensidade > 0){
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
										if(intensidade == 1){
											funcaoDeUtilidade();
										}
									}},
									intensidade -1
							)
					);
				}
				else{
					break;
				}
			}
			return nodo;
		}
		
		public HashSet<Point> identificarPosicoesProximas(Tabuleiro tabuleiro, int distancia){
			HashSet<Point> posicoesProximas = new HashSet<Point>();
			
			for(Point posicao : tabuleiro.getConjuntoDePosicoesDasPecas(ALINHAMENTO)){
				
				for(Sentido sentido : Sentido.values()){
					for(Direcao direcao : sentido.DIRECOES){
						
						Point posicaoAuxiliar = new Point(
							posicao.x + direcao.REFERENCIA_CARTESIANA.x * distancia,
							posicao.y + direcao.REFERENCIA_CARTESIANA.y * distancia
						);
						
						try{
							tabuleiro.validarPosicaoExistente(posicaoAuxiliar);
							tabuleiro.validarPosicaoLivre(posicaoAuxiliar);
						} 
						catch(Exception e){
							break;
						}
						
						posicoesProximas.add(posicaoAuxiliar);
					}
				}
			}
			
			return posicoesProximas;
		}
		
		//SUBCLASSES
		
		private class NodoMiniMax {
			public final ArrayList<NodoMiniMax> REFERENCIAS = new ArrayList<NodoMiniMax>();
			
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
				podaAlfaBeta = _podaAlfaBeta;
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
				REFERENCIAS.add(nodo);
			}
			
			//completar
			protected void funcaoDeUtilidade() {
								
				for(Point posicao : CONTEXTO_DO_JOGO.TABULEIRO.getConjuntoDePosicoesDasPecas(ALINHAMENTO)){
					 
					for(Sentido sentido : Sentido.values()){
						for(Direcao direcao : sentido.DIRECOES){
							int modificadorPecasAliadas = 1;
							
							for(int i=1; i < Gomoku.TAMANHO_SEQUENCIA_VITORIA; i++){
								int utilidadeDaPosicao = Gomoku.TAMANHO_SEQUENCIA_VITORIA - (i-1);
								
								Point posicaoAuxiliar = new Point(
									posicao.x + direcao.REFERENCIA_CARTESIANA.x * i,
									posicao.x + direcao.REFERENCIA_CARTESIANA.y * i
								);
								
								Peca pecaAuxiliar = CONTEXTO_DO_JOGO.TABULEIRO.getPeca(posicao);
								if(pecaAuxiliar != null){
									if(pecaAuxiliar.ALINHAMENTO == ALINHAMENTO)
										modificadorPecasAliadas *= utilidadeDaPosicao;
									else
										break;
								}			
								
								utilidadeDoJogo += utilidadeDaPosicao * modificadorPecasAliadas;
							}
						}
					}
				}
				podaAlfaBeta[MEU_TURNO ? 0 : 1] = utilidadeDoJogo;
			}
		}
}

