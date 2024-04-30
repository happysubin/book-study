package book.bestpractice.part02.chapter16

import book.bestpractice.part01.AbstractServiceTest
import book.bestpractice.part02.chapter17.CloneChild
import book.bestpractice.part02.chapter17.CloneChildRepository
import book.bestpractice.part02.chapter17.CloneParent
import book.bestpractice.part02.chapter17.CloneParentRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional


class Chapter16  @Autowired constructor(
    private val cloneChildRepository: CloneChildRepository,
    private val cloneParentRepository: CloneParentRepository
): AbstractServiceTest() {


    @Test
    @Transactional
    fun cloneParent() {
        val parent = CloneParent(name = "name", genre = "awesome", age = 33)
        parent.children.add(CloneChild(name = "child", parent = parent))
        cloneParentRepository.save(parent)


        val clone = CloneParent(parent, false)
        clone.name ="name2"

        val result = cloneParentRepository.save(clone)
        println(result.name)
        println(parent.name)

        for (child in parent.children) {
            println(child.name)
        }

        println("--------------")

        for (child in result.children) {
            println(child.name)
        }

    }
}