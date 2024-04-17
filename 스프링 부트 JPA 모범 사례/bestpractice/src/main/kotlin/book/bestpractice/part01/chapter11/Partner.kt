package book.bestpractice.part01.chapter11

import jakarta.persistence.*

@Entity
class Partner(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    val age: Int
)