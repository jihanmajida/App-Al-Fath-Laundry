import android.os.Parcelable
import com.example.alfathhlaundry.data.model.user.DetailLaundry
import com.example.alfathhlaundry.data.model.user.Pelanggan
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrupWithCustomer(
    @SerializedName("id_grup") val id_grup: Int,
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("jam") val jam: String,
    @SerializedName("kamar") val kamar: String,
    @SerializedName("berat") val berat: Double,
    @SerializedName("jenis_pakaian") val jenis_pakaian: String,
    @SerializedName("jumlah_orang") val jumlah_orang: Int,
    @SerializedName("status_data") val status_data: String,
    @SerializedName("created_at") val created_at: String? = null,
    @SerializedName("updated_at") val updated_at: String? = null,

    @SerializedName("pelanggan")
    val pelanggan: List<Pelanggan> = emptyList(),

    @SerializedName("detail_laundry")
    val detail_laundry: List<DetailLaundry> = emptyList()
) : Parcelable