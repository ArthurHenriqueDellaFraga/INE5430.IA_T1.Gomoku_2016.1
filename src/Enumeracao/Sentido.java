package Enumeracao;

import java.awt.Point;
import java.util.ArrayList;

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
		Norte(		new Point(0, +1)	),
		Nordeste(	new Point(+1, +1)	),
		Leste(		new Point(+1, 0)	),
		Sudeste(	new Point(+1, -1)	),
		Sul(		new Point(0, -1)	),
		Sudoeste(	new Point(-1, -1)	),
		Oeste(		new Point(-1, 0)	),
		Noroeste(	new Point(-1, +1)	);
		
		public final Point REFERENCIA_CARTESIANA;
		
		Direcao(Point _referenciaCartesiana){
			REFERENCIA_CARTESIANA = _referenciaCartesiana;
		}
		
		//FUNCOES
		
		public Point transladar(Point posicao, int distancia){
			return new Point(
					posicao.x + REFERENCIA_CARTESIANA.x * distancia,
					posicao.x + REFERENCIA_CARTESIANA.y * distancia
				);
		}
		
		public ArrayList<Point> listaDePosicoesAdjacentes(Point posicao, int distancia){
			return new ArrayList<Point>(){{
				for(int i=1; i < distancia; i++){
					add(transladar(posicao, i));
				}
			}};
		}
	}
}
