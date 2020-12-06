package com.henry.kotlin.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

private fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")

/**
 * 协程调度器
 *
 *
[main] 0
[main] 2
[DefaultDispatcher-worker-1] 3
[main] 1
[myownT] 4
当 launch {...} 在不带参数的情况下使用时，它从外部的协程作用域继承上下文和调度器。在本例中，它继承于在主线程中中运行的 runBlocking 协程的上下文
Dispatchers.Unconfined 是一个特殊的调度器，看起来似乎也在主线程中运行，但实际上它是一种不同的机制，稍后将进行解释
在 GlobalScope 中启动协程时默认使用的调度器是 Dispatchers.default，并使用共享的后台线程池，因此 launch(Dispatchers.default){...} 与 GlobalScope.launch{...} 是使用相同的调度器
newSingleThreadContext 用于为协程专门创建一个新的线程来运行。专用线程是非常昂贵的资源。在实际的应用程序中，它必须在不再需要时使用 close 函数释放掉，或者存储在顶级变量中以此实现在整个应用程序中重用
作者：叶志陈
链接：https://www.imooc.com/article/302318?block_id=tuijian_wz
来源：慕课网
本文首次发布于慕课网 ，转载请注明出处，谢谢合作
 */
fun main() {
    log(0)

    runBlocking {

        launch {
            log(1)
        }

        launch(Dispatchers.Unconfined) {
            log(2)
        }
        launch(Dispatchers.Default) {
            log(3)
        }

        launch(newSingleThreadContext("myownT")) {
            log(4)
        }

    }
}