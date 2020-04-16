package data.drivers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import org.apache.log4j.Logger;

import data.peripheral.HardDisk;
import data.peripheral.Slot;
import logs.LoggerUtility;


public class HardDiskDriver extends Driver{
	
	private static Logger logger = LoggerUtility.getLogger(HardDiskDriver.class, "text");
	private HardDisk hd;
	PrintStream writer;
	File info;
	public HardDiskDriver(String driverID, Interaction authorization, HardDisk hd) {
		super(driverID, authorization);
		this.hd = hd;
		info = hd.getInfo();
	}
	
	public void addSlot(String name) throws FullHDException{
		Slot slot = new Slot(name,hd);
		if(hd.getSlotnumber() == hd.getMaxSlot() || hd.getSlotlist().containsKey(slot.getName())) {
			throw new FullHDException();
		}
		
		HashMap<String,Slot> stmp = hd.getSlotlist();
		stmp.put(slot.getName(), slot);
		hd.setSlotlist(stmp);
		try {
			writer = new PrintStream(new FileOutputStream(info,true));
			writer.println(slot.getName()+";"+ slot.getByteSize()+";");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		hd.incrementSlotnumber();
	}
	
	
	
	public void write(String text, String slotName) throws StringIndexOutOfBoundsException {
		Slot s = hd.getSlotlist().get(slotName);
		int size = s.getCharSize();
		int sizeMax = s.getMaxSize();
		int sizeEntry = text.length();
		if(sizeEntry + size >= sizeMax) {
			throw new StringIndexOutOfBoundsException();
		}
		else
		try {
            PrintStream writer = new PrintStream(new FileOutputStream(s.getSlot(),true));
          
            try {
                 writer.print(text);
              
            } finally {
                 writer.close(); 
            }
          
       } catch (IOException e) {
            e.printStackTrace();
       }
		
		s.setSize(size + sizeEntry);
		try { 
	    	   File tempFile = new File(info.getAbsolutePath() + ".tmp");
	    	   BufferedReader br = new BufferedReader(new FileReader(hd.getHdPosition()+"/info.csv"));
	    	   writer = new PrintStream(new FileOutputStream(tempFile));
	    	   String line = null;

	        
		        while ((line = br.readLine()) != null) {
		        	String[] slot = line.split(";");
		        	String value = slot[0];
		            if (!value.equals(slotName)) {
		                writer.println(line);
		            }
		            else {
		            	writer.println(s.getName()+";"+ s.getByteSize()+";");
		            }
		        }
		        writer.close();
		        br.close();
		        if (!tempFile.renameTo(info)) {
		            logger.info("Could not rename file");
		        }
		}catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void read(String slotName) { 
		Slot s = hd.getSlotlist().get(slotName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(s.getSlot()));
			try {
		      String CurrentLine;
		      while ((CurrentLine = reader.readLine()) != null) {
		        logger.info(CurrentLine);
		      }
			}
		      finally {
		    	  reader.close();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
	}
	
	public void eraseSlot(String slotName) {
		Slot s = hd.getSlotlist().get(slotName);
		HashMap<String,Slot> stmp = hd.getSlotlist();
		stmp.remove(slotName);
		hd.setSlotlist(stmp);
		
		   
       try { 
    	   File tempFile = new File(info.getAbsolutePath() + ".tmp");
    	   BufferedReader br = new BufferedReader(new FileReader(hd.getHdPosition()+"/info.csv"));
    	   writer = new PrintStream(new FileOutputStream(tempFile));
    	   String line = null;

        
	        while ((line = br.readLine()) != null) {
	        	String[] slot = line.split(";");
	        	String value = slot[0];
	            if (!value.equals(slotName)) {
	                writer.println(line);
	            }
	        }
	        writer.close();
	        br.close();
	
	        
	        if (!info.delete()) {
	            logger.info("Could not delete file");
	            return;
	        }

        
	        if (!tempFile.renameTo(info))
	            logger.info("Could not rename file");
	        }
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	   	hd.decrementSlotnumber();
	   	s.delete();
       	
       
}

	public HardDisk getHd() {
		return hd;
	}
	
	public static void main(String[] args) {
		HardDisk hd = new HardDisk("76","./src/harddisks1");
		HardDisk hd2 = new HardDisk("tre","./src/harddisks2");

		Interaction authorization = new Interaction();
		HardDiskDriver hdd = new HardDiskDriver("23",authorization,hd);
		HardDiskDriver hdd2 = new HardDiskDriver("3",authorization,hd2);
		try {
			hdd.addSlot("A");
			hdd2.addSlot("B");
			hdd2.addSlot("C");
			hdd.write("Elle a les yeux bleus pour effacer ce ciel gris ","A");
			hdd2.write("Elle a les yeux bleus pour effacer ce ciel fruit ","C");
			hdd2.write("Elle a les yeux bleus pour effacer ce ciel gris ","B");
			hdd2.eraseSlot("B");
		} catch (FullHDException e) {
			e.printStackTrace();
		}
	}

	
}
