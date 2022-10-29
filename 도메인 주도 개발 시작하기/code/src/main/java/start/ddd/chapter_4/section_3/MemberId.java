package start.ddd.chapter_4.section_3;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private Long id;
}
