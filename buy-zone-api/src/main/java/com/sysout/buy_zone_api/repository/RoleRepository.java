package com.sysout.buy_zone_api.repository;

import com.sysout.buy_zone_api.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {

    Role findByAuthority(String authority);
}
