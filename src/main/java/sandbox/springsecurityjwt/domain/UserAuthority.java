package sandbox.springsecurityjwt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "authorities")
public class UserAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_generator")
  @SequenceGenerator(name = "authorities_generator", sequenceName = "authorities_sequence", allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @Fetch(FetchMode.JOIN)
  private AppUser user;

  @Column(name = "authority")
  private String authority;

}
