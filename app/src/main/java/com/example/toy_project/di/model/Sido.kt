package com.example.toy_project.di.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.Body

@Root(name = "response", strict = false)
class Sido {
    @field:Element(name = "header")
//    @param:ElementList(name = "header", inline = true, required = false)
    var header: Headers? = null

    @field:ElementList(name = "body", inline = true, required = false)
//    @param:ElementList(name = "body", inline = true, required = false)
    var body: List<Bodys> = arrayListOf()
}

@Root(name = "header", strict = false)
class Headers {

    @field:Element(name = "resultCode")
   var resultCode: String? = null

    @field:Element(name = "resultMsg")
    var resultMsg: String? = null
}

@Root(name = "body", strict = false)
class Bodys {
    @field:ElementList(name = "items", inline = true, required = false)
    var items: List<Items> = arrayListOf()
}


@Root(name = "items")
class Items {
    @field:ElementList(name = "item", inline = true)
    var items: List<Items> = arrayListOf()
}

@Root(name = "item")
class Item {
    @field:Element(name = "orgCd")
    var orgCd: String? = null
    @field:Element(name = "orgdownNm")
    var orgdownNm: String? = null
}
