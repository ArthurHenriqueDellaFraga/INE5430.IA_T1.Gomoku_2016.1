package Enumeracao;

import java.util.ArrayList;

import Modelo.Humano;
import Modelo.Jogador;

public enum ConfiguracaoDeJogo {
	Humano_Humano (
			new ArrayList<Jogador>(){{
					add(new Humano("Jogador 1", Alinhamento.Branco));
					add(new Humano("Jogador 2", Alinhamento.Preto));
			}}
	);
	
	public final ArrayList<Jogador> LISTA_DE_JOGADORES;
	
	private ConfiguracaoDeJogo(ArrayList<Jogador> _listaDeJogadores){
		LISTA_DE_JOGADORES = _listaDeJogadores;
	}
}
