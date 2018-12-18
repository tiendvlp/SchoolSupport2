package phamf.com.chemicalapp.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import butterknife.BindView
import phamf.com.chemicalapp.CustomView.LessonViewCreator
import phamf.com.chemicalapp.R

class LessonPartFragment : Fragment() {

    @BindView(R.id.board)
    lateinit var content_view: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        content_view = view.findViewById(R.id.board)

        val bundle = arguments
        if (bundle != null) {
            val content = bundle.getString(CONTENT, "")
            val viewCreator = LessonViewCreator.ViewCreator(context!!, content_view)
            viewCreator.addView(content)
        }
    }

    companion object {

        private val CONTENT = "Content"

        fun newInstance(content: String): LessonPartFragment {
            val fragment = LessonPartFragment()
            val args = Bundle()
            args.putString(CONTENT, content)
            fragment.arguments = args
            return fragment
        }
    }

}
