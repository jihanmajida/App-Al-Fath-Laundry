package com.example.alfathhlaundry.ui.home.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.ui.adapter.ListDataAdapter
import com.example.alfathhlaundry.ui.grup.AddEditGroupActivity
import com.example.alfathhlaundry.ui.home.HomeViewModel
import com.example.alfathhlaundry.ui.showdata.ShowDataActivity

class ListDataFragment : Fragment() {

    private lateinit var rvListData: RecyclerView
    private lateinit var adapter: ListDataAdapter

    private val viewModel: HomeViewModel by activityViewModels()

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

        setupAdapter()
        observeData()
    }

    private fun setupAdapter() {

        adapter = ListDataAdapter(
            emptyList(),

            onItemClick = { item ->
                val intent = Intent(requireContext(), ShowDataActivity::class.java)
                intent.putExtra("ID_GRUP", item.id_grup)
                startActivity(intent)
            },

            onEditClick = { item ->
                val intent = Intent(requireContext(), AddEditGroupActivity::class.java)
                intent.putExtra("MODE", "EDIT")
                intent.putExtra("ID_GRUP", item.id_grup)
                startActivity(intent)
            },

            onDeleteClick = { item ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin menghapus data?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.deleteGrup(item.id_grup)
                    }
                    .setNegativeButton("Tidak", null)
                    .show()
            },

            onStatusChange = { item, isChecked ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin mengubah status data?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.updateStatus(
                            item.id_grup,
                            if (isChecked) "1" else "0"
                        )
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                        viewModel.reload() // rollback checkbox
                    }
                    .show()
            }
        )

        rvListData.adapter = adapter
    }

    private fun observeData() {
        viewModel.grupData.observe(viewLifecycleOwner) { data ->
            adapter.updateData(data)
        }
    }
}