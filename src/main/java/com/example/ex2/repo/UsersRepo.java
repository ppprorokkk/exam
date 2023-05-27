package com.example.ex2.repo;

import com.example.ex2.models.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, Long> {

    //boolean findByUsername(String Email);
    @Modifying
    @Query(value="select * from users p where p.email = :email", nativeQuery=true)
    Iterable<Users> FingByName(String email);

}
