package com.sysout.buy_zone_api.repository;

import com.sysout.buy_zone_api.models.entities.User;
import com.sysout.buy_zone_api.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
            SELECT
                                            tb_user.id AS id,
                                            tb_user.email AS email,                       
                                            tb_user.password AS password,
            
                                            tb_user.enabled AS enabled,
                                            tb_user.account_non_locked AS accountNonLocked,
                                            tb_user.account_non_expired AS accountNonExpired,
                                            tb_user.credentials_non_expired AS credentialsNonExpired,
            
                                            role.id AS roleId,
                                            role.authority AS authority
            
                                        FROM tb_user
                                        INNER JOIN user_role ON tb_user.id = user_role.user_id
                                        INNER JOIN role ON role.id = user_role.role_id
                                        WHERE tb_user.email = :email;
            
            """)
    List<UserDetailsProjection> searchUserAndRoleByEmail(String email);
}
