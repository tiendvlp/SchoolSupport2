package phamf.com.chemicalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ChemicalCompositionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chemical_composition)
    }

    companion object {

        val CHEMICAL_COMPOSITION = "chem_composition"
    }
}
