package ee2;

import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
//import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class EE2Q2 {

public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
	Scanner input = new Scanner(System.in);
	int O = input.nextInt(); // nº fixo de Threads na Pool
	
	int N = input.nextInt();
	ExecutorService executor = Executors.newFixedThreadPool(O);
	BlockingQueue<Tarefa> tarefas = new LinkedBlockingQueue<>();
	int aux = N;

	String line = input.nextLine();
	while(aux > 0) {
		
		line = input.nextLine();
		String [] numStr = line.split(" ");
		
		int [] num = new int [numStr.length];
		
		for(int j=0; j<num.length;j++) {
			num[j] = Integer.parseInt(numStr[j]);
			 //lendo a linha e pondo em Array de inteiros
			
		}
		 //Array com as dependencias e -1 nos dois primeiros slots
		if(num.length <= 2) {
			tarefas.add(new Tarefa(num[0], num[1], new int[]{}));
		}else {
			int [] auxD = new int[num.length-2];
			for(int i = 2; i<num.length; i++) {
				auxD[i-2] = num[i];
			}
			tarefas.add(new Tarefa(num[0], num[1], auxD));
		}
		aux--;
	}
	
	/*tarefas.add(new Tarefa(1, 200, new int[]{2, 4}));
	tarefas.add(new Tarefa(4, 30, new int[]{}));
	tarefas.add(new Tarefa(3, 50, new int[]{2}));
	tarefas.add(new Tarefa(2, 100, new int[]{}));
	*/
	List<Integer> concluidas = new ArrayList<>();
	concluidas.add(0); //Pra quando checar os -1 que coloquei no inicio
	while(concluidas.size() <= N) {
		
		Tarefa temp = tarefas.remove();
		boolean podeExecutar = true;
		
		for(int i:temp.dependencias) {
			
				if(!concluidas.contains(i)) {
					podeExecutar = false;
					break;
			}
		}
		
		if(!podeExecutar) {
		
			tarefas.add(temp);
			
		}else {
			concluidas.add(temp.id);
			executor.execute(temp);
			
			}
		
		
	}
	executor.shutdown();			
}
	
	
public static class Tarefa implements Runnable{

	private int id;
	private int tempoExecucao;
	private int [] dependencias;
	
	public Tarefa(int idd, int tempo, int [] dependencias) {
		
		this.id = idd;
		this.tempoExecucao = tempo;
		this.dependencias = dependencias;
		}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(this.tempoExecucao);
		}catch(InterruptedException ie) {}
		System.out.println("tarefa "+this.id+" feita");
		
	}
	
	
	
}

}
