package com.example.weathertask.web.security.expression;


import com.example.weathertask.domain.user.Role;
import com.example.weathertask.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

  public boolean canAccessUser(Long id) {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();

    JwtEntity user = (JwtEntity) authentication.getPrincipal();
    Long userId = user.getId();

    return hasAnyRole(authentication, Role.ROLE_EDITOR);

  }

  private boolean hasAnyRole(final Authentication authentication,
      final Role... roles) {
    for (Role role : roles) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
      if (authentication.getAuthorities().contains(authority)) {
        return true;
      }
    }
    return false;
  }
}
