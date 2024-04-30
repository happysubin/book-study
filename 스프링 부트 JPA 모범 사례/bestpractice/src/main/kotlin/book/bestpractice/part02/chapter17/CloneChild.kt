package book.bestpractice.part02.chapter17

import jakarta.persistence.*

@Entity
class CloneChild(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var parent: CloneParent? = null
) {

    constructor(child: CloneChild): this(null, child.name, null) {

    }
}