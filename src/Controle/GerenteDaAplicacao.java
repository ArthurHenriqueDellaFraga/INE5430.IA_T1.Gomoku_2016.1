package Controle;

import Enumeracao.ConfiguracaoDeJogo;
import Evento.*;
import Modelo.Gomoku;
import Visao.InterfaceDaAplicacao;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
	private GerenteDaAplicacao(){
		
	}
	
	public static GerenteDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new GerenteDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//FUNCOES

	public void iniciar(){
		Gomoku gomoku;
		
		try{
			switch(INTERFACE_DA_APLICACAO.iniciar()){
				case 0:		gomoku = new Gomoku(ConfiguracaoDeJogo.Humano_Humano);
							break;
							
				default:	gomoku = new Gomoku(ConfiguracaoDeJogo.Humano_Humano);
			}
			
			try{
				System.out.println("GerenteDaAplicacao.iniciar -> gomoku.inicar()");
				gomoku.iniciar();
			}
			catch(VitoriaAtingidaException e){
				INTERFACE_DA_APLICACAO.apresentarMensagemDeAlerta(e.getMessage(), "Vitoria");
			}
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
	}
}