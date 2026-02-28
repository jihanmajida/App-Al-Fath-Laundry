import com.google.gson.annotations.SerializedName

data class AddGrupRequest(
    @SerializedName("id") val id: Int? = null, // Tambahkan ini untuk Update
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("jam") val jam: String,
    @SerializedName("kamar") val kamar: String,
    @SerializedName("berat") val berat: Double,
    @SerializedName("jenis_pakaian") val jenisPakaian: String,
    @SerializedName("jumlah_orang") val jumlahOrang: Int,
    @SerializedName("status_data") val statusData: String = "0", // Beri default value
    @SerializedName("pelanggan") val pelanggan: List<PelangganRequest>,
    @SerializedName("detail_laundry") val detail_laundry: List<DetailLaundryRequest>
)

data class PelangganRequest(
    val nama_pelanggan: String
)

data class DetailLaundryRequest(
    @SerializedName("baju") val baju : Int,
    @SerializedName("jilbab") val jilbab: Int,
    @SerializedName("rok") val rok : Int,
    @SerializedName("kaos") val kaos : Int,
    @SerializedName("keterangan") val keterangan : String?
)