package com.etraveli.cardcost.service.external;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.etraveli.cardcost.domain.dbo.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
