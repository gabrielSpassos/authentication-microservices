package br.com.gabrielspassos.characters.controllers.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CharClassDTO {

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
