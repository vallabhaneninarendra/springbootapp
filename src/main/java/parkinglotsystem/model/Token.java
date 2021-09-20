package parkinglotsystem.model;

import java.util.Collection;
import java.util.Date;

import javax.xml.bind.DataBindingException;

import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;

import parkinglotsystem.exception.BadRequestException;
import parkinglotsystem.exception.DataNotFoundException;

public class Token {
	  private String tokenNumber;
	    private Car carDetails;
	    private Slot slotDetails;
	    private Date tokenDate;
	    private long checkInTime;
	    private long checkOutTime;

	    public Token(String tokeNumber, Slot slotDetai, Car carDetails) throws DataNotFoundException{
	    	
	        this.tokenNumber = tokeNumber;
	        this.carDetails = carDetails;
	        this.slotDetails = slotDetai;
	        this.tokenDate = new Date();
	        this.checkInTime = System.currentTimeMillis();
	    }

	    public String getTokenNumber(){
	        return tokenNumber;
	    }
	    public Slot getSlotDetails() throws BadRequestException{
	    if(slotDetails.getSlotNumber()!=null) {
	    	  return slotDetails;
	    }
		throw new BadRequestException("Slot not empty");
	   
	    }
	      
	    
	    public Token updateCheckOutTime(){
	        this.checkOutTime = System.currentTimeMillis();
	        return this;
	    }

	    public Car getCarDetails() {
	        return carDetails;
	    }
}
