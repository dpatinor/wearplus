package com.wearplus.repository;

import com.wearplus.domain.Shopping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shopping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long> {

}
