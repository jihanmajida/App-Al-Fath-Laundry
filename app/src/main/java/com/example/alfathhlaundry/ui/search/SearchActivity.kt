package com.example.alfathhlaundry.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.network.RetrofitClient
import com.example.alfathhlaundry.data.repository.GrupRepository
import com.example.alfathhlaundry.ui.adapter.SearchAdapter
import com.example.alfathhlaundry.ui.showdata.ShowDataActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var tvEmpty: TextView

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            GrupRepository(RetrofitClient.getInstance(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        setupRecyclerView()
        setupAction()
        observeViewModel()
    }

    private fun initView() {
        rvSearch = findViewById(R.id.rvSearch)
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
        tvEmpty = findViewById(R.id.tvEmpty)
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter { grup ->
            val intent = Intent(this, ShowDataActivity::class.java)
            intent.putExtra("ID_GRUP", grup.id_grup)
            startActivity(intent)
        }

        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.adapter = adapter
    }

    private fun setupAction() {

        btnBack.setOnClickListener {
            finish()
        }

        btnSearch.setOnClickListener {
            val keyword = etSearch.text.toString().trim()

            if (keyword.isEmpty()) {
                Toast.makeText(this, "Masukkan kata kunci", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.search(keyword)
        }
    }

    private fun observeViewModel() {

        viewModel.searchResult.observe(this) { data ->

            adapter.updateData(data)

            if (data.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
                rvSearch.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                rvSearch.visibility = View.VISIBLE
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}