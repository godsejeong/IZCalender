package com.jjmin.izcalender.model

import android.util.Log
import com.jjmin.izcalender.data.PlanningItem
import com.jjmin.izcalender.data.TodayItem
import com.jjmin.izcalender.utils.DowChangeUtils
import com.jjmin.izcalender.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlanningModel : Thread() {
    var infoList = ArrayList<PlanningItem>()
    var todayList = ArrayList<TodayItem>()
    var clandardayList = ArrayList<String>()
    var res = Utils.postservice.allPlanList()

    override fun run() {
        Log.e("requestCode", res.execute().code().toString())
        var planJson = res.clone().execute().body()!!.plan

        planJson.forEach {
            Log.e("day", it.day)
            clandardayList.add(it.day)
            if (it.title.size >= 2) {
                for (i in 0 until it.title.size) {
                    searchToday(it.title[i], it.subTitle[i], timeCheck(it.time[i]), it.day, DowChangeUtils.toEn(it.dow))
                }
            } else {
                searchToday(it.title[0], it.subTitle[0], timeCheck(it.time[0]), it.day, DowChangeUtils.toEn(it.dow))
            }
        }
    }

    fun searchToday(title: String, subTitle: String, time: String, day: String, dow: String) {
        val mDay = Utils.today()
        Log.e("mTime", mDay)
        Log.e("Time", day)
        if (day in mDay) {
            Log.e("Ads", "a")
            todayList.add(TodayItem(title, subTitle, timeCheck(time), "TODAY", dow))
        } else {
            infoList.add(PlanningItem(title, subTitle, timeCheck(time), day, dow))
        }
    }

    fun timeCheck(time: String): String {
        return if (time == "하루종일") {
            "Always"
        } else {
            time
        }
    }
}