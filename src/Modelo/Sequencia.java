package Modelo;

import java.util.HashSet;

import Enumeracao.Alinhamento;
import Primitiva.Posicao;

public class Sequencia {
	private Alinhamento alinhamento;
	private int ladosAbertos;
	private int tamanho;
	private HashSet<Posicao> bloqueantes;

	public Sequencia(Alinhamento alinhamento, int ladosAbertos, int tamanho) {
		this.alinhamento = alinhamento;
		this.ladosAbertos = ladosAbertos;
		this.tamanho = tamanho;
		this.bloqueantes = new HashSet<>();
	}

	public Sequencia() {
		this.tamanho = 1;
		this.ladosAbertos = 0;
		;
		this.bloqueantes = new HashSet<>();
	}

	public int getValor() {
		return this.ladosAbertos * this.getValorSequencia() / 2;
	}

	public int getValorSequencia() {
		switch (this.tamanho) {
		case 1:
			return 1;
		case 2:
			return 230;
		case 3:
			return 36000;
		case 4:
			return 720000;
		default:
			return 999999999;
		}
	}

	public void incrementLadosAbertos() {
		this.ladosAbertos++;
	}

	public int getTamanho() {
		return this.tamanho;
	}

	public Alinhamento getAlinhamento() {
		return this.alinhamento;
	}

	public void setAlinhamento(Alinhamento alinhamento) {
		this.alinhamento = alinhamento;
	}

	public void incrementTamanho() {
		this.tamanho++;
	}

	public void addBloqueante(Posicao bloqueante) {
		this.bloqueantes.add(bloqueante);
	}

	public boolean contemPosicaoBloqueante(Posicao bloqueante) {
		return this.bloqueantes.contains(bloqueante);
	}
}
