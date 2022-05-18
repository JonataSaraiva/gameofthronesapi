package com.own.gameofthronesapi.controller

import io.github.serpro69.kfaker.faker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("/")
class CharacterController {

    @GetMapping("characters/{house}")
    fun character(@PathVariable house: String) : List<String> =
        faker { fakerConfig { random = Random() } }
            .gameOfThrones
            .let { got ->
                val characters = mutableListOf<String>()

                for(i in 1.. 250) {
                    got.characters().takeIf { it.contains(house) }?.let {
                        characters.add(it)
                    }
                }

                characters
            }

}