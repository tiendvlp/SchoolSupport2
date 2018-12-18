package phamf.com.chemicalapp.ViewModel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log

import java.util.ArrayList

import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.Database.UpdateDatabaseManager
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
import phamf.com.chemicalapp.Model.SupportModel.ThemeUpdater
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation

import android.content.Context.MODE_PRIVATE

/**
 * @see MainActivity
 */
class MVVM_MainActivityPresenter(application: Application) : AndroidViewModel(application), IMainActivity.Presenter {


    private val updateDB_Manager: UpdateDatabaseManager

    private val offlineDB_manager: OfflineDatabaseManager

    var ldt_ro_chemicalEquation = MutableLiveData<ArrayList<RO_ChemicalEquation>>()

    private var onThemeChangeListener: OnThemeChangeListener? = null

    private var recentSearching_ce_data_manager: RecentSearching_CE_Data_Manager? = null

    private var onUpdateChecked: MVVM_MainActivityPresenter.OnUpdateCheckedListener? = null

    private val themeUpdater: ThemeUpdater

    override val dataVersion: Long
        get() {
            val databaseVersion = getApplication<Application>().getSharedPreferences(APP_INFO, MODE_PRIVATE)
            return databaseVersion.getLong(DATABASE_VERSION, 0)
        }

    init {

        updateDB_Manager = UpdateDatabaseManager(application, dataVersion)

        offlineDB_manager = OfflineDatabaseManager(application)

        themeUpdater = ThemeUpdater(application)

    }


    fun update(onUpdateSuccess: MVVM_MainActivityPresenter.OnUpdateSuccess) {
        Log.e("Inside MVVM", "Ok bro")
        updateDB_Manager.setOnASectionUpdated (object : UpdateDatabaseManager.OnASectionUpdated {
            override fun on_A_Version_Updated_Success(version: Long, isLastVersion: Boolean) {
                if (isLastVersion) {
                    onUpdateSuccess.onUpdateSuccess()
                } else {
                    saveDataVersion(version)
                }
            }

        })

        updateDB_Manager.update()
    }

    // Else

    override fun loadTheme() {
        val isThemeChanging = themeUpdater.loadTheme()
        if (isThemeChanging) onThemeChangeListener!!.onThemeChange()

    }

    override fun saveDataVersion(version: Long) {
        val databaseVersion = getApplication<Application>().getSharedPreferences(APP_INFO, MODE_PRIVATE)
        val editor = databaseVersion.edit()
        editor.putLong(DATABASE_VERSION, version)
        editor.apply()
    }

    override fun saveTheme() {
        themeUpdater.saveTheme()
    }


    override fun loadData() {
        recentSearching_ce_data_manager = RecentSearching_CE_Data_Manager(offlineDB_manager)
        recentSearching_ce_data_manager!!.getData(object : RecentSearching_CE_Data_Manager.OnGetDataSuccess {
            override fun onLoadSuccess(recent_Ces: ArrayList<RO_ChemicalEquation>) {
                ldt_ro_chemicalEquation.value = recent_Ces
            }

        })
    }

    override fun turnOnNightMode() {
        AppThemeManager.isOnNightMode = true
    }

    override fun turnOffNightMode() {
        AppThemeManager.isOnNightMode = false
    }

    override fun setThemeDefaut() {
        themeUpdater.setThemeDefault()
        onThemeChangeListener!!.onThemeChange()
    }


    override fun setOnThemeChangeListener(theme: OnThemeChangeListener) {
        this.onThemeChangeListener = theme
    }

    fun setOnUpdateStatusCheckedListener(onUpdateChecked: MVVM_MainActivityPresenter.OnUpdateCheckedListener) {
        this.onUpdateChecked = onUpdateChecked
    }

    /** Update recent searching chemical equation list into database  */
    override fun pushCachingDataToDB() {
        recentSearching_ce_data_manager!!.updateDB()
    }

    /** Bring this Chemical Equation to top of recent learning lesson list in realm database   */
    override fun bringToTop(ro_ce: RO_ChemicalEquation) {
        recentSearching_ce_data_manager!!.bringToTop(ro_ce)
    }

    override fun checkUpdateStatus() {
        updateDB_Manager.getLastestVersionUpdate (object : UpdateDatabaseManager.OnVersionChecked {
            override fun onVersionLoaded(version: Long) {
                Log.e("App version", dataVersion.toString() + "")
                onUpdateChecked!!.onStatusChecked(version > dataVersion, version)
            }
        })
    }


    /**
     * @see MainActivity
     * which implement this listeners to get Data
     */
    //
    //    public interface DataLoadListener {
    //
    //        void onDataLoadSuccess(ArrayList<RO_ChemicalEquation> ro_chemicalEquations);
    //
    //    }

    interface OnUpdateCheckedListener {

        fun onStatusChecked(isAvailable: Boolean, lasted_version: Long)

    }

    interface OnUpdateSuccess {

        fun onUpdateSuccess()

    }

    internal inner class RequireOverlayPermissionManager(var activity: Activity) {

        var context: Context

        init {
            this.context = activity.baseContext
        }

        fun requirePermission(request_code: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName))
                activity.startActivityForResult(intent, request_code)
            }
        }

    }

    companion object {

        val APP_INFO = "app_info"

        val DATABASE_VERSION = "db_vers"

        val HAS_DEFAULT_VALUE = "has_def_val"
    }

}

