package com.example.backend.models.repository;

import com.example.backend.models.entity.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {
    Team findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM teams WHERE idteam= :idTeam")
    Team findByID(@Param("idTeam") Long idTeam);

    @Query(nativeQuery = true, value = "SELECT * FROM teams WHERE idconstruction= :idConstruction")
    Team findByIdCon(@Param("idConstruction") Long idConstruction);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE teams SET name= :newTeamName WHERE name= :name")
    void changeTeamName(@Param("name") String name, @Param("newTeamName") String newTeamName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE teams SET idconstruction= :idConstruction WHERE name= :name")
    void changeConstruction(@Param("name") String name, @Param("idConstruction") Long idConstruction);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM teams WHERE  name= :name")
    void deleteTeam(@Param("name") String name);
}

