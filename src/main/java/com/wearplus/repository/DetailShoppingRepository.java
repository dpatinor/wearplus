package com.wearplus.repository;

import com.wearplus.domain.DetailShopping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DetailShopping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailShoppingRepository extends JpaRepository<DetailShopping, Long> {

}
