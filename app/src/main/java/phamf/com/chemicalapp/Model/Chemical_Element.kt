package phamf.com.chemicalapp.Model

class Chemical_Element {

    var id: Int = 0

    var name = ""

    var symbol = ""

    var electron_count: Int = 0

    var proton_count: Int = 0

    var notron_count: Int = 0

    var mass: Float = 0.toFloat()

    lateinit var type: String

    var backgroundColor: Int = 0

    constructor() {

    }

    constructor(id: Int, name: String, symbol: String, electron_count: Int, proton_count: Int, notron_count: Int, mass: Float, type: String, backgroundColor: Int) {

        this.name = name
        this.symbol = symbol
        this.electron_count = electron_count
        this.proton_count = proton_count
        this.notron_count = notron_count
        this.mass = mass
        this.type = type
        this.id = id
        this.backgroundColor = backgroundColor
    }
}
