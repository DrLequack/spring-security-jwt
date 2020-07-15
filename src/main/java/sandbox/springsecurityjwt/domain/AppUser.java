package sandbox.springsecurityjwt.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "users_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "username")
  private String name;

  @Column(name = "password")
  private String password;

  @Column(name = "enabled")
  private boolean enabled;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  @Fetch(FetchMode.JOIN)
  private List<UserAuthority> userAuthorities;


}
