package com.example.alfathhlaundry

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListDataFragment : Fragment() {

    private lateinit var rvListData: RecyclerView
    private lateinit var adapter: ListDataAdapter
    private val listData = mutableListOf<ItemListData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvListData = view.findViewById(R.id.rvListData)
        rvListData.layoutManager = LinearLayoutManager(requireContext())

        adapter = ListDataAdapter(
            listData,
            onEditClick = { item ->
                //aksi edit
                val intent = Intent(requireContext(), EditDataActivity::class.java)
                intent.putExtra("berat", item.berat)
                intent.putExtra("judul", item.judul)
                intent.putExtra("nama", item.nama)
                intent.putExtra("status", item.status)
                startActivity(intent)
            },
            onDeleteClick = { item ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menghapus data?")
                    .setPositiveButton("Ya") { _, _ ->
                        listData.remove(item)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            },
            onStatusChange = { item, isChecked ->

                AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin mengubah status data?")
                    .setPositiveButton("Ya") { _, _ ->
                        item.status = isChecked
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                        adapter.notifyDataSetChanged()
                    }
                    .show()
            }
        )

        rvListData.adapter = adapter

        adapter.notifyDataSetChanged()
    }
}