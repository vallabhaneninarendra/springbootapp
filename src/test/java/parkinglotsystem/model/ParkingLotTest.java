package parkinglotsystem.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parkinglotsystem.exception.DataFoundException;
import parkinglotsystem.exception.DataNotFoundException;
import parkinglotsystem.service.ParkingLot;

class ParkingLotTest {

	

	ParkingLot Parkinglot = new ParkingLot();
	

    @BeforeEach
    public void beforeAllMethod(){
    	Parkinglot.initiateLot(2);
    }

	@Test
	void checkParkinglotWithGivenNumSlots() {
		  ArrayList<Slot> availableSlot = Parkinglot.initiateLot(2);
		assertEquals(2,availableSlot.size());
	}
	@Test
	public void checkParkACar() 
	{
		Token slot1 = Parkinglot.parkTheCar("Blue","AP36N0978");
	    assertEquals(1, slot1);
		Token slot2 = Parkinglot.parkTheCar("Red","AP39N0978");
	    assertEquals(2, slot2);
	    Token slot3 = Parkinglot.parkTheCar("White","AP39N0998");
	    assertEquals(3, slot3);
	    Token slot4 = Parkinglot.parkTheCar("grey","AP39N0998");
	    assertEquals(4, slot4);
	    Token slot5 = Parkinglot.parkTheCar("Black","AP39N0998");
	    assertEquals(5, slot5);
	 }
	
     @Test
     public void exitCarPark() 
     {
    	 Exception exception = assertThrows(DataFoundException.class, () ->{
    	 Parkinglot.initiateLot(2);
    	  Token token = Parkinglot.parkTheCar("Blue","AP36N0978");
          String unParkMessage = Parkinglot.unParkTheCar(token.getTokenNumber());
         
     });
     String expectedMessage = "car exit from the parking lot";
	    String actualMessage = exception.getMessage();

 	    assertTrue(actualMessage.contains(expectedMessage));
      // assertEquals(expectedMessage,actualMessage);
     }
     @Test
     public void showParkingDetails() {
    	 Parkinglot.parkTheCar("Blue","AP36N0978");
    	 Parkinglot.parkTheCar("red","AP36N0978");
 	      List<Token> token = Parkinglot.showAllCars();   
 	     assertEquals(2, token.size());
     }
     @Test
     public void searchCarColor() {
    	 Parkinglot.parkTheCar("NavyBlue","AP36N0978");
 	     assertEquals("NavyBlue",Parkinglot.getCarByColor("NavyBlue"));
     }
     @Test
     public void searchCarByRegNo() {
    	 Parkinglot.parkTheCar("NavyBlue","AP36N0978");
 	     assertEquals("AP36N0978",Parkinglot.getCarByRegNO("AP36N0978"));
     }
     @Test
     public void searchCarColorNegativeCase() {
     	   Exception exception = assertThrows(DataNotFoundException.class, () -> {
     		  Parkinglot.initiateLot(1);
     		 Parkinglot.parkTheCar("red","123");
     			List<Token> searchToken = Parkinglot.getCarByColor("blue");
     	    });

     	    String expectedMessage = "blue car not found in parking lot";
     	    String actualMessage = exception.getMessage();

     	    assertTrue(actualMessage.contains(expectedMessage));
     }
//     @Test
//     public void nextSoltNumber() {
//    	 Parkinglot.parkTheCar("NavyBlue","AP36N0978");
// 	     assertEquals(2,Parkinglot.get());
//     }
	


}
