package com.henry.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    //使用interceptor会影响执行顺序，某种机制？

    var job = CoroutineScope(Dispatchers.Default).launch {
        var a1 = launch {
            println("a1 start")
            printLoop("a1", 1000000)
            println("a1 end")
        }
        var a2 = launch {
            println("a2 start")
            printLoop("a2", 20000000)
            println("a2 end")
        }
        var a3 = launch {
            println("a3 start")
            printLoop("a3", 1500000)
            throw Exception("a3 error out")
            println("a3 end")
        }

        try {
            a3.join()
        } catch (ex: Throwable) {
            println("had error:${ex.message}")
        }

    }

    Thread.sleep(10000)
}
