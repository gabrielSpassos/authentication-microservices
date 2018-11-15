package br.com.gabrielspassos.users.controllers;

import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.controllers.dto.UserDTO;
import br.com.gabrielspassos.users.entities.UserEntity;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
public class UserController implements BaseVersion {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        return Stream.of(userDTO)
                .map(this::convertToEntity)
                .map(entity -> producerTemplate.requestBody("direct:createUser", entity))
                .map(response -> convertToResponseUserDTO((UserEntity) response))
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @RequestBody @Valid UserDTO userDTO) throws Throwable {
        try {
            return Stream.of(userDTO)
                    .map(user -> convertToEntity(user, id))
                    .map(entity -> producerTemplate.requestBody("direct:updateUser", entity))
                    .map(response -> convertToResponseUserDTO((UserEntity) response))
                    .map(ResponseEntity::ok)
                    .findFirst()
                    .get();
        } catch (CamelExecutionException e) {
            throw ExceptionUtils.getRootCause(e);
        }
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) throws Throwable {
        try {
            return Stream.of(id)
                    .map(userId -> producerTemplate.requestBody("direct:getUserById", userId))
                    .map(response -> convertToResponseUserDTO((UserEntity) response))
                    .map(ResponseEntity::ok)
                    .findFirst()
                    .get();
        } catch (CamelExecutionException e) {
            throw ExceptionUtils.getRootCause(e);
        }
    }

    @GetMapping(value = "/users/login/{login}")
    public ResponseEntity<?> getUserByLogin(@PathVariable String login) throws Throwable {
        try {
            return Stream.of(login)
                    .map(userLogin -> producerTemplate.requestBody(
                            "direct:getUserByLoginAndPassword",
                            userLogin)
                    ).map(response -> convertToResponseUserDTO((UserEntity) response))
                    .map(ResponseEntity::ok)
                    .findFirst()
                    .get();
        } catch (CamelExecutionException e) {
            throw ExceptionUtils.getRootCause(e);
        }
    }

    private UserEntity convertToEntity(UserDTO userDTO, String id) {
        UserEntity userEntity = convertToEntity(userDTO);
        userEntity.setId(id);
        return userEntity;
    }

    private UserEntity convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    private ResponseUserDTO convertToResponseUserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, ResponseUserDTO.class);
    }
}
