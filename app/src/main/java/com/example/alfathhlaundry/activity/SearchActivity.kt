package com.example.alfathhlaundry.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.DataStorage.DataStorage
import com.example.alfathhlaundry.adapter.SearchAdapter
import com.example.alfathhlaundry.model.GrupWithCustomer

class SearchActivity : AppCompatActivity() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var tvEmpty: TextView

    private lateinit var adapter: SearchAdapter

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
        tvEmpty = findViewById(R.id.tvEmpty)

    }

    private fun setupRecyclerView() {

        adapter = SearchAdapter(emptyList()) { grup ->
            val intent = Intent(this, ShowDataActivity::class.java)
            intent.putExtra("DATA_GRUP", grup)
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
        val filtered = DataStorage.listGrup.filter { grup ->
            grup.customers.any {
                it.nama.contains(keyword, ignoreCase = true)
            }
        }
            .reversed()

        adapter.updateData(filtered)

        if (filtered.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rvSearch.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            rvSearch.visibility = View.VISIBLE
        }
    }
}