package com.henry.dsl.functions

fun main() {
    var test = WhoLovesWhom()
    test.test()
}

/**
 * infix:
 * Functions marked with the infix keyword can also be called using the infix notation
 * (omitting the dot and the parentheses for the call).
 * calling the function using the infix notation is the same as a normal function call
 */
class WhoLovesWhom {

    fun test() {
        say(Person("zhangsan", 24) loves Person("lisi", 22))
        say("li4" loves "wang5")
        say("I" love "my parents.")
        say("you" love "your hubby.")
        say("lala".loves("qiaqia"))
    }

    private fun say(sth: String) = println(sth)
    private infix fun String.loves(whom: String): String = "$this loves $whom"
    private infix fun Person.loves(whom: Person): String = "$this loves $whom"
    private infix fun String.love(whom: String): String = "$this love $whom"

    class Person(val name: String, val age: Int) {
        override fun toString(): String {
            return "$name [$age]"
        }
    }
}