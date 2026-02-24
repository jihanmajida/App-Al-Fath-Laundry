package com.example.alfathhlaundry.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alfathhlaundry.R

class EmptyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_empty, container, false)
        view.findViewById<TextView>(R.id.tvEmpty)
            .text = "Data pada tanggal ini tidak ditemukan"
        return view
    }
}