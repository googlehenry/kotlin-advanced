package com.henry.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

//Question 1: quick return?
//Question 2: can not catch exceptions?
//Question 3: not cancelling brothers? same context or not?  wait on first?
fun main() = runBlocking<Unit> {
    //使用interceptor会影响执行顺序，某种机制？

    var job = CoroutineScope(Dispatchers.Default).async {

        var a1 = async {
            println("a1 start")
            printLoop("a1", 1000000)
            println("a1 end")
        }
        var a2 = async {
            println("a2 start")
            printLoop("a2", 2000000)
            println("a2 end")
        }
        var a3 = async {
            println("a3 start")
            printLoop("a3", 1500000)
            throw Exception("a3 error out")
            println("a3 end")
        }
        try {
            a3.await()
        } catch (ex: Throwable) {
            println("had error:${ex.message}")
        }
    }

    Thread.sleep(10000)
}

fun printLoop(name: String, to: Long) {
    for (id in 0..to) {
        if (id % to == 0L) {
            println("$name printLoop:$id")
        }
    }
}

//Answer:
//to q1: Pay attention to the job.join(), defferred.await() at the TOP level
//to q2: capture true error inside launch, (capture cancelling on job.join()), or true error on deffered.await()
//to q3: await on parent or child lead to different cancelling / quick exception