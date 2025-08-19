package az.edu.asiouconferenceportal.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		var errors = ex.getBindingResult().getFieldErrors().stream()
			.collect(java.util.stream.Collectors.toMap(
					e -> e.getField(),
					e -> e.getDefaultMessage(),
					(a, b) -> a
			));
		return ResponseEntity.badRequest().body(Map.of("errors", errors));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
		String msg = ex.getMessage();
		if (msg == null || msg.isBlank()) msg = "Unexpected error";
		return ResponseEntity.badRequest().body(Map.of("message", msg));
	}
}
