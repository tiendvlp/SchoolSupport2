package phamf.com.chemicalapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import phamf.com.chemicalapp.Abstraction.Interface.IBangTuanHoangActivity
import phamf.com.chemicalapp.Adapter.BangTuanHoang_GridView_Adapter
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element
import phamf.com.chemicalapp.Supporter.UnitConverter

import android.view.View.GONE
import kotlinx.android.synthetic.main.activity_bang_tuan_hoang.*


class BangTuanHoangActivity : FullScreenActivity(), IBangTuanHoangActivity.View {

    internal lateinit var grv_bth_adapter: BangTuanHoang_GridView_Adapter

    internal lateinit var rcv_element_search_adapter: Search_Chem_Element_Adapter

    internal var grv_bth_list = ArrayList<RO_Chemical_Element>()

    var list = ArrayList<String>()

    private lateinit var fade_out: Animation
    private lateinit var fade_in: Animation

    internal lateinit var virtualKeyboardManager: InputMethodManager

    private val CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE = 105 /* 105 dp*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bang_tuan_hoang)

        ButterKnife.bind(this)

        setTheme()

        loadAnim()

        setUpManagers()

        addData()

        addControl()

        addEvent()

        setPeriodicTableWidth()
    }

    private fun setUpManagers() {
        virtualKeyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun addData() {


        var akali_metal_color = getColor(R.color.alkali_metal_color)
        var non_metal_color = getColor(R.color.nonmetal_color)
        var group_b_color = getColor(R.color.Group_B_metal)

        grv_bth_list.add(RO_Chemical_Element(1, "Hidro", "H", 1, 1, 0, 2f, "Không xác định", akali_metal_color,"Hidro là chất khí nhẹ nhất và chiếm 75% khối lượng của vũ trụ, với số lượng lớn Hidro trong không gian nó sẽ hấp thụ ánh sáng và tạo ra những cảnh quan ngoạn mục như ánh sáng mặt trời do sự chuyển hóa của nó thành khí Heli và ánh sáng của các vì sao.\n" +
                "Ứng dụng:\n" +
                "- Được sử dụng trong những khinh khí cầu đầu tiên như Hindenburg.\n" +
                "-Móc chìa khóa phát sáng tritium(3H), bất hợp pháp ở Mỹ.\n" +
                "- Đồng hồ tritium là hợp pháp ở Mỹ.\n" +
                "- Bên trong một thyraton tốc độ cao được điên đầy với một lượng nhỏ khí Hidro.\n"))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(2, "Heli", "He", 2, 2, 2, 4f, "Khí trơ", akali_metal_color,"- Heli là nguyên tố được phát hiện đầu tiên trong không gian.\n" +
                "- Khí Heli nhẹ và khó bốc cháy, Heli trơ với gần như tất cả các liên kết Hóa Học.\n" +
                "- Khi có dòng điên chạy qua heli phát ra ánh sáng màu hồng nhạt.\n" +
                "Ứng dụng:\n" +
                "-  Bóng bay cao su chứ khí Heli nhanh chóng bị xì hơi vi các nguyên tử Heli nhỏ bé sẽ thoát ra nhanh chóng.\n" +
                "-  Bình chưa khí Heli dùng một lần luôn có sẵn trong các cửa hiệu nhưng thường chứa thêm oxi để ngăn ngừa ngạt thở nếu trẻ em hít phải.\n"))
        grv_bth_list.add(RO_Chemical_Element(3, "Liti", "Li", 3, 3, 4, 7f, "Kiềm", akali_metal_color,"- Là 1 kim loại rất nhẹ, nổi trên mặt nước.\n" +
                "- Liti có 1 nguồn năng lượng rất lớn bên trong  1 trọng lượng chẳng bao nhiêu.\n" +
                "Ứng dụng:\n" +
                "-  Liti được dùng trong pin Lithium-ion làm nguồn cho vô số các thiết bị điện tử, từ máy trợ tim đến e ô tô và bao gồm cả máy tính xách tay.\n" +
                "- Lithium stearate cũng được sử dụng phổ biến trong dầu nhờn lithium dùng cho các xe ô tô, xe tải và trong lĩnh vực cơ khí.\n" +
                "-Lithium cacbonat được sử dụng làm thuốc an thần, giúp ân bằng cảm xúc của cơ thể.\n" +
                "- Mỡ lithium thông thường chưa Lithium stearate dùng để cải thiện hiệu suất.\n"))
        grv_bth_list.add(RO_Chemical_Element(4, "Beri", "Be", 4, 4, 5, 9f, "Kiềm thổ", akali_metal_color,"- Beri cứng, nhiệt độ nóng chảy cao, có khả năng chống ăn mòn.\n" +
                "- Trong tự nhiên có tểh tôn tại ở dạng khoáng Beryl xanh lá cây và xanh da trời được gọi là ngọc lục -bảo và ngọc xanh biển. \n" +
                "Ứng dụng:\n" +
                "- Beri nguyen chất được dùng để nấu chảy và trở thành những thành phần bền và nhẹ cho đầu đạn của tên lửa.\n" +
                "- Beri đồng được sử dụng ở đầu của những cây gậy đánh golf.\n" +
                "-Mỏ lết  mở van khí không đánh lửa làm bằng Beri đồng.\n" +
                "- Vật liệu cách điện cao cấp beri oxit.\n" +
                "\n"))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(5, "Bo", "B", 5, 4, 6, 11f, "Bo", non_metal_color,"- Bo cực kì cứng nhưng ko quá giòn.\n" +
                "-“ Boron nitride” dùng trong máy công cụ làm đệm lót đễ cắt các loại thép trui.\n" +
                "- Boric axit được đề xuất dùng cho mọi thứ trừ thuốc nhỏ mắt và thuốc diệt kiến.\n" +
                "- Dung dịch “ Boron cardide” phá hủy động cơ.\n"))
        grv_bth_list.add(RO_Chemical_Element(6, "Cacbon", "C", 5, 4, 6, 12f, "Cacbon", non_metal_color,"- Là nguyên tố quan trọng nhất của chu trình sống.\n" +
                "- “ Hợp chất hưu cơ “ dùng dể chỉ những hóa chất hữu cơ có chưa Cacbon.\n" +
                "- Cacbon tồn tại ở 3 dạng: kim cương, than chì và fuleren.\n" +
                "- Kim cương dùng làm trang sức , kim cương là vĩnh cữu nếu không đun nó quá nóng.\n" +
                "- Các que hàn bằng than chì mạ đồng lun có trong nhưng phân xưởng hàn.\n" +
                "- Than đá thường để rèn và sưởi ấm.\n"))
        grv_bth_list.add(RO_Chemical_Element(7, "Nitơ", "N", 5, 4, 7, 14f, "Nito", non_metal_color,"- N2 khoảng 78% thể tích không khí, là 1 khí trơ.\n" +
                "- NH3 là hợp chất quan trọng của Nitơ, nó trở thành 1 loại phân bón quan trọng(ure).\n" +
                "- Nitơ lỏng ở -1960C nó đủ lạnh để làm đống băng bất cứ thứ gì, được sử dụng để bảo quản các mẫu sinh vật học.\n" +
                "- Si¬3¬N4¬ rất cứng, được sử dụng làm dụng cũ cắt gọt, còn được dùng làm bạc đạn trong các bánh xe trượt ván đắt tiền.\n" +
                "- Những viên thuốc C3H5N3O9 được dùng cho bệnh đau thắt ngực.\n"))
        grv_bth_list.add(RO_Chemical_Element(8, "Oxi", "O", 5, 4, 8, 16f, "Oxi", non_metal_color,"- Oxi chiếm khoảng 21% thể tích không khí.\n" +
                "- Oxi là chất oxi hóa hoạt tính cao.\n" +
                "- Oxi lỏng ở nhiệt độ -1830C và có màu xanh nhạt.\n" +
                "- Các hợp chất của Oxi chiếm 86% trọng lượng của các đại dương và gần một nửa vỏ trái đất.\n" +
                "- Oxi lỏng được dùng làm nhiên liệu dự trữ cho tên lửa.\n" +
                "- Sự sống cần oxi: sự hô hấp của con người, động vật, sự cháy.\n"))
        grv_bth_list.add(RO_Chemical_Element(9, "Flo", "F", 5, 4, 10, 19f, "Flo", non_metal_color,"- Flo là một chất khí màu vàng nhạt.\n" +
                "- Flo là một trong những nguyên tố phản ứng mạnh nhất, thổi một luồng khí Flo vào bất cứ gì và hầu hết chúng sẽ bùng cháy, ngay cả những thứ không dễ bắt lửa như thủy tinh.\n" +
                "- Hợp chất của Flo vởi ổn định cao thường gặp nhất là Teflon là vua của các loại chất dẻo dùng để trán chảo không dính, chai chưa axit, chỉ khâu vết thương.\n" +
                "-Kem đánh răng có chưa Flo.\n"))
        grv_bth_list.add(RO_Chemical_Element(10, "Neon", "Ne", 5, 4, 6, 20f, "Khí trơ", non_metal_color,"- Neon có nghĩa đen là đèn phát sáng.\n" +
                "- Neon hoạt động kém với các nguyên tố và hoàn toàn không phản ứng với các nguyên tố khác.\n" +
                "-Laser helium – neon là chùm tia laser liên tục được đua vào sử dụng trong thương mại.\n" +
                "- Đèn được làm từ neon khi có dòng điện chạy sẽ tạo ra ánh sáng.\n"))

        grv_bth_list.add(RO_Chemical_Element(11, "Natri", "Na", 3, 3, 12, 23f, "Kiềm", akali_metal_color,"- Natri là chất nổ mạnh khi ném nó vào nước.\n" +
                "- Natri có vị ngon nhất trong tất cả các kim loại kiềm.\n" +
                "- NaCl được sử dụng làm muối ăn.\n" +
                "- Natri được sử dụng nhiều trong công nghiệp hóa chất như một chất khử.\n" +
                "- Natri lỏng được sử dụng để tải nhiệt trong lò phản ứng hạt nhân.\n" +
                "- Đèn natri cao áp được sử dụng vì hiệu quá cao, hoàn toàn không tạo ánh sáng khó chịu.\n"))
        grv_bth_list.add(RO_Chemical_Element(12, "Magie", "Mg", 3, 3, 12, 24f, "Kiềm thổ", akali_metal_color,"- Magie có giá vừa phải, cứng, nhẹ và dễ gia công nhưng có nhược điểm là rất dễ cháy.\n" +
                "- Bột Magie làm đén chớp chụp ảnh từ những năm 1920.\n" +
                "- được làm nòng ráp phanh xe hơi.\n" +
                "- Lõi cuộn phim\n" +
                "- Khối Magie làm bộ nhóm lửa trại.\n" +
                "- Bột Magie băng đầu tiên được dùng để phơi sáng khi in ảnh.\n"))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(13, "Nhôm", "Al", 5, 4, 14, 27f, "Bo", non_metal_color,"- Nhôm là kim loại nhẹ và đủ cứng để tạo nên cấu trúc các loại máy.\n" +
                "- Nhôm rất bền trong không khí, không bị rỉ sét do lớp ngoài là Nhôm oxit được tạo ra do nhôm bị oxi chậm bởi oxi của không khí, là 1 trong những chất cứng nhất.\n" +
                "- Bột nhôm là thành phần cơ bản của bột cháy sáng và hỗn hợp nhiên liệ của tên lửa.\n" +
                "- Các bản tản nhiệt sử dụng nhô có độ dẫn nhiệt cao.\n" +
                "- Khăn giữ nhiệt khẩn cấp Myler bọ kim loại Nhôm.\n" +
                "- Đồ dùng nấu nướng được làm bằng Nhôm khá tinh khiết để dẫn nhiệt tốt.\n" +
                "- Phun bột Nhôm với độ tinh khiết cao vào các đĩa ăn.\n"))
        grv_bth_list.add(RO_Chemical_Element(14, "Silic", "Si", 5, 4, 14, 28f, "Cacbon", non_metal_color,"- Silic có hàm lượng lớn thứ 2 trong vỏ trái đất.\n" +
                "- Viên Silic có độ tinh khiết khá thấp nhưng khá dễ nón chảy thu được từ công đoạn đầu tiên làm sạch cát.\n" +
                "- Silic siêu sạch được dùng để chết tạp các con chip.\n" +
                "- Các món đồ chơi của trẻ em có chưa Silic.\n"))
        grv_bth_list.add(RO_Chemical_Element(15, "Photpho", "P", 5, 4, 16, 31f, "Nito", non_metal_color,"- Các dạng thù hình thường gặp của Photpho  là Photpho trắng và Photpho đỏ, Photpho đen là dạng bền nhất nhưng hiếm gặp.\n" +
                "- Photpho trắng rất độc và rất dễ bốc cháy.\n" +
                "- Hợp chất có chứa nhóm PO43-  rất cần cho sự sống và thành phần thiết yếu nhưng có gới hạn trông sự phát triễn của cây lương thực.\n" +
                "- Photpho được sử dụng rộng rãi để làm chất mồi lửa ở các đầu que diêm\n"))
        grv_bth_list.add(RO_Chemical_Element(16, "Lưu huỳnh", "S", 5, 4, 16, 32f, "Oxi", non_metal_color,"- Lưu huỳnh thiên nhiên khá tinh khiết ở xung quanh các núi lửa và lỗ thông hơi nhiệt đới.\n" +
                "- Lưu huỳnh là chất rất nặng mùi dù là ở dạng tinh thể rắn hay là ở dạng bột.\n" +
                "- Mùi đặc trưng của tỏi và hành đều từ những hợp chất của lưu huỳnh.\n" +
                "- Lưu huỳnh là 1 trong 3 thành phần cơ bản của thuốc súng.\n" +
                "- Hợp chất của lưu huỳnh được sản xuất nhiều nhất trê thế giớ là Axit sulfuric.\n" +
                "- Có thể sử dụng lưu huỳnh để xử lý thủy ngân bị rơi vãi.\n"))
        grv_bth_list.add(RO_Chemical_Element(17, "Clo", "Cl", 5, 4, 10, 35f, "Flo", non_metal_color,"- Khí Clo có màu vàng nhạt.\n" +
                "- Khí Clo có thể làm tổn thương đến đường hô hấp khi hít phải.\n" +
                "- Clo được sử dụng làm chất hử trùng giá rẻ thông qua việc xử lí nước uống và nước thải, hồ bơi.\n" +
                "- Clo có trong nhiều hóa chất gia dụng phổ biến như: NaClO( chất tẩy trắng có tính oxi hóa mạnh), NaCl( muối ăn), HCl( axit dạ dày).\n" +
                "- CaCl2 thưởng ở dạng viên để làm tan tuyết và băng.\n" +
                "- Clo trong y tế xưa dùng để làm thuốc xông( trong dung dịch với cồn).\n"))
        grv_bth_list.add(RO_Chemical_Element(18, "Argon", "Ar", 5, 4, 6, 40f, "Khí trơ", non_metal_color,"- Argon có nghĩa là “ không hoạt hóa”, nó được xem là hóa chất trơ nhất.\n" +
                "- Argon tinh khiết là chất khí khống nhìn thấy được.\n" +
                "- Khí có dòng điện kích thích khí, khí argon sẽ sáng rực rỡ màu xanh da trời.\n" +
                "- Bình khí cao áp chứa khí Argon thường đặt trong các phòng thí nghiệm để sử dụng như khí bảo vệ.\n"))

        grv_bth_list.add(RO_Chemical_Element(19, "Kali", "K", 3, 3, 12, 39f, "Kiềm", akali_metal_color,"- kali có màu tím, và phản ứng rất mạnh với nước, khi tiếp xúc với nước chúng sẽ phát nổ,  bắn ra những giọt nhỏ cháy rực màu tím đỏ đặc trưng.\n" +
                "- Kali cực tinh khiết không bị oxi hóa là 1 kim loại sáng bóng.\n" +
                "-  Kali có rất nhiều trong chuối .\n" +
                "-  1% trong nguyên tử Kali trên trái đất khoảng 1/100 là các đồng vị phóng xạ 40K, quá nhiều 40K trong trái đất sơ khai sẽ ngăn chặn sự hình thành của hệ gen, làm cho tỷ lệ đột biến chậm kéo theo sự tiến hóa chậm.\n" +
                "- Trong cơ thể Kali tồn tại ở dạng K+ rất quan torng5 để dẫn truyền thần kinh, nếu nồng độ quá thấp những ngón tay sẽ bắt đầu tê cứng cục bộ và dẫn đến tử vong nếu thiếu hụt lan tới tim.\n" +
                "- K2CO3 là 1 loại phân bón thông dụng.\n"))
        grv_bth_list.add(RO_Chemical_Element(20, "Canxi", "Ca", 3, 3, 12, 40f, "Kiềm thổ", akali_metal_color,"- Canxi tinh khiết là một kim lại sáng bóng.\n" +
                "- Canxi là thành phần chính của sự khoáng hóa xương( xương động vật có vú là một dạng canxi photohat ngậm nước).\n" +
                "- Ca+ là nền tảng trong quá trình hóa sinh của tế bào.\n" +
                "- Phấn việt bảng được làm bằng thạch cao là 1 hợp chất của canxi.\n" +
                "- CaH2 \n"))

        grv_bth_list.add(RO_Chemical_Element(21, "Scanđi", "Sc", 3, 3, 24, 45f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(22, "Titan", "Ti", 3, 3, 12, 48f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(23, "Vanađi", "V", 3, 3, 12, 51f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(24, "Crôm", "Cr", 3, 3, 12, 52f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(25, "Mangan", "Mn", 3, 3, 12, 55f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(26, "Sắt", "Fe", 3, 3, 12, 56f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(27, "Coban", "Co", 3, 3, 12, 59f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(28, "Niken", "Ni", 3, 3, 12, 59f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(29, "Đồng", "Cu", 3, 3, 12, 64f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(30, "Kẽm", "Zn", 3, 3, 12, 65f, "IIIB", group_b_color,""))

        grv_bth_list.add(RO_Chemical_Element(31, "Gali", "Ga", 5, 4, 14, 70f, "Bo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(32, "Gemani", "Ge", 5, 4, 14, 73f, "Cacbon", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(33, "Asen", "As", 5, 4, 16, 75f, "Nito", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(34, "Selen", "Se", 5, 4, 16, 79f, "Oxi", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(35, "Brom", "Br", 5, 4, 10, 80f, "Flo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(36, "Kripton", "Kr", 5, 4, 6, 84f, "Khí trơ", non_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(37, "Rubiđi", "Rb", 3, 3, 12, 86f, "Kiềm", akali_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(38, "Stronti", "Sr", 3, 3, 12, 88f, "Kiềm thổ", akali_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(39, "Ytri", "Y", 3, 3, 24, 89f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(40, "Ziriconi", "Zr", 3, 3, 12, 91f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(41, "Niobi", "Nb", 3, 3, 12, 93f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(42, "Molipđen", "Mo", 3, 3, 12, 96f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(43, "Tecnexi", "Tc", 3, 3, 12, 99f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(44, "Ruteni", "Ru", 3, 3, 12, 101f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(45, "Rođi", "Rh", 3, 3, 12, 103f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(46, "Palađi", "Pd", 3, 3, 12, 106f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(47, "Bạc", "Ag", 3, 3, 12, 108f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(48, "Cađimi", "Cd", 3, 3, 12, 113f, "IIIB", group_b_color,""))

        grv_bth_list.add(RO_Chemical_Element(49, "Inđi", "In", 5, 4, 14, 115f, "Bo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(50, "Thiếc", "Sn", 5, 4, 14, 118f, "Cacbon", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(51, "Antimon", "An", 5, 4, 16, 122f, "Nito", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(52, "Telu", "Te", 5, 4, 16, 128f, "Oxi", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(53, "Iot", "I", 5, 4, 10, 127f, "Flo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(54, "Xenon", "Xe", 5, 4, 6, 131f, "Khí trơ", non_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(55, "Xesi", "Cs", 3, 3, 12, 86f, "Kiềm", akali_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(56, "Bari", "Ba", 3, 3, 12, 88f, "Kiềm thổ", akali_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(57, "Lantan", "La", 3, 3, 24, 89f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(58, "Hafini", "Hf", 3, 3, 12, 91f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(59, "Tantan", "Ta", 3, 3, 12, 93f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(61, "Volfam", "W", 3, 3, 12, 96f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(62, "Reni", "Re", 3, 3, 12, 99f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(63, "Osimi", "Os", 3, 3, 12, 101f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(64, "Iriđi", "Ir", 3, 3, 12, 103f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(65, "Platin", "Pt", 3, 3, 12, 106f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(66, "Vàng", "Au", 3, 3, 12, 108f, "IIIB", group_b_color,""))
        grv_bth_list.add(RO_Chemical_Element(67, "Thủy ngân", "Hg", 3, 3, 12, 113f, "IIIB", group_b_color,""))

        grv_bth_list.add(RO_Chemical_Element(68, "Tali", "Tl", 5, 4, 14, 115f, "Bo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(69, "Chì", "Pb", 5, 4, 14, 118f, "Cacbon", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(70, "Bitmut", "Bi", 5, 4, 16, 122f, "Nito", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(71, "Poloni", "Po", 5, 4, 16, 128f, "Oxi", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(72, "Atatin", "At", 5, 4, 10, 127f, "Flo", non_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(73, "Rađon", "Rn", 5, 4, 6, 131f, "Khí trơ", non_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(74, "Franxi", "Fr", 3, 3, 12, 86f, "Kiềm", akali_metal_color,""))
        grv_bth_list.add(RO_Chemical_Element(75, "Rađi", "Ra", 3, 3, 12, 88f, "Kiềm thổ", akali_metal_color,""))

        grv_bth_list.add(RO_Chemical_Element(76, "Actini", "Ac", 3, 3, 24, 89f, "IIIB", group_b_color,""))

    }

    override fun addControl() {
        grv_bth_adapter = BangTuanHoang_GridView_Adapter(this)
        grv_bth_adapter.adaptFor(grv_bang_tuan_hoan!!)
        grv_bth_adapter.setData(grv_bth_list)

        rcv_element_search_adapter = Search_Chem_Element_Adapter(this)
        rcv_element_search_adapter.adaptFor(rcv_bangtuanhoan_chem_element_search!!)
        rcv_element_search_adapter.setData(grv_bth_list)
        rcv_element_search_adapter.observe(edt_bangtuanhoan_search!!)

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_bang_tuan_hoan!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_bang_tuan_hoan!!.visibility = View.INVISIBLE
        }
    }

    override fun addEvent() {

        btn_bangtuanhoan_back!!.setOnClickListener { v -> finish() }

        grv_bth_adapter.setOnItemClickListener (object : BangTuanHoang_GridView_Adapter.OnItemClickListener {
            override fun onItemClickListener(chem_element: RO_Chemical_Element) {
                val intent = Intent(this@BangTuanHoangActivity, ChemicalElementActivity::class.java)
                intent.putExtra(CHEM_ELEMENT, chem_element)
                startActivity(intent)
                hideVirtualKeyboard()
            }
        })

        rcv_element_search_adapter.setOnItemClickListener (object : Search_Chem_Element_Adapter.OnItemClickListener {
            override fun onItemClick(element: RO_Chemical_Element) {
                bg_edt_search!!.visibility = GONE
                val intent = Intent(this@BangTuanHoangActivity, ChemicalElementActivity::class.java)
                intent.putExtra(CHEM_ELEMENT, element)
                startActivity(intent)
                hideVirtualKeyboard()
            }
        })

        edt_bangtuanhoan_search.setOnHideVirtualKeyboardListener(object : VirtualKeyBoardSensor.OnHideVirtualKeyboardListener {
            override fun onHide() {
                makeFullScreen()
            }
        })

        btn_bottom_bangtuanhoan_search!!.setOnClickListener { v ->
            bangtuanhoan_search_chem_element_view_parent!!.startAnimation(fade_in)
            edt_bangtuanhoan_search!!.requestFocus()
            showVirtualKeyboard()
        }

        btn_top_bangtuanhoan_turn_off_search!!.setOnClickListener { v ->
            bangtuanhoan_search_chem_element_view_parent!!.startAnimation(fade_out)
            hideVirtualKeyboard()
            edt_bangtuanhoan_search!!.setText("")
        }
    }

    override fun loadAnim() {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fade_in!!.fillAfter = false

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fade_out!!.fillAfter = false

        fade_out!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                bangtuanhoan_search_chem_element_view_parent!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fade_in!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                bangtuanhoan_search_chem_element_view_parent!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun hideVirtualKeyboard() {
        virtualKeyboardManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun showVirtualKeyboard() {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun setPeriodicTableWidth() {
        ln_bth_gridview_Parent!!.layoutParams.width = 18 * UnitConverter.DpToPixel(CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE) + 17 * UnitConverter.DpToPixel(3)
    }

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_bangtuanhoan_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_bangtuanhoan_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    companion object {
        val CHEM_ELEMENT = "chem_element"
    }

}
