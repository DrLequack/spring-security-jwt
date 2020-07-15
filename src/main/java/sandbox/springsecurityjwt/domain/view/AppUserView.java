package sandbox.springsecurityjwt.domain.view;

import java.util.List;

public interface AppUserView {
 public String getName();
 public String getPassword();
 public List<UserAuthorityView> getUserAuthorities();
}
