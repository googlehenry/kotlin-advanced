package com.henry.kotlin.coroutine

import kotlinx.coroutines.*

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

fun main() {

    runBlocking {

        GlobalScope.launch() {
            log(1)
            val job = async {
                log(2)
                //cancel("in job1")
                delay(1000)
                log(3)
                "Hello11"
            }
            val job2 = async {
                log(2)
                delay(1100)
                log(3)
                "Hell2o22"
            }
            log(4)
            val result = job.await()
            log("5. $result")
        }

//        for (i in 0..10000000) {
//            if (i % 10000000 == 0) log("a_$i")
//        }
        log(6)
    }

    Thread.sleep(3000)
}