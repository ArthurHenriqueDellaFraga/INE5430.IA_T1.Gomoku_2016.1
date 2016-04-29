package Modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import Enumeracao.Alinhamento;
import Enumeracao.Sentido;
import Enumeracao.Sentido.Direcao;
import Excecao.PosicaoOcupadaException;
import Modelo.Gomoku.ContextoDoJogo;
import Primitiva.Jogada;
import Primitiva.Peca;
import Primitiva.Posicao;

public class InteligenciaArtificial extends Jogador {

	public final int PROFUNDIDADE = 0;

	public InteligenciaArtificial(String _identificacao, Alinhamento _alinhamento) {
		super(_identificacao, _alinhamento);
	}

	// FUNCOES

	@Override
	protected void ingressarJogo(Gomoku gomoku) {
		// estrategia = new NodoMiniMax(gomoku.getContextoDoJogo());
	}

	@Override
	public Jogada definirJogada(ContextoDoJogo contextoDoJogo) {
		try {
			NodoMiniMax nodo = this.refletirSobreAsPossibilidades(new NodoMiniMax(contextoDoJogo), this.PROFUNDIDADE);
			return new Jogada(this, nodo.melhorJogada, this.gerarPeca());
		} catch (NullPointerException e) {
			System.out.println("ERRO: Jogador " + this.IDENTIFICACAO + " n�o introduzido a um jogo");
			e.printStackTrace();
		}
		return null;
	}

	public NodoMiniMax refletirSobreAsPossibilidades(final NodoMiniMax nodo, final int profundidade) {

		LinkedHashSet<Posicao> listaDePosicoes = new LinkedHashSet<Posicao>() {
			{
				for (int distancia = 1; distancia <= 2; distancia++) {
					this.addAll(nodo.CONTEXTO_DO_JOGO.TABULEIRO
							.getConjuntoDePosicoesRelativoAsPecas(nodo.MEU_TURNO ? InteligenciaArtificial.this.ALINHAMENTO : InteligenciaArtificial.this.ALINHAMENTO.oposto(), distancia, true));
				}

				for (int distancia = 1; distancia <= 1; distancia++) {
					this.addAll(nodo.CONTEXTO_DO_JOGO.TABULEIRO
							.getConjuntoDePosicoesRelativoAsPecas(nodo.MEU_TURNO ? InteligenciaArtificial.this.ALINHAMENTO.oposto() : InteligenciaArtificial.this.ALINHAMENTO, distancia, true));
				}
			}
		};

		if (nodo.CONTEXTO_DO_JOGO.TABULEIRO.getPeca(new Posicao(7, 7)).ALINHAMENTO.equals(Alinhamento.Vazio)) {
			nodo.melhorJogada = new Posicao(7, 7);
			return nodo;
		}

		for (final Posicao posicao : listaDePosicoes) {
			if (nodo.podaAlfaBeta[0] < nodo.podaAlfaBeta[1] && profundidade >= 0) {
				nodo.adicionarReferencia(this.refletirSobreAsPossibilidades(
						new NodoMiniMax(posicao, new ContextoDoJogo(nodo.CONTEXTO_DO_JOGO.TABULEIRO.clone(), nodo.CONTEXTO_DO_JOGO.TURNO + 1, nodo.CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ.oposto()) {
							{
								try {
									this.TABULEIRO.adicionar(posicao, new Peca(this.ALINHAMENTO_DA_VEZ.oposto()));
								} catch (Exception e) {
								}
							}
						}, nodo.podaAlfaBeta) {
							{
								if (profundidade == 0) {
									this.funcaoDeUtilidade();
								}
							}
						}, profundidade - 1));
			} else {
				break;
			}
		}
		return nodo;
	}

	// SUBCLASSES

	private class NodoMiniMax {
		public final HashMap<Integer, NodoMiniMax> REFERENCIAS = new HashMap<Integer, NodoMiniMax>();

		public final Posicao ULTIMA_POSICAO_JOGADA;
		public final ContextoDoJogo CONTEXTO_DO_JOGO;
		public final boolean MEU_TURNO;

		public int[] podaAlfaBeta = { Integer.MIN_VALUE, Integer.MAX_VALUE };
		public int utilidadeDoJogo;
		public int valorInimigo;
		public int meuValor;
		public Posicao melhorJogada;

		public NodoMiniMax(ContextoDoJogo _contextoDoJogo) {
			this.ULTIMA_POSICAO_JOGADA = null;
			this.CONTEXTO_DO_JOGO = _contextoDoJogo;
			this.MEU_TURNO = this.CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == InteligenciaArtificial.this.ALINHAMENTO;
			this.utilidadeDoJogo = this.MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}

