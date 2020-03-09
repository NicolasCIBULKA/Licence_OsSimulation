package data.primitive;

import data.drivers.Driver;
import process.visitor.OSPrimitiveVisitor;

public class Read {
	/*
	 *  This is the class of the Read primitive
	 *  
	 *  @author Nicolas CIBULKA
	 */
	
	// --------------------------------------
	// Attributs
	// --------------------------------------
	private Driver driver;
	
	// --------------------------------------
	// Methods
	// --------------------------------------
	
	public Read(Driver driver) {
		this.setDriver(driver);
	}

	// Getters and setters
	
	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	
	// primitive visitor
	
		public <T> T accept(OSPrimitiveVisitor<T> visitor) {
			return visitor.visit(this);
		}
	
}
