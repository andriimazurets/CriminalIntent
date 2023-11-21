package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import com.example.criminalintent.model.Crime

class CrimeListFragment : Fragment() {
    private val TAG = "CrimeListFragment"
    private lateinit var binding: FragmentCrimeListBinding

    private var adapter: CrimeAdapter? = null

    private lateinit var crimeRecyclerView: RecyclerView

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrimeListBinding.inflate(inflater)
        crimeRecyclerView = binding.crimeRecyclerView
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUi()
        return binding.root
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            return CrimeHolder(layoutInflater.inflate(R.layout.list_item_crime, parent, false))
        }

        override fun getItemCount(): Int = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }

    }

    private fun updateUi() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): CrimeListFragment = CrimeListFragment()
    }
}