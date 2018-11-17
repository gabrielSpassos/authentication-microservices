package br.com.gabrielspassos.users.routes;

import br.com.gabrielspassos.users.entities.UserEntity;
import br.com.gabrielspassos.users.repositories.UserRepository;
import com.google.common.collect.Lists;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRoute extends RouteBuilder {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void configure() throws Exception {
        from("direct:createUser")
                .routeId("createUser")
                .process(this::initializeCharacterList)
                .process(this::saveUser)
                .end();

        from("direct:updateUser")
                .routeId("updateUser")
                .process(this::getUserToUpdate)
                .validate(this::isValidUser)
                .process(this::updateUserFields)
                .process(this::saveUser)
                .end();

        from("direct:getUserById")
                .routeId("getUserById")
                .process(this::getUserById)
                .validate(this::isUserPresent)
                .end();

        from("direct:getUserByLoginAndPassword")
                .routeId("getUserByLoginAndPassword")
                .choice()
                    .when(this::isPasswordEmpty)
                        .throwException(new IllegalArgumentException("You must inform the password"))
                    .otherwise()
                        .process(this::getUserByLoginAndPassword)
                        .validate(this::isUserPresent)
                .endChoice()
                .end();
    }

    private void initializeCharacterList(Exchange exchange) {
        UserEntity userEntity = exchange.getIn().getBody(UserEntity.class);
        userEntity.setCharacters(Lists.newArrayList());
        exchange.getIn().setBody(userEntity, UserEntity.class);
    }

    private void saveUser(Exchange exchange) {
        UserEntity userEntity = exchange.getIn().getBody(UserEntity.class);
        userRepository.save(userEntity);
    }

    private void getUserToUpdate(Exchange exchange) {
        UserEntity newUser = exchange.getIn().getBody(UserEntity.class);
        UserEntity oldUser = userRepository.findById(newUser.getId());
        exchange.setProperty("oldUser", oldUser);
    }

    private Boolean isValidUser(Exchange exchange) {
        UserEntity oldUser = exchange.getProperty("oldUser", UserEntity.class);
        return Objects.nonNull(oldUser);
    }

    private void updateUserFields(Exchange exchange) {
        UserEntity oldUser = exchange.getProperty("oldUser", UserEntity.class);
        UserEntity newUser = exchange.getIn().getBody(UserEntity.class);
        oldUser.setAccountType(newUser.getAccountType());
        oldUser.setStatus(newUser.getStatus());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setPassword(newUser.getPassword());
        exchange.getIn().setBody(oldUser);
    }

    private void getUserById(Exchange exchange) {
        String id = exchange.getIn().getBody(String.class);
        UserEntity userEntity = userRepository.findById(id);
        exchange.getIn().setBody(userEntity, UserEntity.class);
    }

    private Boolean isPasswordEmpty(Exchange exchange) {
        String password = exchange.getIn().getHeader("password", String.class);
        return StringUtils.isBlank(password);
    }

    private void getUserByLoginAndPassword(Exchange exchange) {
        String login = exchange.getIn().getBody(String.class);
        String password = exchange.getIn().getHeader("password", String.class);
        UserEntity userEntity = userRepository.findByLoginAndPassword(login, password);
        exchange.getIn().setBody(userEntity, UserEntity.class);
    }

    private Boolean isUserPresent(Exchange exchange) {
        UserEntity userEntity = exchange.getIn().getBody(UserEntity.class);
        return Objects.nonNull(userEntity);
    }
}
