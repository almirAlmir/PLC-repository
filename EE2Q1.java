package ee2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EE2Q1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		/*Aeroporto aero1 = new Aeroporto(1);
		Aviao [] saidas = new Aviao [4];
		Aviao [] chegadas = new Aviao [2];
		*/ //Usei pra testar as classes
		int N = input.nextInt();
		int [] aux1 = new int [N];
		
		for(int i = 0; i < N; i++) {
			aux1[i] = input.nextInt();
		}
		
		int M = input.nextInt();
		int [] aux2 = new int [M];
		
		for(int i = 0; i < M; i++) {
			aux2[i] = input.nextInt();
		}
		
		int K = input.nextInt();
		Aviao [] saidas = new Aviao [N];
		Aviao [] chegadas = new Aviao [M];
		Aeroporto aero1 = new Aeroporto(K);
		for(int i = 0; i < N ; i++) {
			int n = aux1[i];
			saidas[i] = new Aviao(n, 0, aero1, i);
			
		}
		
		for(int i = 0; i < M; i++) {
			int n = aux2[i];
			chegadas[i] = new Aviao(0, n, aero1, (i+N));
			
		}
		
		for(Aviao a:saidas) {
			a.start();
		}
		for(Aviao a:chegadas) {
			a.start();
		}
	}
	
	public static class Aviao extends Thread{
		
		private int idVoo;
		private int horaChegada;
		private int horaSaida;
		private int atraso;
		private int saidaReal;
		private Aeroporto aero;
		
		public Aviao(int saida, int chegada, Aeroporto aero, int idd){
			this.aero = aero;
			this.horaSaida = saida;
			this.horaChegada = chegada;
			this.saidaReal = 0;
			this.atraso = 0;
			this.idVoo = idd;
		}
		
		public void run() {
			//synchronized(aero){
				Aviao temp = this;
				if(this.horaChegada == 0) {
					int ini = decolando();
					aero.decolar(temp, ini);
					
					
					
				}
				if(this.horaSaida == 0) {
					int ini = pousando();
					
					aero.aterrisar(temp, ini);
					
					
				}
				
				this.aero.autorizandoVoo(temp); // Liberando pista pra pouso ou decolagem
			//}
		}
		
		public int decolando() {
			int x;
			int ini = (int)System.currentTimeMillis();
			x = (this.horaSaida > this.horaChegada? this.horaSaida:this.horaChegada);
			try {
				Thread.sleep(x);
			}catch(InterruptedException ie) {}
			
			return ini;
		}
		
		public int pousando() {
			int x;
			int ini = (int)System.currentTimeMillis();
			x = (this.horaSaida > this.horaChegada? this.horaSaida:this.horaChegada);
			try {
				Thread.sleep(x);
			}catch(InterruptedException ie) {}
			
			return ini;
		}
		
	}
	
	public static class Aeroporto{
		
		private int pistasDisponiveis;
		private LinkedList<Aviao> listaVoos;
		
		public Aeroporto(int pistas) {
			this.pistasDisponiveis = pistas;
			this.listaVoos = new LinkedList<Aviao>();
		}
		
		public synchronized void autorizandoVoo(Aviao aviao) {
			
			pistasDisponiveis++;
			notifyAll();
			
			System.out.println("Aviao "+aviao.idVoo+" deixa pista Livre para o proximo Voo ");
			
		}
		
		public synchronized void decolar(Aviao aviao, int ini) {
			
			listaVoos.add(aviao);
			System.out.println("Aviao "+aviao.idVoo+" solicitando pista livre");
			
			while(aviao.idVoo != listaVoos.getFirst().idVoo || !(pistasDisponiveis > 0)) {
				try {
					wait();
				}catch(InterruptedException ie) {}
				
			}
			try {
				Thread.sleep(500); // 500 milisegundos de uso da pista
			}catch(InterruptedException ie) {
				
			}
	        pistasDisponiveis--;
	        
	        aviao.atraso = ((int)System.currentTimeMillis() - ini)-aviao.horaSaida;
			aviao.saidaReal = aviao.horaSaida + aviao.atraso;
			
	        System.out.println("|Aviao " + listaVoos.removeFirst().idVoo + " decolando|");
	        System.out.println("[Saida esperada "+aviao.horaSaida+", Saida real "+aviao.saidaReal+", Atraso "+aviao.atraso+"]");
	        
			
		}
		
		public synchronized void aterrisar(Aviao aviao, int ini) {
			
			listaVoos.add(aviao);
			
			System.out.println("Aviao "+aviao.idVoo+" solicita pista livre para aterrisar");

	        while(aviao.idVoo != (int) listaVoos.getFirst().idVoo || !(pistasDisponiveis > 0)) {
	            try {
	                wait();
	            } catch (InterruptedException error) {}

	        }
	        
	        pistasDisponiveis--;
	        try {
				Thread.sleep(500); //tempo ocupando a pista
			}catch(InterruptedException ie) {
				
			}
	        
	        aviao.atraso = ((int)System.currentTimeMillis() - ini)-aviao.horaChegada;
			aviao.saidaReal = aviao.horaChegada + aviao.atraso;
	        
	        System.out.println("|Aviao "+listaVoos.removeFirst().idVoo+" aterrisou|");
	        System.out.println("[Pouso esperado "+aviao.horaChegada+", Pouso real "+aviao.saidaReal+", Atraso "+aviao.atraso+"]");
	        //usei this.saidaReal e respectivos atributos aqui pois operam da mesma forma
		}
	}
	
	

}
