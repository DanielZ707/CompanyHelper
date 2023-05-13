package com.example.backend.repository;

import com.example.backend.entity.Construction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConstructionRepo extends JpaRepository<Construction, Long> {

    Construction findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM constructions WHERE idconstruction= :idConstruction")
    Construction findByID(@Param("idConstruction") Long idConstruction);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE constructions SET name= :newConstructionName WHERE name= :name")
    void changeConstructionName(@Param("name") String name, @Param("newConstructionName") String newConstructionName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE constructions SET deadlineDay= :newDeadline WHERE name= :name")
    void changeConstructionDeadline(@Param("name") String name, @Param("newDeadline") Date newDeadline);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM constructions WHERE  name= :name")
    void deleteConstruction(@Param("name") String name);

}
