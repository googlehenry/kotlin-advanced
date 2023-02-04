package com.henry.dsl.functions.html4


fun main() {
    val html4 = Html4()
    html4.build {
        head {
            text { "header!!" }
        }
        body {
            css { "background:yellow;" }
            p {
                css { "background:red;" }
                text { "paragraph 1" }
            }
            p {
                css { "color:red;" }
                text { "paragraph 2" }
            }
        }
    }

    val byBuilder = Html4.builder {
        head {
            text { "header companion" }
        }
        body {
            css { "font-size:20px;" }
            p { text { "paragraph 1" } }
            p { text { "paragraph 2" } }
        }
    }

    println(html4.printable())
    println(byBuilder.printable())
}

class Html4 {
    val tagName = "html"
    var head: Head? = null
    var body: Body? = null

    fun build(config: Html4.() -> Unit): Html4 {
        config()
        return this
    }

    fun head(config: Head.() -> Unit?) {
        val hd = Head()
        hd.config()//html.head(), 具体怎么给head的属性赋值,用户根据head class定义的方法去完成即可.
        head = hd
    }

    fun body(config: Body.() -> Unit) {
        val bd = Body()
        bd.config() //html.body();具体怎么给body的属性赋值,用户根据body class定义的方法去完成即可.
        body = bd
    }


    fun printable(): String {
        val sb = StringBuilder()
        sb.append("<$tagName>")
        sb.append(head?.let { it.printable() } ?: "")
        sb.append(body?.let { it.printable() } ?: "")
        sb.append("</$tagName>")
        return sb.toString()
    }

    companion object {
        fun builder(config: Html4.() -> Unit): Html4 {
            val html4 = Html4()
            html4.config()
            return html4
        }
    }

    class Head {
        val tagName = "head"
        var css: String? = null
        var text: String? = null

        fun css(config: Head.() -> String?) {
            css = config()
        }

        fun text(config: Head.() -> String?) {
            text = config()
        }

        fun printable(): String {
            val sb = StringBuilder()
            sb.append("<$tagName${css?.let { " style=\"$it\"" } ?: ""}>")
            sb.append(text ?: "")
            sb.append("</$tagName>")
            return sb.toString()
        }
    }

    class Body {
        val tagName = "body"
        var css: String? = null
        var text: String? = null
        var paras: MutableList<P> = mutableListOf()
        fun css(config: Body.() -> String?) {
            css = config()
        }

        fun text(config: Body.() -> String?) {
            text = config()
        }

        fun p(config: P.() -> Unit?) {
            val pa = P()
            pa.config()
            paras.add(pa)
        }

        fun printable(): String {
            val sb = StringBuilder()
            sb.append("<$tagName${css?.let { " style=\"$it\"" } ?: ""}>")
            paras.forEach { sb.append(it.printable()) }
            sb.append(text ?: "")
            sb.append("</$tagName>")
            return sb.toString()
        }
    }

    class P {
        val tagName = "p"
        var css: String? = null
        var text: String? = null

        fun css(config: P.() -> String?) {
            css = config()
        }

        fun text(config: P.() -> String?) {
            text = config()
        }

        fun printable(): String {
            val sb = StringBuilder()
            sb.append("<$tagName${css?.let { " style=\"$it\"" } ?: ""}>")
            sb.append(text ?: "")
            sb.append("</$tagName>")
            return sb.toString()
        }
    }
}