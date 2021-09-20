package parkinglotsystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import parkinglotsystem.exception.DataFoundException;
import parkinglotsystem.exception.DataNotFoundException;
import parkinglotsystem.exception.GlobalExceptionMapper;

import parkinglotsystem.model.Car;
import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
@Service
public class ParkingLot {
	 ArrayList<Slot> availableSlotList;
	   private final List<Token> tokenForLot;

	   private final List<Token> historyOfParking;
	   
	   private final List<Token> carList;

	   public ParkingLot() {
	      this.tokenForLot = new ArrayList<>();
	      this.historyOfParking = new ArrayList<>();
	      this.carList = new ArrayList<>();
	 
	   }

	   public ArrayList<Slot> initiateLot(int numberOfLots) {
	      ArrayList<Slot> totalSlots = new ArrayList<Slot>() {};
	      for (int i = 1; i<= numberOfLots; i++) {
	         Slot getSlotAssignment = new Slot(i);
	         totalSlots.add(getSlotAssignment);
	      }

	      return this.availableSlotList = totalSlots;
	   }

	   public Token parkTheCar(String carColor, String carNumber){
	   
	      Car car = new Car(carColor,carNumber);
	      if(isSlotAvailable()){
	         Slot availableSlot = getTheNextFreeSlot();
	         Token parkingToken = new Token(String.valueOf(System.currentTimeMillis()),availableSlot,car);
	         this.tokenForLot.add(parkingToken);
	         return parkingToken;
	      }else {
	    	  throw new DataNotFoundException("Slots Not Availble");
	      }
	   }

	   private boolean isSlotAvailable() {
		   if(availableSlotList!=null) {
	      boolean isSlotAvailable = false;

	      for(Slot slot:availableSlotList){
	         if(slot.isSlotFree()){
	            isSlotAvailable = true;
	            break;
	         }
	      }
	      return isSlotAvailable;
		   }else {
			   throw new DataNotFoundException("initiate slots comback to parking"); 
		   }
	   }
		   
	   private Slot getTheNextFreeSlot() {
	      for(Slot slot : availableSlotList){
	         if(slot.isSlotFree()){
	            slot.makeSlotOccupied();
	            return slot;
	         }
	      }
	      throw new DataNotFoundException("initiate slots and comback to parking");
	   }

	   public Token getCarByRegNO(String carNumber) {
	      for(Token tokenSearch:tokenForLot){
	         String carDetails = tokenSearch.getCarDetails().getCarNumber();
	         if(carDetails.equalsIgnoreCase(carNumber)){
	           return tokenSearch;
	         }
	      }
	       throw new DataNotFoundException("carNumber "+carNumber+" not found ");
	   }

	public List<Token> getCarByColor(String carColor) {
		   List<Token> matchColor = new ArrayList<Token>();

		
		      for(Token tokenSearch:tokenForLot){
		         String carDetails = tokenSearch.getCarDetails().getCarColor();
		         if(carDetails.equalsIgnoreCase(carColor)){
		        	 matchColor.add(tokenSearch);
		         }
		      }
		      
		      if(matchColor.size() == 0) {
				//extracted(carColor);
		      throw new DataNotFoundException(""+carColor+" car not found in parking lot");
		      }else {
		    	  return matchColor;
		      }
		   }

		 

	   public String unParkTheCar(String tokenNumber) {
		 
			   for(Token tokenInLot:tokenForLot){
			         if(tokenInLot.getTokenNumber().equals(tokenNumber)){
			            tokenForLot.remove(tokenInLot);
			            Slot slot = tokenInLot.getSlotDetails();
			            int slotNumber = slot.getSlotNumber();
			            return removeCarFromSlot(tokenInLot,slotNumber);
			         }
			         
			      }
		 
			   throw new DataNotFoundException("car not found in parking lot");
	   }


	   private String removeCarFromSlot(Token token, int slotNumber) {
	      for (Slot removeEntry:availableSlotList){
	         if(removeEntry.getSlotNumber() == slotNumber){
	            removeEntry.makeSlotFree();
	            Token historyToken = token.updateCheckOutTime();
	            historyOfParking.add(historyToken);
	            throw new DataFoundException("car exit from the parking lot");
	         }

	      }
	      throw new DataNotFoundException("Error occured");
	     
	   }
	   public List<Token> showAllCars(){
		   if(tokenForLot.size()!=0) {
			   return tokenForLot;
		   }
		   throw new DataFoundException("Parkinglot empty");  
	   }
	   public List<Token> historyOfParking(){
	      return historyOfParking;
	   }
}
