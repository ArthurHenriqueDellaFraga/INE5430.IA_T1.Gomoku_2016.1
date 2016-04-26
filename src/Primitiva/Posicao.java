package Primitiva;

import java.awt.Point;

public class Posicao extends Point {

	public Posicao(int x, int y){
		super(x, y);
	}
	
	//OUTROS
	
	public int hashCode(){
		return x * 1000 + y;
	}
}
