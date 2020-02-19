package data.processus;

import process.visitor.ArrayListVisitor;

public class Sleep {
	
	
	// --------------------------------------
	// Attributs
	// --------------------------------------
	private int time; // time in ms
	
	// --------------------------------------
	// Methods
	// --------------------------------------
	public Sleep(int time) {
		// super(processus);
		this.setTime(time);
	}
	/*
	public Sleep(Processus processus) {
		super(processus);
		this.setTime(1000);
	}
	*/
	public Sleep() {
		this.setTime(1000);
	}
	
	// getters and setters
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	// toString 
	
	public String toString() {
		return /*"Processus : " + this.getProcessus().getProcessusname() + " -  PID : " + this.getProcessus().getpid() + */" - has been sleeped for " + this.getTime() + "ms" ;
	}
	
	public <T> T accept(ArrayListVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	
}
