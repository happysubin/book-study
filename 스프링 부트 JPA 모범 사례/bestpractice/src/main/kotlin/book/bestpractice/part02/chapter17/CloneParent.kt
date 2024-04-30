package book.bestpractice.part02.chapter17

import jakarta.persistence.*

@Entity
class CloneParent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,
    val genre: String,
    val age: Int,


    @OneToMany(mappedBy = "parent", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val children: MutableList<CloneChild> = ArrayList()

) {

    constructor(parent: CloneParent, cloneChildren: Boolean) : this(null, parent.name, parent.genre, parent.age) {
        //this.children.addAll(parent.children)

        if(!cloneChildren) {
            children.addAll(parent.children)
            return
        }

        //복제
        for (child in parent.children) {
            addChild(CloneChild(child))
        }
    }

    private fun addChild(cloneChild: CloneChild) {
        this.children.add(cloneChild)
        cloneChild.parent = this
    }
}