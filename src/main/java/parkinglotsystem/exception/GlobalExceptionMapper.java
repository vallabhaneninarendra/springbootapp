package parkinglotsystem.exception;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import parkinglotsystem.model.ErrorMessage;

@ControllerAdvice
public class GlobalExceptionMapper{

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorMessage> dataNotFound(HttpServletRequest req, Exception e) 
	{
	ErrorMessage error = new ErrorMessage(e.getMessage(), 404, req.getRequestURI());
	return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(DataFoundException.class)
	public ResponseEntity<ErrorMessage> dataFound(HttpServletRequest req, Exception e) 
	{
	ErrorMessage error = new ErrorMessage(e.getMessage(), 200, req.getRequestURI());
	return new ResponseEntity<ErrorMessage>(error, HttpStatus.OK);

	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessage> badRequest(HttpServletRequest req, Exception e) 
	{
	ErrorMessage error = new ErrorMessage(e.getMessage(), 400, req.getRequestURI());
	return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);

	}
	
}
