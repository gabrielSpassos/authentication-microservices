package br.com.gabrielspassos.characters.controllers.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CharacterDTO {

    private String name;
    private String charClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
