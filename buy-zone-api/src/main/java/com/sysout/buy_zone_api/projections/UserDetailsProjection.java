package com.sysout.buy_zone_api.projections;

public interface UserDetailsProjection {

    Long getId();
    String getEmail();   // username real
    String getPassword();
    Boolean getEnabled();
    Boolean getAccountNonLocked();
    Boolean getAccountNonExpired();
    Boolean getCredentialsNonExpired();
    Long getRoleId();
    String getAuthority();
}
