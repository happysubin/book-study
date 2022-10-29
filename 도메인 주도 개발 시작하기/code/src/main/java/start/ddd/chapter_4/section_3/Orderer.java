package start.ddd.chapter_4.section_3;

import javax.persistence.*;

@Embeddable
public class Orderer {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "orderer_id"))
    )
    private MemberId memberId;

    @Column(name = "orderer_name")
    private String name;

    @Convert(converter = EmailSetConverter.class)
    private EmailSet emailSet;
}
