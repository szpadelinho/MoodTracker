package com.example.moodtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.adapter.MyAdapter
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.Mood

class MoodHistoryFragment : Fragment(), MyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_moods)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //val moodList = FakeMoodRepository.moodList
        adapter = MyAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        val currentItemList = FakeMoodRepository.moodList
        adapter.itemList.clear()
        adapter.itemList.addAll(currentItemList)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(mood: Mood){
        val direction = MoodHistoryFragmentDirections.moodHistoryFragmentToMoodDetailsFragment(mood)
        findNavController().navigate(direction)
    }
}