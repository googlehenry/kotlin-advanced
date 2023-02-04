package com.henry.dsl.functions


fun main() {
    var test = HighOrderFunctions()
    test.test()
    println(test.str_add2.invoke("zhang", "li"))
    test.print.invoke("ok")
    println(test.str_add3.invoke("hello", "world"))
    println(addUpByHOF("a", "b", test::add))
}

fun addUpByHOF(a: String, b: String, add: (a: String, b: String) -> String): String {
    return add.invoke(a, b);
}

/**
 * A higher-order function is a function that takes functions as parameters, or returns a function.
 * 将已经定义好的方法作为参数传递
 * 1.静态方法(不需要实例对象,相当于utils,helpers)
 * 2.同一个class的方法可以省略对象::method(..)
 * 3,另一个类实例方法::必须要把实例对象生成出来以instance::method(...)
 */
class HighOrderFunctions {

    fun test() {
        func_add.invoke(HighOrderFunctions(), HighOrderFunctions())
        println(str_add2.invoke("a", "b"))
    }

    val str_add: String.(other: String) -> String = { this + it } //是一个函数类型的变量;在一个string实例的context中,接受一个参数,返回string.
    val str_add2: (first: String, other: String) -> String = ::add //返回当前对象中的这个方法,需要一个对象invoke method(这里隐式传递)
    val str_add3: (first: String, other: String) -> String = OK::add
    val print: (text: String) -> Unit = System.out::print
    val func_add: HighOrderFunctions.(next: HighOrderFunctions) -> String = { add("zhangsna", it.javaClass.name) }

    fun add(textA: String, textB: String): String {
        return "$textA $textB"
    }

    companion object {
        fun addComp(textA: String, textB: String): String {
            return "$textA $textB $this"
        }
    }

    object OK {
        fun add(textA: String, textB: String): String {
            return "$textA $textB"
        }
    }
}