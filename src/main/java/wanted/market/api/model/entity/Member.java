package wanted.market.api.model.entity;


import jakarta.persistence.*;
import lombok.*;
import wanted.market.api.model.type.RoleType;

@Entity
@Getter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long memberNo;

    @Column(name = "ID", nullable = false, unique = true)
    private String memberId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    @Column(name = "EMAIL", nullable = false)
    private String email;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "ROLE")
//    private RoleType role;


}
