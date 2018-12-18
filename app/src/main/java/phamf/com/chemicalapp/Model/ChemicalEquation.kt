package phamf.com.chemicalapp.Model

import android.text.Html
import android.util.Log
import android.widget.TextView

import java.security.Key
import java.util.ArrayList
import java.util.Random

import io.realm.Realm
import io.realm.RealmModel
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation

data class ChemicalEquation (var id: Int = 0,
        // Use for caching equation field value to highlight the filtered equation without lost their beginning data;
                             var equation : String = "",
                             var condition: String = "",
                             var phenonema : String = "",
                             var how_to_do : String = ""
)

