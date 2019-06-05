package ru.harry.Entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN(0), ROLE_USER(1);

  Role(int i) {  }
  @Override
  public String toString() {
    return name();
  }
  public String getAuthority() {
    return name();
  }


}
