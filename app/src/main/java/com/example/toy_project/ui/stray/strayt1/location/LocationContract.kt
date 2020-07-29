package com.example.toy_project.ui.stray.strayt1.location


import com.example.toy_project.base.BaseContract
import com.example.toy_project.di.model.Item

interface LocationContract {

    interface LocationView : BaseContract.View {
        fun showUprList(uprList: List<Item>)
        fun showOrgList(orgList: List<Item>)
        fun showCareList(careList: List<Item>)
    }

    interface LocationPresenter : BaseContract.Presenter<LocationView> {
        fun getUpr()
        fun getOrg(upr_cd: String)
        fun getCare(upr_cd: String, org_cd: String)
        fun setLocation(upr_cd: String, org_cd: String, care_reg_no: String)
    }
}