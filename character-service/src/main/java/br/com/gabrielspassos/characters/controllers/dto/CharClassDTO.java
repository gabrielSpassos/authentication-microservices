package br.com.gabrielspassos.characters.controllers.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class CharClassDTO {

    @NotNull(message = "You must inform the the character class name")
    private String characterClassName;

    public String getCharacterClassName() {
        return characterClassName;
    }

    public void setCharacterClassName(String characterClassName) {
        this.characterClassName = characterClassName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
