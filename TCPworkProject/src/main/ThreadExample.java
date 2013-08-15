package main;

public class ThreadExample implements Runnable{

	private String greeting;

	public ThreadExample(String greeting){
		this.greeting = greeting;

	}
	public void run(){
		for(;;){
			System.out.println(Thread.currentThread().getName() + ": "+ greeting);
			try{
				Thread.sleep((long)(Math.random() * 100));
			}catch(InterruptedException e){}
		}
	}
	
	public static void main(String args[]){
		new Thread(new ThreadExample("Hello")).start();
		new Thread(new ThreadExample("Aloha")).start();
		new Thread(new ThreadExample("Ciao")).start();
	}
}