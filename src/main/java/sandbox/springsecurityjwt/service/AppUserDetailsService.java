package sandbox.springsecurityjwt.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sandbox.springsecurityjwt.domain.AppUser;
import sandbox.springsecurityjwt.domain.UserAuthority;
import sandbox.springsecurityjwt.domain.view.AppUserView;
import sandbox.springsecurityjwt.domain.view.UserAuthorityView;
import sandbox.springsecurityjwt.repository.AppUserRepository;
import sandbox.springsecurityjwt.repository.UserAuthorityRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private UserAuthorityRepository userAuthorityRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    AppUserView user = appUserRepository.getAppUserByName(userName);
    return User.builder().username(user.getName())
        .password(user.getPassword())
        .authorities(user.getUserAuthorities().stream().map(UserAuthorityView::getAuthority).toArray(String[]::new))
        .build();
  }

  public UserDetails loadFullUserByUsername(String userName) throws UsernameNotFoundException {
    AppUser appUser = appUserRepository.findByName(userName);
    return User.builder().username(appUser.getName())
        .password(appUser.getPassword())
        .authorities(appUser.getUserAuthorities().stream().map(UserAuthority::getAuthority).toArray(String[]::new))
        .build();
  }

  @PostConstruct
  public void importDataOnStartup() {
    if (appUserRepository.count() == 0) {

      AppUser appUser = appUserRepository.save(AppUser.builder()
          .name("admin")
          .password(passwordEncoder.encode("admin"))
          .enabled(true)
          .build());

      userAuthorityRepository.save(UserAuthority.builder()
          .authority("ADMIN")
          .user(appUser)
          .build());

       appUser = appUserRepository.save(AppUser.builder()
          .name("user")
          .password(passwordEncoder.encode("pass"))
           .enabled(true)
          .build());

      userAuthorityRepository.save(UserAuthority.builder()
          .authority("USER")
          .user(appUser)
          .build());
    }
  }

}
