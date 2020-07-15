package sandbox.springsecurityjwt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.springsecurityjwt.domain.UserAuthority;

public interface UserAuthorityRepository  extends JpaRepository<UserAuthority, Long> {

  List<UserAuthority> findAllByUserName(String userName);
}
