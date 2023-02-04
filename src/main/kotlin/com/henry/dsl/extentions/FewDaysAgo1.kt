package com.henry.dsl.extentions

import java.time.LocalDate
import java.time.Period

fun main() {
    var test = FewDaysAgo1()
    test.test()
}

/*
扩展函数:
1.Int加一个days()扩展func返回一个当前int_value表示的duration的区间Period对象
2.Period加一个ago()扩展func返回一个当前时间减去当前period的时间LocalDate对象.
Extensions do not actually modify the classes they extend.
By defining an extension, you are not inserting new members into a class,
only making new functions callable with the dot-notation on variables of this type.
 */
class FewDaysAgo1 {
    fun test() {
        println(this.javaClass)
        println(3.days().ago())
        println(4.days().ago())
        println(5.days().ago())
        println(6.days().ago())
        println(show(7))
    }

    private fun show(few: Int) = few.days().ago()
    private fun Int.days(): Period = Period.ofDays(this) //this->当前object
    private fun Period.ago(): LocalDate = LocalDate.now() - this
}
