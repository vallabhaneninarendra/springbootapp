package parkinglotsystem.model;

import java.util.Date;

public class Token {
	  private String tokenNumber;
	    private Car carDetails;
	    private Slot slotDetails;
	    private Date tokenDate;
	    private long checkInTime;
	    private long checkOutTime;

	    public Token(String tokeNumber, Slot slotDetai, Car carDetails){
	        this.tokenNumber = tokeNumber;
	        this.carDetails = carDetails;
	        this.slotDetails = slotDetai;
	        this.tokenDate = new Date();
	        this.checkInTime = System.currentTimeMillis();
	    }

	    public String getTokenNumber() {
	        return tokenNumber;
	    }
	    public Slot getSlotDetails() {
	        return slotDetails;
	    }
	    public Token updateCheckOutTime(){
	        this.checkOutTime = System.currentTimeMillis();
	        return this;
	    }

	    public Car getCarDetails() {
	        return carDetails;
	    }
}
