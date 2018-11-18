package br.com.gabrielspassos.characters;

import br.com.gabrielspassos.characters.controllers.dto.CharClassDTO;
import br.com.gabrielspassos.characters.controllers.dto.CharacterDTO;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CharacterApplication.class})
public class TestConfig {

    protected CharacterDTO createCharacterDTO(String charClass, String charName) {
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setCharClass(charClass);
        characterDTO.setName(charName);
        return characterDTO;
    }

    protected CharClassDTO createCharClassDTO(String charClass) {
        CharClassDTO charClassDTO = new CharClassDTO();
        charClassDTO.setCharacterClassName(charClass);
        return charClassDTO;
    }
}