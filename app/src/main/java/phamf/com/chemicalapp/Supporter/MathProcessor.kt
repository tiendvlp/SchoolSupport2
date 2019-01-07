package phamf.com.chemicalapp.Supporter

import java.lang.StringBuilder


class MathProcessor {

    companion object {
        val <T>  List<T>.firstElement
            get () = this[0]

        fun processValueOf (content : String) : String {

            var list : ArrayList<String> = getContentInParenthese(content)

            var s : String = content

            var value: Float

            if (list.size > 0) {
                for (exp in list) {
                    s = if (exp.contains("("))
                        s.replace("($exp)", processValueOf(exp))
                    else{
                        s.replace("($exp)", calc_sum(exp))
                    }
                }
            }

            value = calc_sum(s).toFloat()

            return if (value % 1 == 0f) value.toInt().toString() else value.toString()
        }

        fun calc_sum (exp : String) : String{

            var result = 0f
            var content = exp

            if (content.contains("^2")) content = content.processSQUARE()

            var p_list : List<String> = content.split("+")

            for (exp in p_list) {
                result += if (exp.contains("*") or exp.contains("/"))
                    process_multiply_and_devive(exp)
                else
                {
                    exp.toFloat()
                }

            }

            return result.toString()
        }

        fun process_multiply_and_devive (s : String) : Float {
            var list : List<String> = s.split("*")

            var value = 1f

            for (exp in list) {
                value *= if (!exp.contains("/"))
                    exp.toFloat()
                else
                    process_divide(exp)

            }

            return value
        }

        fun process_divide (s : String) : Float {
            var list : List<String> = s.split("/")

            var value : Float = list.firstElement.toFloat()

            for (i in 1 until list.size) {
                value /= list[i].toFloat()
            }

            return value
        }

        fun getContentInParenthese (content : String) : ArrayList<String> {
            val result : ArrayList<String> = ArrayList()
            if (content.contains('(')) {
                var isInParenths = false
                var startPos = 0
                var endPos: Int
                var parenth_count = 0

                for (i in 0 until content.length) {
                    val char : Char = content[i]

                    if (isInParenths) {
                        if (char == ')') {
                            parenth_count -= 1

                            if (parenth_count == 0) {
                                endPos = i
                                val data : String = content.substring(startPos, endPos)
                                result.add(data)
                                isInParenths = false
                            }

                        } else if (char == '(') parenth_count += 1

                    } else {
                        if (char == '(') {
                            isInParenths = true
                            startPos = i + 1
                            parenth_count += 1
                        }
                    }
                }

            }

            return result
        }


    }

}
