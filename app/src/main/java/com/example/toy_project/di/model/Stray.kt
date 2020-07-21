package com.example.toy_project.di.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.Body

@Root(name = "response", strict = false)
class Stray {
    @field:Element(name = "header")
    var header: Headers? = null

    @field:ElementList(name = "body", inline = true, required = false)
    var body: List<Bodys> = arrayListOf()
}

@Root(name = "header", strict = false)
class Headers {
    @field:Element(name = "resultCode")
   var resultCode: String? = ""

    @field:Element(name = "resultMsg")
    var resultMsg: String? = ""
}

@Root(name = "body", strict = false)
class Bodys {
    @field:ElementList(name = "items", inline = true, required = false)
    var items: List<Items> = arrayListOf()
}


@Root(name = "items", strict = false)
class Items {
    @field:ElementList(name = "item", inline = true, required = false)
    var item: List<Item> = arrayListOf()
}

@Root(name = "item", strict = false)
class Item {
    /*
    시도코드
     */
    @field:Element(name = "orgCd", required = false)
    var orgCd: String? = ""

    /*
    시도명
     */
    @field:Element(name = "orgdownNm", required = false)
    var orgdownNm: String? = ""

    /*
    나이
     */
    @field:Element(name = "age", required = false)
    var age: String? = ""

    /*
    보호장소
     */
    @field:Element(name = "careAddr", required = false)
    var careAddr: String? = ""

    /*
   보호소이름
    */
    @field:Element(name = "careNm", required = false)
    var careNm: String? = ""

    /*
   보호소전화번호
    */
    @field:Element(name = "careTel", required = false)
    var careTel: String? = ""

    /*
   담당자
    */
    @field:Element(name = "chargeNm", required = false)
    var chargeNm: String? = ""

    /*
   색상
    */
    @field:Element(name = "colorCd", required = false)
    var colorCd: String? = ""

    /*
   유기번호
    */
    @field:Element(name = "desertionNo", required = false)
    var desertionNo: String? = ""

    /*
   Thumbnail Image
    */
    @field:Element(name = "filename", required = false)
    var filename: String? = ""

    /*
   happenDt
    */
    @field:Element(name = "happenDt", required = false)
    var happenDt: String? = ""

    /*
   보호장소
    */
    @field:Element(name = "happenPlace", required = false)
    var happenPlace: String? = ""

    /*
   품종
    */
    @field:Element(name = "kindCd", required = false)
    var kindCd: String? = ""

    /*
   중성화여부
    */
    @field:Element(name = "neuterYn", required = false)
    var neuterYn: String? = ""

    /*
   공고종료일
    */
    @field:Element(name = "noticeEdt", required = false)
    var noticeEdt: String? = ""

    /*
   공고번호
    */
    @field:Element(name = "noticeNo", required = false)
    var noticeNo: String? = ""

    /*
   공고시작일
    */
    @field:Element(name = "noticeSdt", required = false)
    var noticeSdt: String? = ""

    /*
   담당자연락처
    */
    @field:Element(name = "officetel", required = false)
    var officetel: String? = ""

    /*
   관할기관
    */
    @field:Element(name = "orgNm", required = false)
    var orgNm: String? = ""

    /*
   Image
    */
    @field:Element(name = "popfile", required = false)
    var popfile: String? = ""

    /*
   상태
    */
    @field:Element(name = "processState", required = false)
    var processState: String? = ""

    /*
   성별
    */
    @field:Element(name = "sexCd", required = false)
    var sexCd: String? = ""

    /*
   특징
    */
    @field:Element(name = "specialMark", required = false)
    var specialMark: String? = ""

    /*
   체중
    */
    @field:Element(name = "weight", required = false)
    var weight: String? = ""
}
