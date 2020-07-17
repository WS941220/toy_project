package com.example.toy_project.di.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response")
data class Sid constructor(

    @field:ElementList(name = "header")
    @param:ElementList(name = "header", inline = true, required = false)
    private var header: List<Headers>,

    @field:ElementList(name = "body")
    @param:ElementList(name = "body", inline = true, required = false)
    private var body: List<Bodys>
)

@Root(name = "header")
data class Headers constructor(

    @field:Element(name = "resultCode")
    @param:Element(name = "resultCode", required= false)
    private var resultCode: String,

    @field:Element(name = "resultMsg")
    @param:Element(name = "resultMsg", required= false)
    private var resultMsg: String
)

@Root(name = "body")
data class Bodys constructor(

    @field:Element(name = "items")
    @param:Element(name = "items")
    private var items: List<Items>
)

@Root(name = "item")
data class Items constructor(

    @field:Element(name = "orgCd")
    @param:Element(name = "orgCd")
    private var orgCd: String,

    @field:Element(name = "orgdownNm")
    @param:Element(name = "orgdownNm")
    private var orgdownNm: String
)