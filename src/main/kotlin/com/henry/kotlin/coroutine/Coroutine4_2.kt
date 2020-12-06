package com.henry.kotlin.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

fun main() {



    runBlocking {

        //使用interceptor会影响执行顺序，某种机制？
        GlobalScope.launch(MyContinuationInterceptor()) {
            log(1)
            val job = async {
                log(2)
                delay(1000)
                log(3)
                "Hello"
            }

            log(4)
//            throw Exception("broken")
            val result = job.await()
            log("5. $result")
        }

        log(6)
    }

    Thread.sleep(3000)
}

class MyContinuationInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>) = MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        log("<MyContinuation> $result")
        continuation.resumeWith(result)
    }
}