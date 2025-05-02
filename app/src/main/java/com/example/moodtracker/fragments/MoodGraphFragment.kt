package com.example.moodtracker.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.Locale

class MoodGraphFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.line_chart)
        pieChart = view.findViewById(R.id.pie_chart)

        setupLineChart()
        loadLineChart()

        setupPieChart()
        loadPieChart()
    }

    private fun setupLineChart(){
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index >= 0 && index < FakeMoodRepository.moodList.size) {
                    index.toString()
                } else {
                    ""
                }
            }
        }

        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.setDrawGridLines(true)
        yAxisLeft.axisMinimum = 0f
        yAxisLeft.axisMaximum = 5f

        lineChart.axisRight.isEnabled = false
    }

    private fun loadLineChart() {
        val entries = ArrayList<Entry>()
        FakeMoodRepository.moodList.forEachIndexed { index, mood ->
            entries.add(Entry(index.toFloat(), mood.rating))
        }

        val dataSet = LineDataSet(entries, "Ocena nastroju")
        dataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setDrawCircleHole(false)
        dataSet.valueTextSize = 10f
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun setupPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61f
    }

    private fun loadPieChart() {
        val moodCounts = HashMap<String, Int>()
        FakeMoodRepository.moodList.forEach { mood ->
            moodCounts[mood.feeling] = (moodCounts[mood.feeling] ?: 0) + 1
        }

        val entries = ArrayList<PieEntry>()
        moodCounts.forEach { (feeling, count) ->
            entries.add(PieEntry(count.toFloat(), feeling))
        }

        val dataSet = PieDataSet(entries, "Moods")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()

        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }

}