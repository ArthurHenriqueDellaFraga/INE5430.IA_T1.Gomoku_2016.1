package PadraoDeProjeto;

import java.util.HashSet;

public class Propagador<T> {
    private final HashSet<Captador<T>> CONJUNTO_DE_CAPTADORES =  new HashSet<Captador<T>>();
 
    public Propagador(){
    	
    }
    
    //FUNCOES
    
    public void adicionarCaptador(Captador<T> captador) {
    	CONJUNTO_DE_CAPTADORES.add(captador);
    }
 
    public void removerCaptador(Captador<T> captador) {
    	CONJUNTO_DE_CAPTADORES.remove(captador);
    }
 
    public void propagar(T informacao) {
    	for(Captador<T> captador : CONJUNTO_DE_CAPTADORES){
    		captador.captar(informacao);
        }
    }
    
    public T sintonizar(){
    	Sintonizador captador = new Sintonizador();
		adicionarCaptador(captador);
		
		while(captador.captacao == null){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return captador.captacao;
    }
    
    // SUBCLASSES
    
    class Sintonizador extends Captador<T>{
		public T captacao;
		
		public void captar(T t){
			captacao = t;
		}
	};
}