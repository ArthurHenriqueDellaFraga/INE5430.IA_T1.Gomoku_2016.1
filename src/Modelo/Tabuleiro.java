package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Controle.TabuleiroControle;
import Enumeracao.Alinhamento;
import Enumeracao.Sentido;
import Enumeracao.Sentido.Direcao;
import Excecao.PosicaoOcupadaException;
import Primitiva.Peca;
import Primitiva.Posicao;

public class Tabuleiro {
	protected final TabuleiroControle CONTROLE;

	private final HashMap<Integer, Peca> ESTRUTURA;
	private final HashMap<Peca, Posicao> CONFIGURACAO;
	public final int TAMANHO = 15;

	public Tabuleiro() {
		this.CONTROLE = new TabuleiroControle(this);

		this.ESTRUTURA = new HashMap<Integer, Peca>();
		this.CONFIGURACAO = new HashMap<Peca, Posicao>();
	}

	private Tabuleiro(Tabuleiro tabuleiro) {
		this.CONTROLE = null;

		this.ESTRUTURA = new HashMap<Integer, Peca>(tabuleiro.ESTRUTURA);
		this.CONFIGURACAO = new HashMap<Peca, Posicao>(tabuleiro.CONFIGURACAO);
	}

	// ACESSO

	public Peca getPeca(Posicao posicao) {
		Peca peca = this.ESTRUTURA.get(posicao.hashCode());
		if (peca == null) {
			return new Peca(Alinhamento.Vazio);
		}
		return peca;
	}

	public Posicao getPosicao(Peca peca) {
		return this.CONFIGURACAO.get(peca);
	}

	public HashSet<Posicao> getConjuntoDePosicoesRelativoAsPecas(Alinhamento alinhamento, int distancia, boolean apenasPosicoesLivres) {
		HashSet<Posicao> conjuntoDePosicoes = new HashSet<Posicao>();

		for (Peca peca : this.CONFIGURACAO.keySet()) {
			if (peca.ALINHAMENTO == alinhamento) {
				Posicao posicao = this.getPosicao(peca);

				if (distancia > 0) {
					for (Sentido sentido : Sentido.values()) {
						for (Direcao direcao : sentido.DIRECOES) {

							Posicao posicaoAuxiliar = direcao.transladar(posicao, distancia);

							try {
								this.validarPosicaoExistente(posicaoAuxiliar);
								if (apenasPosicoesLivres) {
									this.validarPosicaoLivre(posicaoAuxiliar);
								}
								conjuntoDePosicoes.add(posicaoAuxiliar);
							} catch (IndexOutOfBoundsException e) {
							} catch (PosicaoOcupadaException e) {
							}
						}
					}
				} else {
					conjuntoDePosicoes.add(posicao);
				}
			}
		}

		return conjuntoDePosicoes;
	}

	// FUNCOES

	public void adicionar(Posicao posicao, Peca peca) throws PosicaoOcupadaException, IndexOutOfBoundsException {
		this.validarPosicaoExistente(posicao);
		this.validarPosicaoLivre(posicao);

		this.ESTRUTURA.put(posicao.hashCode(), peca);
		this.CONFIGURACAO.put(peca, posicao);
		this.CONTROLE.atualizarVisualizacao();
	}

	// VALIDACOES

	protected void validarPosicaoExistente(Posicao posicao) throws IndexOutOfBoundsException {
		if (posicao.x < 0 || posicao.x > this.TAMANHO || posicao.y < 0 || posicao.y > this.TAMANHO) {
			throw new IndexOutOfBoundsException("A posicao escolhida esta fora das dimensoes do tabuleiro.");
		}
	}

	protected void validarPosicaoLivre(Posicao posicao) throws PosicaoOcupadaException {
		if (this.ESTRUTURA.containsKey(posicao.hashCode())) {
			throw new PosicaoOcupadaException(this.ESTRUTURA.get(posicao.hashCode()));
		}
	}

	public Peca getPecaVizinhaNaDirecao(Peca peca, Direcao direcao) {
		Posicao posicao = this.getPosicao(peca);
		posicao.translate(direcao.REFERENCIA_CARTESIANA.x, direcao.REFERENCIA_CARTESIANA.y);
		return this.getPeca(posicao);
	}

	// OUTROS

	@Override
	public Tabuleiro clone() {
		return new Tabuleiro(this);
	}

	public boolean isEmpty() {
		return this.CONFIGURACAO.isEmpty();
	}
}
