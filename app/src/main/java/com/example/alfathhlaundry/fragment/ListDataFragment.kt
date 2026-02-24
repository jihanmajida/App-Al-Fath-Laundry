package com.example.alfathhlaundry.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.model.ItemListData
import com.example.alfathhlaundry.adapter.ListDataAdapter
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.activity.AddEditGroupActivity
import com.example.alfathhlaundry.activity.ShowDataActivity

/**
 * A simple [Fragment] subclass.
 * Use the [ListDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListDataFragment : Fragment() {

    private lateinit var rvListData: RecyclerView
    private lateinit var adapter: ListDataAdapter
    private var listData = mutableListOf<ItemListData>()

    companion object{
        private const val ARG_DATA = "ARG_DATA"

        fun newInstance(data: ArrayList<ItemListData>): ListDataFragment {
            val fragment = ListDataFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_DATA, data)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            listData = (it.getSerializable(ARG_DATA) as? ArrayList<ItemListData>)
                ?: arrayListOf()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvListData = view.findViewById(R.id.rvListData)
        rvListData.layoutManager = LinearLayoutManager(requireContext())

        adapter = ListDataAdapter(
            listData,

            //Klik Sluruh item -> ShowDataActivity
            onItemClick = { item ->
                val intent = Intent(requireContext(), ShowDataActivity::class.java)
                intent.putExtra("DATA_ITEM", item)
                startActivity(intent)
            },

            //Klik button edit
            onEditClick = { item ->
                val intent = Intent(requireContext(), AddEditGroupActivity::class.java)
                intent.putExtra("DATA_ITEM", item)
                startActivity(intent)
            },

            //Klik Button delete
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

            //Klik Button status
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
    }
}