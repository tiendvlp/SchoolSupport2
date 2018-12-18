package phamf.com.chemicalapp.supportClass

import jp.wasabeef.richeditor.RichEditor

inline fun RichEditor.setTab () {
    html += "&nbsp;&nbsp;&nbsp;&nbsp;"
    clearFocusEditor()
    focusEditor()
}

inline fun RichEditor.setDash () {
    html += "<hr  width=\"50%\" align=\"center\" /> <Br>"
    clearFocusEditor()
    focusEditor()
}