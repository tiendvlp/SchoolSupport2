package phamf.com.chemicalapp.Manager

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.IntDef
import android.util.Log

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


object AppThemeManager {

    const val ART_THEME = 0

    const val DARK_THEME = 1

    const val NORMAL_THEME = 2

    val APP_THEME = "app_theme"

    val CURRENT_THEME = "current_theme"

    val IS_USING_COLOR_BACKGROUND = "is_using_color_background"

    val BACKGROUND_COLOR = "background_color"

    val TEXT_COLOR = "text_color"

    val BACKGROUND_DRAWABLE = "background_drawable"

    val WIDGET_COLOR = "theme_color"

    val IS_CUSTOMING = "is_customing"

    val IS_USING_AVAILABLE_THEMES = "is_using_available_themes"

    val IS_ON_NIGHT_MODE = "is_on_night_mode"


    var backgroundColor = 0

    var backgroundDrawable = 0

    var textColor = 0

    var widgetColor = 0

    var availableTheme = 0

    var isCustomingTheme: Boolean = false

    var isUsingAvailableThemes: Boolean = false

    var isUsingColorBackground: Boolean = false

    var isOnNightMode: Boolean = false


    var backgroundColor_list = IntArray(6)

    var themeColor_list = IntArray(6)

    var textColor_list = IntArray(6)

    var defautTheme_list = arrayOfNulls<DefaultTheme>(3)

    var backgroundDrawable_list = arrayOfNulls<Drawable>(6)


    init {
        backgroundColor_list[0] = android.R.color.holo_blue_dark
        backgroundColor_list[1] = android.R.color.holo_orange_light
        backgroundColor_list[2] = android.R.color.holo_purple
        backgroundColor_list[3] = android.R.color.holo_red_dark
        backgroundColor_list[4] = android.R.color.holo_green_light
        backgroundColor_list[5] = android.R.color.holo_red_light

        themeColor_list[0] = android.R.color.holo_blue_dark
        themeColor_list[1] = android.R.color.holo_orange_light
        themeColor_list[2] = android.R.color.holo_purple
        themeColor_list[3] = android.R.color.holo_red_dark
        themeColor_list[4] = android.R.color.holo_green_light
        themeColor_list[5] = android.R.color.holo_red_light

        textColor_list[0] = android.R.color.holo_blue_dark
        textColor_list[1] = android.R.color.holo_orange_light
        textColor_list[2] = android.R.color.holo_purple
        textColor_list[3] = android.R.color.holo_red_dark
        textColor_list[4] = android.R.color.holo_green_light
        textColor_list[5] = android.R.color.holo_red_light

        defautTheme_list[0] = DefaultTheme.ART_THEME
        defautTheme_list[1] = DefaultTheme.DARK_THEME
        defautTheme_list[2] = DefaultTheme.NORMAL_THEME
    }


    fun setTheme(@AppTheme order: Int) {

        val theme = defautTheme_list[order]
        when (theme) {
            AppThemeManager.DefaultTheme.ART_THEME -> {
                backgroundColor = 1
                textColor = 2
            }
            AppThemeManager.DefaultTheme.DARK_THEME -> {
                backgroundColor = 4
                textColor = 3
            }
            AppThemeManager.DefaultTheme.NORMAL_THEME -> {
                textColor = 0
                backgroundColor = 0
            }
        }
    }

    fun getBackgroundColor_(): Int {
        return backgroundColor_list[backgroundColor]
    }

    fun getBackgroundDrawable_(): Drawable? {
        return backgroundDrawable_list[backgroundDrawable]
    }

    fun getTextColor_() : Int {
        return textColor_list[textColor];
    }

    enum class DefaultTheme {
        DARK_THEME,
        NORMAL_THEME,
        ART_THEME
    }

    @IntDef(ART_THEME, DARK_THEME, NORMAL_THEME)
    @Retention(RetentionPolicy.SOURCE)
    annotation class AppTheme
}


