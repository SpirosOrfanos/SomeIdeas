package com.etraveli.cardcost.domain.dbo;

import static com.etraveli.cardcost.enums.Permission.BO_CREATE;
import static com.etraveli.cardcost.enums.Permission.BO_DELETE;
import static com.etraveli.cardcost.enums.Permission.BO_READ;
import static com.etraveli.cardcost.enums.Permission.BO_UPDATE;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.etraveli.cardcost.enums.Permission;

public enum Role {

  USER(Collections.emptySet()), 
  BO(Set.of(BO_CREATE, BO_DELETE, BO_UPDATE, BO_READ)),
  ;

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }
  
}
