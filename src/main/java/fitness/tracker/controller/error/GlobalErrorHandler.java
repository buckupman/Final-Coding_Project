package fitness.tracker.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice

/*
 @RestControllerAdvice: This annotation combines @ControllerAdvice and @ResponseBody, 
 making it a centralized place to handle exceptions thrown by controllers in a RESTful manner
 */

@Slf4j  // This annotation from Lombok generates a logger named log for logging purposes.
public class GlobalErrorHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleNoSuchElementException(
			NoSuchElementException exception) {
		
		log.error("NoSuchElementException occured: {}", exception.getMessage());
		
		return Map.of("message", exception.toString());
	}
	
	/*
	 * @ExceptionHandler(NoSuchElementException.class): This annotation specifies that the following 
	 * method will handle exceptions of type NoSuchElementException.
	 * 
	 * @ResponseStatus(code = HttpStatus.NOT_FOUND): This annotation sets the HTTP response status 
	 * to 404 (NOT_FOUND) when this exception occurs.
	 * 
	 * public Map<String, String> handleNoSuchElementException(NoSuchElementException exception) 
	 * { ... }: This method handles NoSuchElementException exceptions. It takes the exception 
	 * as input and returns a Map<String, String> containing an error message.
	 * 
	 * Inside the method, it logs the error message using the log.error method.
	 * 
	 * It then returns a Map containing the error message wrapped in a key-value pair.
	 */
}

/*
 In summary, this GlobalErrorHandler class acts as a centralized error handler for the application. 
 It intercepts NoSuchElementExceptions thrown by controllers and returns a custom error response with 
 a 404 status code. This ensures that if any controller encounters a NoSuchElementException, the error 
 will be handled uniformly across the application.
 */
