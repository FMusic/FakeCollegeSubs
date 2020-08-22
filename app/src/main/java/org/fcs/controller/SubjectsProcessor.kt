package org.fcs.controller

import android.app.Activity
import org.fcs.model.College
import org.fcs.model.CollegeList
import org.fcs.model.toObject
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.nio.charset.Charset

class SubjectsProcessor{
    var collegeList: CollegeList? = null

    fun Process(activity: Activity) : List<String>{
        val lines : String = loadTextFromJsonAssets(activity)
        val obj = lines.toObject<CollegeList>()
        collegeList = obj
        return getCollegesNames(obj)
    }

    private fun loadTextFromJsonAssets(activity: Activity): String {
        var json: String = ""
        try{
            var ios: InputStream = activity.assets.open("subjects.json")
            val size: Int = ios.available()
            var buffer = ByteArray(size)
            ios.read(buffer)
            ios.close()
            json = String(buffer, Charset.defaultCharset())
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return json
    }

    private fun getCollegesNames(list: CollegeList): List<String> {
        var listOfNames = ArrayList<String>()
        var listStr = list.colleges.forEach { listOfNames.add(it.collegeName) }
        return listOfNames
    }

    fun getCollege(pickedCollege: Int, activity: Activity): College? {
        Process(activity)
        return collegeList?.colleges?.get(pickedCollege)
    }
}