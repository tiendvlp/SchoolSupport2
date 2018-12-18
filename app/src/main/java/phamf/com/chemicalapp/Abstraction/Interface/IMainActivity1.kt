package phamf.com.chemicalapp.Abstraction.Interface

import android.app.Activity

//import phamf.com.chemicalapp.Presenter.MainActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation

/**
 * @see phamf.com.chemicalapp.MainActivity
 */
interface IMainActivity {

    interface View {

        fun createNecessaryInfo()

        fun addEvent()

        fun loadAnim()

        fun setFont()

        fun setTheme()

        fun setEdt_SearchAdvanceFunctions()

        fun hideSoftKeyboard(activity: Activity)

        fun showSoftKeyboard()
    }


    interface Presenter {

        val dataVersion: Long

        fun loadTheme()

        fun saveTheme()

        fun setThemeDefaut()

        fun loadData()

        fun turnOnNightMode()

        fun turnOffNightMode()

        fun saveDataVersion(version: Long)

        fun setOnThemeChangeListener(theme: OnThemeChangeListener)

        /** Update recent searching chemical equation list into database  */
        fun pushCachingDataToDB()

        /** Bring this Chemical Equation to top of recent learning lesson list in realm database   */
        fun bringToTop(ro_ce: RO_ChemicalEquation)

        fun checkUpdateStatus()
    }
}
