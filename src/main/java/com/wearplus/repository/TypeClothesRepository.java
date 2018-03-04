package com.wearplus.repository;

import com.wearplus.domain.TypeClothes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeClothes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeClothesRepository extends JpaRepository<TypeClothes, Long> {

}
