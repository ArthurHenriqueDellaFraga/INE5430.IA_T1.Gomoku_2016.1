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
	
	public Tabuleiro(){
		CONTROLE = new TabuleiroControle(this);
		
		ESTRUTURA = new HashMap<Integer, Peca>();
		CONFIGURACAO = new HashMap<Peca, Posicao>();
	}
	
	private Tabuleiro(Tabuleiro tabuleiro){
		CONTROLE = null;
		
		ESTRUTURA = new HashMap<Integer, Peca>(tabuleiro.ESTRUTURA);
		CONFIGURACAO = new  HashMap<Peca, Posicao>(tabuleiro.CONFIGURACAO);
	}
	
	//ACESSO
		
	public Peca getPeca(Posicao posicao) {
		return ESTRUTURA.get(posicao.hashCode());
	}
	
	
	public Posicao getPosicao(Peca peca) {
		return CONFIGURACAO.get(peca);
	}
	
	public HashSet<Posicao> getConjuntoDePosicoesRelativoAsPecas(Alinhamento alinhamento, int distancia, boolean apenasPosicoesLivres){
		HashSet<Posicao> conjuntoDePosicoes = new HashSet<Posicao>();
		
		for(Peca peca : CONFIGURACAO.keySet()){
			if(peca.ALINHAMENTO == alinhamento){
				Posicao posicao = getPosicao(peca);
				
				if(distancia > 0){
					for(Sentido sentido : Sentido.values()){
						for(Direcao direcao : sentido.DIRECOES){
							
							Posicao posicaoAuxiliar = direcao.transladar(posicao, distancia);
							
							try{
								this.validarPosicaoExistente(posicaoAuxiliar);
								if(apenasPosicoesLivres) this.validarPosicaoLivre(posicaoAuxiliar);
								conjuntoDePosicoes.add(posicaoAuxiliar);
							} 
							catch(IndexOutOfBoundsException e){	}
							catch(PosicaoOcupadaException e) { }
						}
					}
				}
				else{
					conjuntoDePosicoes.add(posicao);
				}
			}
		}
		
		return conjuntoDePosicoes;
	}

	//FUNCOES
	
	public void adicionar(Posicao posicao, Peca peca) throws PosicaoOcupadaException, IndexOutOfBoundsException{
		validarPosicaoExistente(posicao);
		validarPosicaoLivre(posicao);
		
		ESTRUTURA.put(posicao.hashCode(), peca);
		CONFIGURACAO.put(peca, posicao);
		
		CONTROLE.atualizarVisualizacao();
	}
	
	//VALIDACOES

	protected void validarPosicaoExistente(Posicao posicao) throws IndexOutOfBoundsException{
		if(posicao.x < 0 || posicao.x > TAMANHO || posicao.y < 0 || posicao.y > TAMANHO){
				throw new IndexOutOfBoundsException("A posicao escolhida esta fora das dimensoes do tabuleiro.");
		}
	}

	protected void validarPosicaoLivre(Posicao posicao) throws PosicaoOcupadaException {
		if(ESTRUTURA.containsKey(posicao.hashCode())){
			throw new PosicaoOcupadaException(ESTRUTURA.get(posicao.hashCode()));
		}
	}
	
	//OUTROS
	
	public Tabuleiro clone(){
		return new Tabuleiro(this);
	}
	
	public boolean isEmpty(){
		return CONFIGURACAO.isEmpty();
	}
}
