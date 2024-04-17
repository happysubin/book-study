package book.bestpractice.part01.chapter11

import book.bestpractice.part01.AbstractServiceTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class Chapter11ServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val partnerRepository: PartnerRepository,
    private val chapter11Service: Chapter11Service
): AbstractServiceTest() {


    @Test
    fun test1() {
        val savedMember = memberRepository.save(Member(name = "eb"))

        chapter11Service.newPartnerOfMember(savedMember.id!!)
    }

    @Test
    fun test2() {
        val savedMember = memberRepository.save(Member(name = "eb"))
        chapter11Service.fetchBookByAuthor(savedMember.id!!)
    }

    @Test
    fun test3() {
        val savedMember = memberRepository.save(Member(name = "eb"))
        chapter11Service.fetchBookByAuthorId(savedMember.id!!)
    }
}