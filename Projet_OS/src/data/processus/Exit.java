package data.processus;

import process.visitor.ArrayListVisitor;

public class Exit extends ProcessusControl{
	/*
	 * Data class of the Exit primitive 
	 * 
	 * @author Nicolas CIBULKA
	 */

	// --------------------------------------
	// Attributs
	// --------------------------------------


	
	// --------------------------------------
	// Methods
	// --------------------------------------
	
	// Constructor of the data class Exit 
	public Exit(Processus processus) {
		super(processus);
	}
	
	
	// toString 
	public String toString() {
		return "Processus : " + this.getProcessus().getProcessusname() + " -  PID : " + this.getProcessus().getpid() + " - has been stopped" ;
	}
	
	public <T> T accept(ArrayListVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
