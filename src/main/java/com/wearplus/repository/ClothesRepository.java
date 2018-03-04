package com.wearplus.repository;

import com.wearplus.domain.Clothes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Clothes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

}
