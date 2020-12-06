package com.henry.kotlin.coroutine

import kotlinx.coroutines.*

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    //使用interceptor会影响执行顺序，某种机制？

    var job = CoroutineScope(Dispatchers.Default).async {

        var a1 = async {
            println("a1 start")
            delay(1000)
            println("a1 end")
        }
        var a2 = async {
            println("a2 start")
            delay(2000)
            println("a2 end")
        }

        var a3 = async {
            println("a3 start")
            delay(1500)
            throw Exception("a3 error out")
            println("a3 end")
        }
        a1.await()
    }


}
