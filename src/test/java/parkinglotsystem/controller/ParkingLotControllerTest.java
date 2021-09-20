package parkinglotsystem.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import parkinglotsystem.controller.ParkingLotController;
import parkinglotsystem.exception.DataFoundException;
import parkinglotsystem.exception.DataNotFoundException;
import parkinglotsystem.model.Car;
import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
import parkinglotsystem.service.ParkingLot;
@WebMvcTest(ParkingLotController.class)
class ParkingLotControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParkingLot service;
	
	    @Test
	    public void initiateSlots() throws Exception
	    {
	    	String initiateSlots = "{\"slotNumber\":10}";
	        Slot slot = new Slot(1);
	        ArrayList<Slot> slots = new ArrayList<Slot>();
	        slots.add(slot);

	        when(service.initiateLot(10)).thenReturn(slots);

	        mockMvc.perform(post("/initiateLot")
	        		  // Validate the response code and content type
	        		.contentType(MediaType.APPLICATION_JSON)
					.content(initiateSlots)
					.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].slotNumber").value("1"))
	                .andExpect(jsonPath("$[0].slotFree").value("true"));
	        verify(service, times(1)).initiateLot(10);
	        verifyNoMoreInteractions(service);

	    }
	    @Test
	    public void initiateSlotsWithEmpty() throws Exception
	    {
	    	String initiateSlots = "{\"slotNumber\":\"\"}";
	        Slot slot = new Slot(1);
	        ArrayList<Slot> slots = new ArrayList<Slot>();
	        slots.add(slot);

	        when(service.initiateLot(1)).thenReturn(slots);

	        mockMvc.perform(post("/initiateLot")
	        		  // Validate the response code and content type
	        		.contentType(MediaType.APPLICATION_JSON)
					.content(initiateSlots)
					.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isBadRequest())
	                .andExpect(jsonPath("$.errorMessage").value("Slots can not be empty or null"));
	        verify(service, times(0)).initiateLot(1);
	        verifyNoMoreInteractions(service);

	    }
	    @Test
	    public void parkTheCar() throws Exception
	    
	    {
	    	String car = "{\"carColor\":\"Blue\",\"carNumber\":\"12345\"}";
	        Token token = new Token("123123",new Slot(123),new Car("Blue","12345"));

	        when(service.parkTheCar("Blue","12345")).thenReturn(token);
	        mockMvc.perform(post("/ParkTheCar")
	        		// Validate the response code and content type
	        		.contentType(MediaType.APPLICATION_JSON)
					.content(car)
					.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                // Validate the returned fields
	                .andExpect(jsonPath("$['carDetails'].carColor").value("Blue"))
	                .andExpect(jsonPath("$['carDetails'].carNumber").value("12345"))
	                .andExpect(jsonPath("$.tokenNumber").value("123123"));
	        verify(service, times(1)).parkTheCar("Blue","12345");
	        verifyNoMoreInteractions(service);

	    }
	    
	    @Test
	    public void parkTheCarWithOutCarNumber() throws Exception
	    
	    {
	    	String car = "{\"carColor\":\"Blue\",\"carNumber\":\"\"}";
	        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));
	        when(service.parkTheCar("Blue","")).thenReturn(token);
	        mockMvc.perform(post("/ParkTheCar")
	        		   // Validate the response code and content type
	        		.contentType(MediaType.APPLICATION_JSON)
					.content(car)
					.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound())
	                // Validate the returned fields
	                .andExpect(jsonPath("$.errorMessage").value("Car number empty"));
	              
	        verify(service, times(0)).parkTheCar("Blue","");
	        verifyNoMoreInteractions(service);

	    }
	    
	    @Test
	    public void parkTheCarWithoutIntializeSlots() throws Exception
	    
	    {
	    	String car = "{\"carColor\":\"Blue\",\"carNumber\":\"12345\"}";
	       Token token = new Token("",new Slot(),new Car("Red","12345"));

	        when(service.parkTheCar("Blue","12345")).thenReturn(token);
	        mockMvc.perform(post("/ParkTheCar")
	        		// Validate the response code and content type
	        		.contentType(MediaType.APPLICATION_JSON)
					.content(car)
					.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isBadRequest());
	        verify(service, times(1)).parkTheCar("Blue","12345");
	        verifyNoMoreInteractions(service);

	    }
	 
    @Test
    public void searchCarByRegNo() throws Exception
    {
        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));

        when(service.getCarByRegNO("123")).thenReturn(token);

        mockMvc.perform(get("/getCarByRegNo/{carNumber}", "123"))
                // Validate the response code
                .andExpect(status().isOk())
                // Validate the returned fields
                .andExpect(jsonPath("$.tokenNumber", is("123123")))
                .andExpect(jsonPath("$['carDetails'].carNumber", is("123"))
                );

        verify(service, times(1)).getCarByRegNO("123");
        verifyNoMoreInteractions(service);

    }
    

    @Test
    public void searchOneCarByColor() throws Exception
    {
    	  ArrayList<Token> carList = new ArrayList<Token>(); 
        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));
        carList.add(token);
        when(service.getCarByColor("Blue")).thenReturn(carList);
        mockMvc.perform(get("/getCarByColor/{carColor}", "Blue"))
        // Validate the response code
                .andExpect(status().isOk())
                // Validate the returned fields
                .andExpect(jsonPath("$[*].carDetails.carColor").value("Blue"))
                .andExpect(jsonPath("$[*].carDetails.carNumber").value("123"))
                .andExpect(jsonPath("$[*].tokenNumber").value("123123"))
                .andReturn();
        verify(service, times(1)).getCarByColor("Blue");
        verifyNoMoreInteractions(service);

    }
	@Test
    public void searchMultipleCarsByColor() throws Exception
    {
    
        ArrayList<Token> carList = new ArrayList<Token>(); 

        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));
        Token token1 = new Token("1231234",new Slot(123),new Car("Blue","1234"));
          
       carList.add(token);
       carList.add(token1);
       
        when(service.getCarByColor("Blue")).thenReturn(carList);
        mockMvc.perform(get("/getCarByColor/{carColor}", "Blue")
      
     // Validate the response code and content type
        .contentType(MediaType.APPLICATION_JSON)
	
		.accept(MediaType.APPLICATION_JSON))
 
        .andExpect(status().isOk())
               
     // Validate headers
        .andExpect(header().string(HttpHeaders.LOCATION, "/getCarByColor/Blue"))

     // Validate the returned fields

        .andExpect(jsonPath("$[*].[?(@.carDetails.carColor == '%s')]", "Blue").exists())
        .andExpect(jsonPath("$[*].[?(@.carDetails.carColor == '%s')]", "Blue").exists())
        .andExpect(jsonPath("$[*].[?(@.carDetails.carNumber == '%s')]", "123").exists())
        .andExpect(jsonPath("$[*].[?(@.carDetails.carNumber == '%s')]", "1234").exists())
        .andExpect(jsonPath("$[*].[?(@.tokenNumber == '%s')]", "123123").exists())
        .andExpect(jsonPath("$[*].[?(@.tokenNumber == '%s')]", "1231234").exists());
        
        verify(service, times(1)).getCarByColor("Blue");
        verifyNoMoreInteractions(service);

    }
    @Test
    public void UnParkTheCar() throws Exception
    {
        String responseString = "Car exit from parkinglot";

        when(service.unParkTheCar("123123")).thenReturn(responseString);

        mockMvc.perform(delete("/unParkTheCar/{tokenNumber}", "123123"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", is("Car exit from parkinglot")));

        verify(service, times(1)).unParkTheCar("123123");
        verifyNoMoreInteractions(service);

    }
    

}
