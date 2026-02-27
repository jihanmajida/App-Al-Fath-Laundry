import android.os.Parcelable
import com.example.alfathhlaundry.data.model.user.DetailLaundry
import com.example.alfathhlaundry.data.model.user.Pelanggan
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrupWithCustomer(
    val id_grup: Int,
    val id_user: Int,
    val tanggal: String,
    val jam: String,
    val kamar: String,
    val berat: Double,
    val jenis_pakaian: String,
    val jumlah_orang: Int,
    val status_data: String,
    val created_at: String,
    val updated_at: String,
    val pelanggan: List<Pelanggan>,
    val detailLaundry: List<DetailLaundry>
) : Parcelable