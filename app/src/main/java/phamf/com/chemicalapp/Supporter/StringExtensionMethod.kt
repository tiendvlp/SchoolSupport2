package phamf.com.chemicalapp.supportClass

inline fun String.isEmail () : Boolean {
    return this.contains("@") && this.contains(".")
}

inline fun String.isContainsCapCharacter () : Boolean {
    forEach {
        if (it.isUpperCase()) {
            return true
        }
    }
    return false
}

inline fun String.isContainNumber () : Boolean {
    forEach {
        if (it.isDigit()) {
            return true
        }
    }
    return false
}
