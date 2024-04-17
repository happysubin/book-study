package book.bestpractice.part01.chapter11

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PartnerRepository: JpaRepository<Partner, Long> {

    @Query("select p from Partner p where p.member = :member ")
    fun findByMember(member: Member)
}