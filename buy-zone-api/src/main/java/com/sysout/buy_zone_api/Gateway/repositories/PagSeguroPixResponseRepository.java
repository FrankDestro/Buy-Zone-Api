package com.sysout.buy_zone_api.Gateway.repositories;

import com.sysout.buy_zone_api.Gateway.response.PagSeguroPixResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagSeguroPixResponseRepository extends JpaRepository <PagSeguroPixResponse, String> {
}
