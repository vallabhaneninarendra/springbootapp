package parkinglotsystem.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import parkinglotsystem.exception.DataFoundException;
import parkinglotsystem.exception.DataNotFoundException;
import parkinglotsystem.model.Car;
import parkinglotsystem.model.ErrorMessage;
import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
import parkinglotsystem.service.ParkingLot;

@RestController
public class ParkingLotController {

	private final ParkingLot service;

	public ParkingLotController(ParkingLot service) {
		this.service = service;
	}

    @PostMapping("/initiateLot")
    public ArrayList<Slot> initiateLot(@RequestBody Slot slot){
    	int slots = slot.getSlotNumber();
    	if(slots==0) {
    		  throw new DataNotFoundException("Slot never empty");
    	}else {
    		 ArrayList<Slot> availableSlot= service.initiateLot(slot.getSlotNumber());
    	        return  availableSlot;
    	}
       
    
       
    }
    @PostMapping("/ParkTheCar")
	public ResponseEntity<Object> parkTheCar(@RequestBody Car car)
	{
    	if(car.getCarNumber()!="") {
    		return new ResponseEntity<>(service.parkTheCar(car.getCarColor(),car.getCarNumber()),HttpStatus.OK);
    	}else {
    		  throw new DataNotFoundException("Car number empty");
    	}
	
	}


    @DeleteMapping("/unParkTheCar/{tokenNumber}")
    public String unParkCar(@PathVariable String tokenNumber) throws DataFoundException{
        String parkingStatus = service.unParkTheCar(tokenNumber);
        return parkingStatus;
    }

    @GetMapping("/getCarByRegNo/{carNumber}")
    public Token getCarByRegNo(@PathVariable String carNumber, HttpServletResponse httpResponse, 
            WebRequest request){
        Token token = service.getCarByRegNO(carNumber);
        httpResponse.getStatus();
        System.out.println(token);
        return token;
    }
    @GetMapping("/getCarByColor/{carColor}")
    public List<Token> getCarByColor(@PathVariable String carColor,HttpServletResponse httpResponse, 
            WebRequest request) throws JsonProcessingException {
        List<Token> token = service.getCarByColor(carColor);
      
    httpResponse.setHeader("Location", String.format("%s/getCarByColor/"+carColor+1+"", 
               request.getContextPath(),request.getParameterMap(),token));

        
        return token;
    }
    @GetMapping("/getAllCars")
    @ResponseBody
    public List<Token> getAll() throws DataNotFoundException  {
        List<Token> token = service.showAllCars();
        return token;
    }

}