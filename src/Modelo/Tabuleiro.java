package Modelo;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import Controle.TabuleiroControle;
import Enumeracao.Alinhamento;
import Enumeracao.Sentido;
import Excecao.PosicaoOcupadaException;
import Primitiva.Peca;

public class Tabuleiro {
	protected final TabuleiroControle CONTROLE;
	
	private final HashMap<Integer, Peca> ESTRUTURA;
	private final HashMap<Peca, Point> CONFIGURACAO;
	public final int TAMANHO = 15;
	
	public Tabuleiro(){
		CONTROLE = new TabuleiroControle(this);
		
		ESTRUTURA = new HashMap<Integer, Peca>();
		CONFIGURACAO = new HashMap<Peca, Point>();
	}
	
	private Tabuleiro(Tabuleiro tabuleiro){
		CONTROLE = null;
		
		ESTRUTURA = new HashMap<Integer, Peca>(tabuleiro.ESTRUTURA);
		CONFIGURACAO = new  HashMap<Peca, Point>(tabuleiro.CONFIGURACAO);
	}
	
	//ACESSO
		
	public Peca getPeca(Point posicao) {
		return ESTRUTURA.get(posicao.hashCode());
	}
	
	
	public Point getPosicao(Peca peca) {
		return CONFIGURACAO.get(peca);
	}
	
	public HashSet<Point> getConjuntoDePosicoesDasPecas(Alinhamento alinhamento){
		HashSet<Point> conjuntoDePosicoes = new HashSet<Point>();
		
		for(Peca peca : CONFIGURACAO.keySet()){
			if(peca.ALINHAMENTO == alinhamento)
				conjuntoDePosicoes.add(getPosicao(peca));
		}
		
		return conjuntoDePosicoes;
	}

	//FUNCOES
	
	public void adicionar(Point posicao, Peca peca) throws PosicaoOcupadaException, IndexOutOfBoundsException{
		validarPosicaoExistente(posicao);
		validarPosicaoLivre(posicao);
		
		ESTRUTURA.put(posicao.hashCode(), peca);
		CONFIGURACAO.put(peca, posicao);
		
		CONTROLE.atualizarVisualizacao();
	}
	
	//VALIDACOES

	protected void validarPosicaoExistente(Point posicao) throws IndexOutOfBoundsException{
		if(posicao.x < 0 || posicao.x > TAMANHO || posicao.y < 0 || posicao.y > TAMANHO){
				throw new IndexOutOfBoundsException("A posicao escolhida esta fora das dimensoes do tabuleiro.");
		}
	}

	protected void validarPosicaoLivre(Point posicao) throws PosicaoOcupadaException {
		if(ESTRUTURA.containsKey(posicao.hashCode())){
			throw new PosicaoOcupadaException();
		}
	}
	
	//OUTROS
	
	public Tabuleiro clone(){
		return new Tabuleiro(this);
	}
}
