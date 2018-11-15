package br.com.gabrielspassos.characters.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class CharacterEntity {

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
}
