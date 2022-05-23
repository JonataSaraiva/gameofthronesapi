package com.own.gameofthronesapi.controller

import com.own.gameofthronesapi.controller.response.CharacterResponse
import com.own.gameofthronesapi.controller.response.Character
import io.github.serpro69.kfaker.faker
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.Random

@RestController("/")
class CharacterController {

    private val log = LoggerFactory.getLogger(this::class.java)
    @GetMapping("characters/{house}")
    fun characters(@PathVariable house: String) : CharacterResponse =
        faker { fakerConfig { random = Random() } }
            .gameOfThrones
            .let { got ->
                val characters = mutableListOf<Character>()

                for(i in 1.. 400) {
                    got.characters().takeIf { it.contains(house) }?.let {
                        characters.add(Character(name = it))
                    }
                }

                val membersEnlisted = characters.take(3)
                log.info("Enlisted $membersEnlisted to house $house")

                CharacterResponse(characters.take(3))
            }



}