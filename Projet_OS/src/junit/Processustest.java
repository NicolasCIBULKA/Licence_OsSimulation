package junit;

import data.*;
import process.*;
import process.rrobin.Rrobin;
import process.traduction.Transcriptor;
import process.visitor.OperationVisitor;
import org.junit.*;
import junit.framework.TestCase;
import data.arithmeticaloperation.*;
import data.drivers.Interaction;
import data.drivers.ScreenDriver;
import data.functions.*;
import data.peripheral.Screen;
import data.processus.Processus;
import data.processus.Processuslist;
import data.variable.Intvariable;
import data.variable.Stringvariable;

public class Processustest extends TestCase{
	
	@Test
	public void testAddition() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(0);
		Addition add = new Addition(a,b,c);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(add);
		assertEquals(5, c.getContent());
	}
	
	@Test
	public void testSubstraction() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(0);
		Substraction op = new Substraction(a,b,c);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(1, c.getContent());
	}
	
	@Test
	public void testMultiplication() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(0);
		Multiplication op = new Multiplication(a,b,c);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(6, c.getContent());	
	}
	
	@Test
	public void testDivision() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(0);
		Division op = new Division(a,b,c);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(1, c.getContent());
	}
	
	@Test
	public void testModulo() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(0);
		Modulo op = new Modulo(a,b,c);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(1, c.getContent());
	}
	
	@Test
	public void testComparaison() {
		Intvariable a = new Intvariable(3);
		Intvariable b = new Intvariable(2);
		Intvariable c = new Intvariable(9);
		Comparaison op = new Comparaison(a,b,c, "<");
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(-1, op.getResult().getContent());
	}
	
	@Test
	public void testIncrement() {
		Intvariable a = new Intvariable(1);
		Increment op = new Increment(a);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(2,op.getVar().getContent());
	}
	
	@Test
	public void testDecrement() {
		Intvariable a = new Intvariable(1);
		Decrement op = new Decrement(a);
		OperationVisitor visitor = new OperationVisitor();
		visitor.visit(op);
		assertEquals(0,op.getVar().getContent());
	}
	
	@Test
	public void testPrint() {
		fail(); // TODO
	}
	
	
	@Test
	public void testTraduction() {
		Transcriptor transcriptor = new Transcriptor();
		Processus proc = new Processus();
		String filename ="src/scripts/JUnittest.txt" ;
		transcriptor.transcription(proc, filename);
		assertEquals(proc.getProcessusname(), "JUnittest");
		assertTrue(proc.getOplist().get(1) instanceof Intvariable);
		assertTrue(proc.getOplist().get(0) instanceof Stringvariable);
		assertTrue(proc.getOplist().get(3) instanceof Print);
		assertTrue(proc.getOplist().get(4) instanceof Addition);
		assertTrue(proc.getOplist().get(5) instanceof Sleep);
	}
	
	/*
	@Test
	public void testRounrobin() {
		fail(); // TODO
	}
	*/
	
	
	
	
}
