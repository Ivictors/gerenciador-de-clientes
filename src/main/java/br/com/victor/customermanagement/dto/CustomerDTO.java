package br.com.victor.customermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerDTO(@NotBlank (message = "Name is required") String name,
                          @NotBlank (message = "Email is required")
                          @Email(message = "Enter a valid email address.") String email,
                          @NotNull(message = "age is required")
                          @Min(value = 16, message = "The age must be over 15 years") Integer age) {
}
