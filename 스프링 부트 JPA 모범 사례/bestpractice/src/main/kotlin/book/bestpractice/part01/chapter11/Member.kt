package book.bestpractice.part01.chapter11

import jakarta.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

//    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    var book: Partner? = null,
)