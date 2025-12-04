package com.sysout.buy_zone_api.repository;

import com.sysout.buy_zone_api.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository <Address, Long> {

    @Query("SELECT obj FROM Address obj " +
            "WHERE obj.user.id = :userId")
    List<Address> searchAddressByUserId(Long userId);

}
