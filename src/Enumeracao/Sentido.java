package Enumeracao;

import java.util.ArrayList;

import Primitiva.Posicao;

public enum Sentido {
	Vertical(new Direcao[]{ Direcao.Norte, Direcao.Sul }),
	DiagonalPrincipal(new Direcao[]{ Direcao.Nordeste, Direcao.Sudoeste }),
	Horizontal(new Direcao[]{ Direcao.Leste, Direcao.Oeste }),
	DiagonalSecundaria(new Direcao[]{ Direcao.Noroeste, Direcao.Sudeste});
	
	public final Direcao[] DIRECOES;
	
	Sentido(Direcao[] _direcoes){
		DIRECOES = _direcoes;
	}
	
	//SUBENUMERACAO
	
	public enum Direcao{
		Norte(		new Posicao(0, +1)	),
		Nordeste(	new Posicao(+1, +1)	),
		Leste(		new Posicao(+1, 0)	),
		Sudeste(	new Posicao(+1, -1)	),
		Sul(		new Posicao(0, -1)	),
		Sudoeste(	new Posicao(-1, -1)	),
		Oeste(		new Posicao(-1, 0)	),
		Noroeste(	new Posicao(-1, +1)	);
		
		public final Posicao REFERENCIA_CARTESIANA;
		
		Direcao(Posicao _referenciaCartesiana){
			REFERENCIA_CARTESIANA = _referenciaCartesiana;
		}
		
		//FUNCOES
		
		public Posicao transladar(Posicao posicao, int distancia){
			return new Posicao(
					posicao.x + (REFERENCIA_CARTESIANA.x * distancia),
					posicao.y + (REFERENCIA_CARTESIANA.y * distancia)
				);
		}
		
		public ArrayList<Posicao> listaDePosicoesAdjacentes(Posicao posicao, int distancia){
			return new ArrayList<Posicao>(){{
				for(int i=1; i < distancia; i++){
					add(transladar(posicao, i));
				}
			}};
		}
	}
}
