package com.example.backend.models.repository;

import com.example.backend.models.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE iduser= :idUser")
    User findByID(@Param("idUser") Long idUser);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE users SET password= :newPassword WHERE email= :email")
    void changeUserPassword(@Param("email") String email, @Param("newPassword") String newPassword);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE idteam= :idTeam")
    List<User> findUsersFromTeam(@Param("idTeam") Long idTeam);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE  email= :email")
    void deleteAccount(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET lastlogindate= :lastLoginDate WHERE email= :email")
    void changeLoginDate(@Param("email") String email, @Param("lastLoginDate") Date lastLoginDate);


}