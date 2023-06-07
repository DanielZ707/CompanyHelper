package com.example.backend.models.repository;

import com.example.backend.models.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM token WHERE  iduser= :idUser")
    void deleteTokens(@Param("idUser") Long idUser);
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.idUser= u.idUser\s
      where u.idUser = :id and (t.expired = false or t.revoked = false)\s
      """)


    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}