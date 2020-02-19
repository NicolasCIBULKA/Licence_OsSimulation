package data.processus;

import java.util.ArrayList;

public class Processus {
	
	/*
	 * This class contains all the operations of a processus in the arrayList
	 * 
	 * @Author Nicolas CIBULKA
	 * 
	 */
	
	// --------------------------------------
	// Attributs
	// --------------------------------------
	private ArrayList<Operation> operationlist;
	private int priority;
	private int pid;
	private String processusname;
	// --------------------------------------
	// Methods
	// --------------------------------------
	
	// this is the constructor of the Processus class
	public Processus(String processusname) {
		this.operationlist = new ArrayList<Operation>();
		this.processusname = processusname;
	}
	
	// Getters and setters
	
	public ArrayList<Operation> getOplist() {
		return operationlist;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getpid() {
		return pid;
	}
	
	public void setpid(int pid) {
		this.pid = pid;	
	}
	
	public String getProcessusname() {
		return processusname;
	}
	
	public void setProcessusname(String processusname) {
		this.processusname = processusname;
	}

	public int getNboperation() {
		return operationlist.size();
	}
	// Methods
	
	public void addOperation(Operation op) {
		operationlist.add(op);
	}


	
		
}
