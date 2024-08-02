package book.bestpractice.lab

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LabController {

    @PostMapping("/json")
    fun json(@RequestBody request: Request): Request {
        println("request = ${request}")
        return request
    }
}

data class Request(
    var message: String,
    val age: Int
)