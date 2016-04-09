package Visao;

import javax.swing.JOptionPane;
import Evento.OperacaoCanceladaException;

public abstract class ComunicadorAbstrato {
	
	//FUNCOES
	
	private void apresentarMensagem(String mensagem, String titulo, int tipo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, tipo);
	}
	
		public void apresentarMensagemDeErro(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.ERROR_MESSAGE);
		}
		
		public void apresentarMensagemDeInformacao(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
		}
		
		public void apresentarMensagemDeAlerta(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.WARNING_MESSAGE);
		}
		
	public int apresentarDialogoOpitativo(String mensagem, String titulo, Object[] opcoes){
		int retorno = JOptionPane.showOptionDialog(null, mensagem, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}

	/*
	public int apresentarDialogoPersonalizado(Object[] listaDeComponentes, String titulo){
		int retorno = JOptionPane.showConfirmDialog(null, listaDeComponentes, titulo, JOptionPane.OK_CANCEL_OPTION , JOptionPane.QUESTION_MESSAGE);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}
	
	public String coletarTextoSimples(String mensagem, String titulo){
		return JOptionPane.showInputDialog(FRAME_FAMILIAR, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
	}
	
	
	public Object selecionarDaLista(String mensagem, String titulo, Object[] lista){
		return JOptionPane.showInputDialog(FRAME_FAMILIAR, mensagem, titulo, JOptionPane.QUESTION_MESSAGE, null, lista, null);
	}
	*/
}