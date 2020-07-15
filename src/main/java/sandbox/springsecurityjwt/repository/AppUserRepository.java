package sandbox.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandbox.springsecurityjwt.domain.AppUser;
import sandbox.springsecurityjwt.domain.view.AppUserView;

public interface AppUserRepository  extends JpaRepository<AppUser, Long>{


  @Query("select au"
      + " from AppUser au"
      + " join fetch au.userAuthorities"
      + " where au.name = :userName")
  public AppUser findByName(@Param("userName") String userName);


  public AppUserView getAppUserByName(@Param("userName") String userName);

}
