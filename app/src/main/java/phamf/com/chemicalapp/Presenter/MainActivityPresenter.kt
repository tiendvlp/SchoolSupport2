//package phamf.com.chemicalapp.Presenter
//
//import android.content.SharedPreferences
//import android.util.Log
//import android.widget.Toast
//
//import java.util.ArrayList
//
//import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity
//import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Database.OfflineDatabaseManager
//import phamf.com.chemicalapp.Database.UpdateDatabaseManager
//import phamf.com.chemicalapp.MainActivity
//import phamf.com.chemicalapp.Manager.AppThemeManager
//import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
//import phamf.com.chemicalapp.Manager.RequireOverlayPermissionManager
//import phamf.com.chemicalapp.RO_Model.RO_Chapter
//import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
//import phamf.com.chemicalapp.RO_Model.RO_Lesson
//
//import android.content.Context.MODE_PRIVATE
//
//
///**
// * @see MainActivity
// */
//class MainActivityPresenter(view: MainActivity) : Presenter<MainActivity>(view), IMainActivity.Presenter {
//
//    private val updateDB_Manager: UpdateDatabaseManager
//
//    private val offlineDB_manager: OfflineDatabaseManager
//
//    private val requireOverlayPermissionManager: RequireOverlayPermissionManager
//
//    private var onDataLoadListener: MainActivityPresenter.DataLoadListener? = null
//
//    private var onThemeChangeListener: OnThemeChangeListener? = null
//
//    private var recentSearching_ce_data_manager: RecentSearching_CE_Data_Manager? = null
//
//    private var onUpdateChecked: OnUpdateCheckedListener? = null
//
//    override val dataVersion: Long
//        get() {
//            val databaseVersion = view.getSharedPreferences(APP_INFO, MODE_PRIVATE)
//            return databaseVersion.getLong(DATABASE_VERSION, 1)
//        }
//
//
//    init {
//
//        this.view = view
//
//        this.context = view.baseContext
//
//        updateDB_Manager = UpdateDatabaseManager(view, dataVersion)
//
//        offlineDB_manager = OfflineDatabaseManager(context)
//
//        requireOverlayPermissionManager = RequireOverlayPermissionManager(view)
//    }
//
//    fun update(onUpdateSuccess: OnUpdateSuccess) {
//        updateDB_Manager.setOnASectionUpdated { version, isLastVersion ->
//            if (isLastVersion) {
//                onUpdateSuccess.onUpdateSuccess()
//            } else {
//                saveDataVersion(version)
//            }
//        }
//
//        updateDB_Manager.update()
//
//    }
//
//
//    // Some functions Else
//    override fun loadTheme() {
//
//        val sharedPreferences = view.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE)
//
//        AppThemeManager.isCustomingTheme = sharedPreferences.getBoolean(AppThemeManager.IS_CUSTOMING, false)
//        AppThemeManager.isUsingAvailableThemes = sharedPreferences.getBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, false)
//        AppThemeManager.isOnNightMode = sharedPreferences.getBoolean(AppThemeManager.IS_ON_NIGHT_MODE, false)
//        Toast.makeText(view, AppThemeManager.isOnNightMode.toString() + "", Toast.LENGTH_SHORT).show()
//
//        if (AppThemeManager.isCustomingTheme) {
//            AppThemeManager.isUsingColorBackground = sharedPreferences.getBoolean(AppThemeManager.IS_USING_COLOR_BACKGROUND, true)
//            AppThemeManager.widgetColor = sharedPreferences.getInt(AppThemeManager.WIDGET_COLOR, 0)
//            AppThemeManager.textColor = sharedPreferences.getInt(AppThemeManager.TEXT_COLOR, 0)
//            AppThemeManager.backgroundColor = sharedPreferences.getInt(AppThemeManager.BACKGROUND_COLOR, 0)
//            AppThemeManager.backgroundDrawable = sharedPreferences.getInt(AppThemeManager.BACKGROUND_DRAWABLE, 0)
//            onThemeChangeListener!!.onThemeChange()
//        } else if (AppThemeManager.isUsingAvailableThemes) {
//            AppThemeManager.setTheme(sharedPreferences.getInt(AppThemeManager.CURRENT_THEME, 0))
//            onThemeChangeListener!!.onThemeChange()
//        }
//    }
//
//    override fun saveDataVersion(version: Long) {
//        val databaseVersion = view.getSharedPreferences(APP_INFO, MODE_PRIVATE)
//        val editor = databaseVersion.edit()
//        editor.putLong(DATABASE_VERSION, version)
//        editor.apply()
//    }
//
//    override fun saveTheme() {
//        val sharedPreferences = view.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        Toast.makeText(view, AppThemeManager.isOnNightMode.toString() + "", Toast.LENGTH_SHORT).show()
//        editor.putBoolean(AppThemeManager.IS_ON_NIGHT_MODE, AppThemeManager.isOnNightMode)
//        if (AppThemeManager.isCustomingTheme) {
//
//            editor.putInt(AppThemeManager.BACKGROUND_COLOR, AppThemeManager.backgroundColor)
//            editor.putInt(AppThemeManager.BACKGROUND_DRAWABLE, AppThemeManager.backgroundDrawable)
//            editor.putInt(AppThemeManager.TEXT_COLOR, AppThemeManager.textColor)
//            editor.putInt(AppThemeManager.WIDGET_COLOR, AppThemeManager.widgetColor)
//            editor.putBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, AppThemeManager.isUsingAvailableThemes)
//            editor.putBoolean(AppThemeManager.IS_CUSTOMING, AppThemeManager.isCustomingTheme)
//
//        } else {
//            Log.e("Don't save anything", "!!!")
//        }
//        editor.apply()
//    }
//
//    fun addDefaultDataOnceTime() {
//        val app_info = context.getSharedPreferences(APP_INFO, MODE_PRIVATE)
//        val has_default_value = app_info.getBoolean(HAS_DEFAULT_VALUE, false)
//        if (!has_default_value) {
//            try {
//                // This data just for demo
//                // Lessons
//                val ro_lesson = RO_Lesson()
//                ro_lesson.id = 0
//                ro_lesson.content = "<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ..."
//                ro_lesson.name = "Bai 1"
//
//                val ro_lesson1 = RO_Lesson()
//                ro_lesson1.id = 1
//                ro_lesson1.content = "<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ..."
//                ro_lesson1.name = "Bai 2"
//
//                val ro_lesson2 = RO_Lesson()
//                ro_lesson2.id = 2
//                ro_lesson2.content = "<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ..."
//                ro_lesson2.name = "Bai 3"
//
//                val ro_lessons = ArrayList<RO_Lesson>()
//                ro_lessons.add(ro_lesson)
//                ro_lessons.add(ro_lesson1)
//
//                val ro_lessons1 = ArrayList<RO_Lesson>()
//                ro_lessons1.add(ro_lesson2)
//
//                //Chapters
//                val ro_chapter = RO_Chapter()
//                ro_chapter.id = 0
//                ro_chapter.name = "Chuong 1"
//                ro_chapter.setLessons(ro_lessons)
//
//                val ro_chapter1 = RO_Chapter()
//                ro_chapter1.id = 1
//                ro_chapter1.name = "Chuong 2"
//                ro_chapter1.setLessons(ro_lessons1)
//
//                offlineDB_manager.addOrUpdateDataOf(RO_Chapter::class.java, ro_chapter, ro_chapter1)
//
//                val editor = app_info.edit()
//                editor.putBoolean(HAS_DEFAULT_VALUE, true)
//                editor.apply()
//
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//
//        }
//    }
//
//    override fun loadData() {
//        recentSearching_ce_data_manager = RecentSearching_CE_Data_Manager(offlineDB_manager)
//        recentSearching_ce_data_manager!!.getData { ces -> onDataLoadListener!!.onDataLoadSuccess(ces) }
//    }
//
//    override fun turnOnNightMode() {
//        AppThemeManager.isOnNightMode = true
//    }
//
//    override fun turnOffNightMode() {
//        AppThemeManager.isOnNightMode = false
//    }
//
//    override fun setThemeDefaut() {
//        AppThemeManager.setTheme(AppThemeManager.NORMAL_THEME)
//        onThemeChangeListener!!.onThemeChange()
//    }
//
//    fun requirePermission(requestCode: Int) {
//        requireOverlayPermissionManager.requirePermission(requestCode)
//    }
//
//    fun setOnDataLoadListener(onDataLoadListener: DataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener
//    }
//
//    override fun setOnThemeChangeListener(theme: OnThemeChangeListener) {
//        this.onThemeChangeListener = theme
//    }
//
//    fun setOnUpdateStatusCheckedListener(onUpdateChecked: OnUpdateCheckedListener) {
//        this.onUpdateChecked = onUpdateChecked
//    }
//
//    /** Update recent searching chemical equation list into database  */
//    override fun pushCachingDataToDB() {
//        recentSearching_ce_data_manager!!.updateDB()
//    }
//
//    /** Bring this Chemical Equation to top of recent learning lesson list in realm database   */
//    override fun bringToTop(ro_ce: RO_ChemicalEquation) {
//        recentSearching_ce_data_manager!!.bringToTop(ro_ce)
//    }
//
//    override fun checkUpdateStatus() {
//        updateDB_Manager.getLastestVersionUpdate { version ->
//            // if firebase database version is bigger than app version, there's at least
//            // one update version available
//            Log.e("App version", dataVersion.toString() + "")
//            onUpdateChecked!!.onStatusChecked(version > dataVersion, version)
//        }
//    }
//
//
//    /**
//     * @see MainActivity
//     * which implement this listeners to get Data
//     */
//
//    interface DataLoadListener {
//
//        fun onDataLoadSuccess(ro_chemicalEquations: ArrayList<RO_ChemicalEquation>)
//
//    }
//
//    interface OnUpdateCheckedListener {
//
//        fun onStatusChecked(isAvailable: Boolean, lasted_version: Long)
//
//    }
//
//    interface OnUpdateSuccess {
//
//        fun onUpdateSuccess()
//
//    }
//
//    companion object {
//
//        val APP_INFO = "app_info"
//
//        val DATABASE_VERSION = "db_vers"
//
//        val HAS_DEFAULT_VALUE = "has_def_val"
//    }
//
//
//}
//
//
