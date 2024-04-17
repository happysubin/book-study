package book.bestpractice.part01.chapter11

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class Chapter11Service (
    private val partnerRepository: PartnerRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun newPartnerOfMember(id: Long) {
        val member = memberRepository.findById(id).orElseThrow()
        val partner = Partner(age = 23)
        partner.member = member

        partnerRepository.save(partner)
    }

    @Transactional
    fun fetchBookByAuthor(id: Long) {
        val member = memberRepository.findById(id).orElseThrow()
        partnerRepository.findByMember(member)
    }

    @Transactional
    fun fetchBookByAuthorId(id: Long) {
        val member = memberRepository.findById(id).orElseThrow()
        partnerRepository.findById(member.id)
    }
}