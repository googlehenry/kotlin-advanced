package com.henry.kotlin.coroutine

import kotlinx.coroutines.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

/**
 * 两个协程中发生异常时，相互影响。线程池，有线程切换。
 */
fun main() {
    //Main: step 1 in sync: invoke static
    log("1-start")

    //Main: step 2 in sync: invoke static
    runBlocking {
        var p = CoroutineScope(Dispatchers.IO).async(start = CoroutineStart.DEFAULT) {

            for (tt in 0..1) {
                log("tt:$tt")
                var a = async(start = CoroutineStart.DEFAULT) {
                    for (id in 0..1000000) {
                        if (id % 100000 == 0) {
                            log("a[$tt]:$id")
                        }
                        if (id > 500000 && Math.random() < 0.01) {
                            throw Exception("a[$tt]:has error")
                        }
                    }
                }
            }
            var b = async(start = CoroutineStart.DEFAULT) {
                for (id in 0..10) {
                    if (id % 1 == 0) {
                        if (isActive) log("b:$id")
                    }
                }
            }


//
//            a.await()
//            b.await()
            log("parent is done")
        }

        p.await()


    }
    //Main: step 3 in sync: invoke static
    log("1-end")
}