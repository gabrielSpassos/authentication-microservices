package br.com.gabrielspassos.users.repositories;

import br.com.gabrielspassos.users.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findById(String id);
}
