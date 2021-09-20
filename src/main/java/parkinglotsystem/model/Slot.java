package parkinglotsystem.model;

public class Slot {
	  private boolean isParked;
	    private Integer slotNumber;
public Slot() {
	
}

	    public Slot(Integer slotNumber){
	        this.slotNumber = slotNumber;
	        this.isParked = false;
	    }

	    public boolean isSlotFree() {
	        return !isParked;
	    }

	    public Slot makeSlotFree(){
	        isParked = false;
	        return this;
	    }
	    public Integer getSlotNumber() {
	        return slotNumber;
	    }
	    public Slot makeSlotOccupied() {
	        this.isParked = true;
	        return this;
	    }
}
