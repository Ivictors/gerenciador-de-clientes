package br.com.victor.customermanagement.exceptions;

public record ErrorResponseDTO(int status, String message, long timestamp) {
}