		public NodoMiniMax(Posicao _ultimaPosicaoJogada, ContextoDoJogo _contextoDoJogo) {
			this.ULTIMA_POSICAO_JOGADA = _ultimaPosicaoJogada;
			this.CONTEXTO_DO_JOGO = _contextoDoJogo;
			this.MEU_TURNO = this.CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == InteligenciaArtificial.this.ALINHAMENTO;
			this.utilidadeDoJogo = this.MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}

		public NodoMiniMax(Posicao _ultimaPosicaoJogada, ContextoDoJogo _contextoDoJogo, int[] _podaAlfaBeta) {
			this.ULTIMA_POSICAO_JOGADA = _ultimaPosicaoJogada;
			this.CONTEXTO_DO_JOGO = _contextoDoJogo;
			this.MEU_TURNO = this.CONTEXTO_DO_JOGO.ALINHAMENTO_DA_VEZ == InteligenciaArtificial.this.ALINHAMENTO;
			this.podaAlfaBeta = _podaAlfaBeta.clone();
			this.utilidadeDoJogo = this.MEU_TURNO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}

		// FUNCOES

		public void adicionarReferencia(NodoMiniMax nodo) {
			if (this.MEU_TURNO && nodo.utilidadeDoJogo > this.utilidadeDoJogo) {
				this.utilidadeDoJogo = nodo.utilidadeDoJogo;
				this.podaAlfaBeta[0] = this.utilidadeDoJogo;
				this.melhorJogada = nodo.ULTIMA_POSICAO_JOGADA;
			} else if (!this.MEU_TURNO && nodo.utilidadeDoJogo < this.utilidadeDoJogo) {
				this.utilidadeDoJogo = nodo.utilidadeDoJogo;
				this.podaAlfaBeta[1] = this.utilidadeDoJogo;
				this.melhorJogada = nodo.ULTIMA_POSICAO_JOGADA;
			}
			this.REFERENCIAS.put(nodo.ULTIMA_POSICAO_JOGADA.hashCode(), nodo);
		}

		protected void funcaoDeUtilidade() {
			this.utilidadeDoJogo = 0;

			for (Alinhamento alinhamento : Alinhamento.values()) {
				int utilidadeDoAlinhamento = 0;

				for (Sentido sentido : Sentido.values()) {
					HashSet<Posicao> jaVisitadas = new HashSet<>();

					HashSet<Posicao> posicoes = this.CONTEXTO_DO_JOGO.TABULEIRO.getConjuntoDePosicoesRelativoAsPecas(alinhamento, 0, false);
					for (Posicao posicao : posicoes) {
						if (!jaVisitadas.contains(posicao)) {
							jaVisitadas.add(posicao);
							Sequencia sequencia = new Sequencia();

							for (Direcao direcao : sentido.DIRECOES) {

								for (int distancia = 1; distancia < Gomoku.TAMANHO_SEQUENCIA_VITORIA; distancia++) {

									Posicao posicaoAuxiliar = direcao.transladar(posicao, distancia);

									try {
										this.CONTEXTO_DO_JOGO.TABULEIRO.validarPosicaoExistente(posicaoAuxiliar);
										this.CONTEXTO_DO_JOGO.TABULEIRO.validarPosicaoLivre(posicaoAuxiliar);
										sequencia.incrementLadosAbertos();
										break;
									} catch (IndexOutOfBoundsException e) {
										break;
									} catch (PosicaoOcupadaException e) {
										if (e.PECA.ALINHAMENTO == alinhamento) {
											sequencia.incrementTamanho();
											jaVisitadas.add(posicaoAuxiliar);
										} else {
											sequencia.addBloqueante(posicaoAuxiliar);
											break;
										}
									}
								}
							}
							if (sequencia.contemPosicaoBloqueante(this.ULTIMA_POSICAO_JOGADA) && sequencia.getTamanho() >= 3) {
								utilidadeDoAlinhamento -= 5 * sequencia.getValorSequencia();
							}
							utilidadeDoAlinhamento += sequencia.getValor();
						}
					}

				}
				if (alinhamento == InteligenciaArtificial.this.ALINHAMENTO) {
					this.meuValor += utilidadeDoAlinhamento;
				} else {
					this.valorInimigo += utilidadeDoAlinhamento;
				}
			}
			this.utilidadeDoJogo = this.meuValor - this.valorInimigo;
			this.podaAlfaBeta[this.MEU_TURNO ? 0 : 1] = this.utilidadeDoJogo;
		}
	}
}
