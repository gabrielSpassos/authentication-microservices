package br.com.gabrielspassos.characters.repositories;

import br.com.gabrielspassos.characters.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

}
