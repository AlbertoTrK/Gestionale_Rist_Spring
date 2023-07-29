package org.elis.progettoristorante.dto.swagger;

import java.time.LocalDateTime;
import java.util.List;

public class BadRequestDTO {
	
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String exception;
	private String messagge;
	private List<DettagliErroreDTO> errors;
	private String path;
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getMessagge() {
		return messagge;
	}
	public void setMessagge(String messagge) {
		this.messagge = messagge;
	}
	public List<DettagliErroreDTO> getErrors() {
		return errors;
	}
	public void setErrors(List<DettagliErroreDTO> errors) {
		this.errors = errors;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
