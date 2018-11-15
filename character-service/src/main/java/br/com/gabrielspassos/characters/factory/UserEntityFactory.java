package br.com.gabrielspassos.characters.factory;

import br.com.gabrielspassos.characters.client.dto.CharacterDTO;
import br.com.gabrielspassos.characters.client.dto.UserDTO;
import br.com.gabrielspassos.characters.entities.CharacterEntity;
import br.com.gabrielspassos.characters.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserEntityFactory {

    @Autowired
    private ModelMapper modelMapper;

    public UserEntity buildUserEntity(UserDTO dto){
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setAccountType(dto.getAccountType());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        entity.setCharacters(convertToCharactersEntityList(dto.getCharacters()));
        return entity;
    }

    private List<CharacterEntity> convertToCharactersEntityList(List<CharacterDTO> characterDTOList) {
        return characterDTOList.stream()
                .map(this::convertToCharacterEntity)
                .collect(Collectors.toList());
    }

    private CharacterEntity convertToCharacterEntity(CharacterDTO characterDTO) {
        return modelMapper.map(characterDTO, CharacterEntity.class);
    }
}
