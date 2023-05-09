package com.example.backend.repository;

import com.example.backend.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM app_user WHERE id_user= :idUser")
    UserEntity findByID(@Param("idUser") Long idUser);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE app_user SET password= :newPassword WHERE id_user= :idUser")
    void changeUserPassword(@Param("idUser") Long idUser, @Param("newPassword") String newPassword);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM app_user WHERE  email= :email")
    void deleteAccount(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE app_user SET last_login_date= :lastLoginDate WHERE id_user= :idUser")
    void changeLoginDate(@Param("idUser") Long idUser, @Param("lastLoginDate") Date lastLoginDate);
}