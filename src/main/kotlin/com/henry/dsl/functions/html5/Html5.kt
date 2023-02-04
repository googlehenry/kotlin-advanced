package com.henry.dsl.functions.html5


/**
 * 利用其来实现builder模式可以非常intuitive
 * Function types with receiver, such as A.(B) -> C, can be instantiated with a special form of function literals – function literals with receiver.
 */
fun main() {
    val html5 = Html5.build {
        head {
            attr {
                name { "style" }
                value { "background:red;" }
            }
            text { "this is header!!" }
        }
        body {
            p {
                css { "background:black;" }
                text { "paragraph 1" }
            }
            p {
                css { "width:100%;height:50%;" }
                text { "paragraph 1" }
            }
        }
    }
    println(html5.printable())
}

class Html5 : HtmlTag("html") {

    companion object {
        fun build(config: Html5.() -> Unit): Html5 {
            val html5 = Html5()
            html5.config()
            return html5
        }
    }

    fun head(config: Head.() -> Unit) {
        val hd = Head()
        hd.config()
        elements.add(hd)
    }

    fun body(config: Body.() -> Unit) {
        val hd = Body()
        hd.config()
        elements.add(hd)
    }
}

class Head : HtmlTag("head")

class Body : HtmlTag("body") {
    fun p(config: P.() -> Unit) {
        val element = P()
        element.config()
        elements.add(element)
    }

    fun h1(config: H1.() -> Unit) {
        val element = H1()
        element.config()
        elements.add(element)
    }

    fun h2(config: H2.() -> Unit) {
        val element = H2()
        element.config()
        elements.add(element)
    }

    fun h3(config: H3.() -> Unit) {
        val element = H3()
        element.config()
        elements.add(element)
    }

    fun h4(config: H4.() -> Unit) {
        val element = H4()
        element.config()
        elements.add(element)
    }
}

class P : HtmlTag("p")
class H1 : HtmlTag("h1")
class H2 : HtmlTag("h2")
class H3 : HtmlTag("h3")
class H4 : HtmlTag("h4")

open class HtmlTag(val name: String) {
    var text: String? = null
    var elements: MutableList<HtmlTag> = mutableListOf()
    var attributes: MutableList<HtmlAttr> = mutableListOf()
    fun attr(config: HtmlAttr.() -> Unit) {
        val attr = HtmlAttr()
        attr.config()
        attributes.add(attr)
    }

    fun text(config: HtmlTag.() -> String?) {
        text = config()
    }

    fun css(config: HtmlAttr.() -> String?) {
        var attr = HtmlAttr()
        attr.name = "style"
        attr.value = attr.config()
    }

    fun printable(): String {
        var sb = StringBuilder()
        sb.append(
            "<$name${
                attributes.joinToString(" ") { it.printable() }.let { if (it.isEmpty()) "" else " $it" }
            }>"
        )
        elements.forEach { sb.append(it.printable()) }
        sb.append("</$name>")
        return sb.toString()
    }
}

class HtmlAttr {
    var name: String? = null
    var value: String? = null

    fun name(config: HtmlAttr.() -> String) {
        name = config()
    }

    fun value(config: HtmlAttr.() -> String) {
        value = config()
    }

    fun printable(): String {
        return "$name=\"${value ?: ""}\""
    }
}