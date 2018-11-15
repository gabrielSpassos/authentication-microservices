package br.com.gabrielspassos.characters.routes;

import br.com.gabrielspassos.characters.client.UserClient;
import br.com.gabrielspassos.characters.client.dto.UserDTO;
import br.com.gabrielspassos.characters.entities.CharacterEntity;
import br.com.gabrielspassos.characters.entities.UserEntity;
import br.com.gabrielspassos.characters.factory.UserEntityFactory;
import br.com.gabrielspassos.characters.repositories.UserRepository;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterRoute extends RouteBuilder {

    @Autowired
    private UserClient userClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEntityFactory userEntityFactory;

    @Override
    public void configure() throws Exception {
        from("direct:createCharacter")
                .routeId("createCharacter")
                .process(this::getUserById)
                .process(this::fetchUserEntity)
                .process(this::enhanceUserWithCaracter)
                .process(this::saveUserCharacter)
                .end();

        from("direct:updateCharacter")
                .routeId("updateCharacter")
                .process(this::getUserById)
                .process(this::fetchUserEntity)
                .process(this::updateUserCharacterByCharName)
                .process(this::saveUserCharacter)
                .process(this::fetchUpdatedCharacter)
                .end();
    }

    private void getUserById(Exchange exchange) {
        String id = exchange.getIn().getHeader("id", String.class);
        UserDTO userDTO = userClient.getUserById(id);
        exchange.setProperty("userDTO", userDTO);
    }

    private void fetchUserEntity(Exchange exchange) {
        UserDTO userDTO = exchange.getProperty("userDTO", UserDTO.class);
        UserEntity userEntity = userEntityFactory.buildUserEntity(userDTO);
        exchange.setProperty("userEntity", userEntity);
    }

    private void enhanceUserWithCaracter(Exchange exchange) {
        UserEntity userEntity = exchange.getProperty("userEntity", UserEntity.class);
        CharacterEntity characterEntity = exchange.getIn().getBody(CharacterEntity.class);
        userEntity.getCharacters().add(characterEntity);
        exchange.setProperty("userEntity", userEntity);
    }

    private void saveUserCharacter(Exchange exchange) {
        UserEntity userEntity = exchange.getProperty("userEntity", UserEntity.class);
        userRepository.save(userEntity);
    }

    private void updateUserCharacterByCharName(Exchange exchange) {
        String charName = exchange.getIn().getHeader("name", String.class);
        String charClass = exchange.getIn().getHeader("charClass", String.class);
        UserEntity userEntity = exchange.getProperty("userEntity", UserEntity.class);

        CharacterEntity characterEntity = findCharacterByName(userEntity, charName);
        userEntity = updateUserCharacterList(userEntity, characterEntity, charClass);

        exchange.setProperty("userEntity", userEntity);
    }

    private void fetchUpdatedCharacter(Exchange exchange) {
        UserEntity userEntity = exchange.getProperty("userEntity", UserEntity.class);
        String charName = exchange.getIn().getHeader("name", String.class);

        CharacterEntity characterEntity = findCharacterByName(userEntity, charName);
        exchange.getIn().setBody(characterEntity, CharacterEntity.class);
    }

    private CharacterEntity findCharacterByName(UserEntity userEntity, String charName) {
        return userEntity.getCharacters().stream()
                .filter(charEntity -> charEntity.getName().equals(charName))
                .findFirst()
                .get();
    }

    private UserEntity updateUserCharacterList(UserEntity userEntity,
                                                    CharacterEntity characterEntity,
                                                    String charNewClass) {
        userEntity.getCharacters().remove(characterEntity);
        characterEntity.setCharClass(charNewClass);
        userEntity.getCharacters().add(characterEntity);
        return userEntity;
    }
}
