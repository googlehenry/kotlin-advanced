package com.henry.dsl.extentions

import java.time.LocalDate
import java.time.Period

fun main() {
    var test = FewDaysAgo2()
    test.test()
}

/*
扩展属性:
1.Int加一个days扩展property返回一个当前int_value表示的duration的区间Period对象
2.Period加一个ago扩展property返回一个当前时间减去当前period的时间LocalDate对象.
Since extensions do not actually insert members into classes,
there's no efficient way for an extension property to have a backing field.
This is why initializers are not allowed for extension properties.
Their behavior can only be defined by explicitly providing getters/setters.
 */
class FewDaysAgo2 {
    fun test() {
        println(this.javaClass)
        println(3.days.ago)
        println(4.days.ago)
        println(5.days.ago)
        println(6.days.ago)
        println(show(7))
    }

    private fun show(few: Int) = few.days.ago
    private val Int.days: Period get() = Period.ofDays(this)
    private val Period.ago: LocalDate get() = LocalDate.now() - this

}
