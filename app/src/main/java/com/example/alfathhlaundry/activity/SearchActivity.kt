package com.example.alfathhlaundry.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.adapter.SearchAdapter
import com.example.alfathhlaundry.model.Search

class SearchActivity : AppCompatActivity() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageView
    private lateinit var btnBack: ImageView

    private lateinit var adapter: SearchAdapter
    private var listGrup = mutableListOf<Search>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        setupRecyclerView()
        setupAction()
    }

    private fun initView() {
        rvSearch = findViewById(R.id.rvSearch)
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(emptyList()) { grup ->
            val intent = Intent(this, ShowDataActivity::class.java)
            intent.putExtra("id_grup", grup.idGrup)
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
            searchData(keyword)
        }
    }

    private fun searchData(keyword: String) {

        val filtered = listGrup.filter { grup ->
            grup.listNamaPelanggan.any {
                it.contains(keyword, ignoreCase = true)
            }
        }
            .sortedByDescending { it.createdAt } // terbaru → terlama

        adapter.updateData(filtered)
    }
}