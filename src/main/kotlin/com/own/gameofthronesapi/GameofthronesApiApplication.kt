package com.own.gameofthronesapi

import eu.rekawek.toxiproxy.ToxiproxyClient
import eu.rekawek.toxiproxy.model.ToxicDirection.DOWNSTREAM
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameofthronesApiApplication : CommandLineRunner {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${toxiproxy.name}")
    private val name: String? = null

    @Value("\${toxiproxy.latency}")
    private val latency: Long? = null

    @Value("\${toxiproxy.proxed-endpoint}")
    private val proxedEndpoint: String? = null

    @Value("\${toxiproxy.original-endpoint}")
    private val originalEndpoint: String? = null

    override fun run(vararg args: String?) {
        val client = ToxiproxyClient("127.0.0.1", 8474)
        val proxy = client.getProxyOrNull(name)

        if(proxy == null)
            client.createProxy(name, proxedEndpoint, originalEndpoint)
                ?.toxics()
                ?.latency("my-latency-toxic", DOWNSTREAM, latency ?: 1000L)
                ?.jitter = 15

        log.info("ToxiProxy is running")
    }

}

fun main(args: Array<String>) {
    runApplication<GameofthronesApiApplication>(*args)
}