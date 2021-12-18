package com.daviholanda.cursomc.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO {

    @NotEmpty(message = "Preenchimento Obrigatório")
    @Email(message = "E-mal inválido")
    private String email;

    private EmailDTO() {

    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
