package br.com.gabrielspassos.characters.controllers;

import br.com.gabrielspassos.characters.controllers.dto.CharClassDTO;
import br.com.gabrielspassos.characters.controllers.dto.CharacterDTO;
import br.com.gabrielspassos.characters.entities.CharacterEntity;
import org.apache.camel.ProducerTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class CharacterController {

    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/users/{id}/characters")
    public ResponseEntity<?> createCharacter(@PathVariable String id,
                                             @RequestBody @Valid CharacterDTO characterDTO) {
        return Stream.of(characterDTO)
                .map(this::convertToCharacterEntity)
                .map(characterEntity -> producerTemplate.requestBodyAndHeaders(
                        "direct:createCharacter",
                        characterEntity,
                        createHeaders(id, null, null)
                ))
                .map(response -> convertToCharacterDTO((CharacterEntity) response))
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @PutMapping(value = "/users/{id}/characters/{name}")
    public ResponseEntity<?> updateCharacter(@PathVariable String id,
                                             @PathVariable String name,
                                             @RequestBody @Valid CharClassDTO charClassDTO) {
        return Stream.of(charClassDTO)
                .map(classDTO -> producerTemplate.requestBodyAndHeaders(
                        "direct:updateCharacter",
                        null,
                        createHeaders(id, name, classDTO.getCharacterClassName())
                ))
                .map(response -> convertToCharacterDTO((CharacterEntity) response))
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    private Map<String, Object> createHeaders(String id, String name, String charClass) {
        Map<String, Object> routeHeaders = new HashMap<>();
        routeHeaders.put("id", id);
        routeHeaders.put("name", name);
        routeHeaders.put("charClass", charClass);
        return routeHeaders;
    }

    private CharacterEntity convertToCharacterEntity(CharacterDTO characterDTO) {
        return modelMapper.map(characterDTO, CharacterEntity.class);
    }

    private CharacterDTO convertToCharacterDTO(CharacterEntity characterEntity) {
        return modelMapper.map(characterEntity, CharacterDTO.class);
    }
}
