package book.bestpractice.part03.chapter23

import jakarta.persistence.*

class Author (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Lob
    @Basic(fetch = FetchType.LAZY) //속성 지연 로딩
    val avatar: ByteArray
){

}