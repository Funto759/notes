package com.example.notes.ui.fragments

import android.health.connect.datatypes.units.Length
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notes.Notes
import com.example.notes.R
import com.example.notes.databinding.NoteFullPageBinding
import com.example.notes.ui.MainActivity
import com.example.notes.ui.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Notes_Full_Page_Fragment : Fragment(R.layout.note_full_page){

    private var _binding: NoteFullPageBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NotesViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NoteFullPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.date.text = getCurrentTime()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.save.setOnClickListener {
            var title = binding.title.text.toString()
            var content = binding.content.text.toString()
            var date = getCurrentTime()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val notes = Notes(title,content,date)
                viewModel.saveNotes(notes)
                Toast.makeText(requireActivity(),"Saved Succesfully", Toast.LENGTH_SHORT ).show()
                findNavController().popBackStack()

            } else{
                Toast.makeText(requireContext(),"Please Fill in all Fields",Toast.LENGTH_SHORT).show()
            }



        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime():String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM-dd  HH:mm")
        return current.format(formatter)
    }
}