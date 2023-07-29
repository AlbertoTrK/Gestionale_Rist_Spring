package org.elis.progettoristorante.dto;

import java.time.LocalDateTime;

public class ErrorMsgDTO {
	private String message;
	private LocalDateTime data;

	public ErrorMsgDTO(String message) {
		this.message = message;
		data = LocalDateTime.now();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	
	
}
