package com.example.monitoringapp.ui.doctor.patientspriority

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.databinding.FragmentPatientsPriorityBinding
import com.example.monitoringapp.ui.adapter.PatientsByPriorityAdapter
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientsPriorityFragment : Fragment() {

    private val patientsPriorityViewModel: PatientsPriorityViewModel by viewModels()

    
    private var _binding: FragmentPatientsPriorityBinding? = null
    private val binding get() = _binding!!


    private lateinit var patientsByPriorityAdapter: PatientsByPriorityAdapter


    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var startDate = 1641016800000
    private var endDate = 1704002400000
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientsPriorityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            textStartDate.setOnClickListener {
                showDatePickerDialogStartDate()
            }

            textEndDate.setOnClickListener {
                showDatePickerDialogEndDate()
            }

            imageStartDate.setOnClickListener {
                showDatePickerDialogStartDate()
            }

            imageEndDate.setOnClickListener {
                showDatePickerDialogEndDate()
            }

            buttonSearch.setOnClickListener {
                patientsPriorityViewModel.getPatientsByPriority(1, startDate.toString())
            }
        }
    }
    
    private fun setupObservers() {
        patientsPriorityViewModel.uiViewGetPatientsByPriorityStateObservable.observe(
            viewLifecycleOwner,
            getPatientsPriorityObserver
        )
    }

    //Observers
    private val getPatientsPriorityObserver = Observer<UIViewState<List<PriorityType>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val prioritiesObserver = it.result
                binding.run {
                    patientsByPriorityAdapter = PatientsByPriorityAdapter(prioritiesObserver)
                    recycler.adapter = patientsByPriorityAdapter
                }
            }
            is UIViewState.Loading -> {
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.gone()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }
    
    private fun showDatePickerDialogStartDate() {
        val c: Calendar = Calendar.getInstance()
        c.time = currentDate
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                c.set(mYear, mMonth, mDay, 0, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textStartDate.text = textCalendar

                startDate = c.timeInMillis

            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

    private fun showDatePickerDialogEndDate() {
        val c: Calendar = Calendar.getInstance()
        c.time = currentDate
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                c.set(mYear, mMonth, mDay, 0, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textEndDate.text = textCalendar

                endDate = c.timeInMillis
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}