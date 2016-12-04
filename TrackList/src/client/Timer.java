package client;

import java.util.TimerTask;


public class Timer extends Thread{
	client client;
	int time;
	private boolean stop;
	public Timer(int min,client c){
		client=c;
		time=min*60;
		stop=false;
		this.start();
	}
	public void stopTimer(){
		stop =true;
	}
	
	public void run(){
		try{
			for(int i=0;i<time&&!stop;i++){
				Thread.sleep(1000);
				if(i==time-1){
					if(!stop){
						client.endTimer();
					}
				}
				else{
					if(!stop){
						client.updateTimer();
					}
				}
			}	
		}catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
