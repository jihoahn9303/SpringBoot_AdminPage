package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  // == table
// N쪽 class의 toString()과, 1쪽 class의 toString()이 상호 참조를 하면서 stack overflow 현상이 일어나는 것을 방지하는 어노테이션
// @ToString(exclude = {...})
@ToString(exclude = {"orderGroupList"})
/*@Builder와 @Accessors
  객체 생성자 호출 시, 일부의 속성들만 값을 초기화 시킬 수 도 있다. 원래의 경우, 해당 클래스에서 인자의 갯수에 따라 서로 다른 생성자를 지정해야 한다.
  그러나, Builder 또는 Accessor를 활용하면 이러한 번거로움을 없애줄 수 있다. 사용 패턴은 UserRepositoryTest.java 파일을 참고!!
  참고로, Builder 패턴은 객체 생성 시, Accessor를 통한 chain 패턴은 주로 객체 속성을 갱신할 때 이용한다.
 */
@Builder
@Accessors(chain = true)
public class User extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;  // REGISTERED / UNREGISTERED / WAITING

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    // 1 : N(order_detail)
    // LAZY = 지연로딩 : 따로 연관 쿼리를 설정하지 않는 이상 해당 객체에 대응되는 테이블에서만 내용 참조
    // EAGER = 즉시로딩 : 연관된 모든 테이블을 조인하여 결과를 출력 (1 : 1 대응관계에서 추천)

    // LAZY = SELECT * FROM user where id = ?

    // EAGER =
    // item_id = order_detail.item_id
    // user_id = order_detail.user_id
    // where user.id = ?
    // JOIN : from order_detail orderdetai0_ left outer join item item1_ on orderdetai0_.item_id=item1_.id where orderdetai0_.user_id=?

    // @OneToMany : mappedBy 파라미터 값은 반드시 N에 해당하는 User type의 변수명과 동일해야 한다!!!!
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;
}
