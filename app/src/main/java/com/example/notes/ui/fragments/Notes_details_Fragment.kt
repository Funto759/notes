package com.example.notes.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notes.Notes
import com.example.notes.R
import com.example.notes.databinding.NotesDetailsBinding
import com.example.notes.ui.MainActivity
import com.example.notes.ui.NotesViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Notes_details_Fragment : Fragment(R.layout.notes_details) {

   private var _binding : NotesDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).viewModel

        val id = requireArguments().getLong("noteId")

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        notesViewModel.getNotesById(id).observe(viewLifecycleOwner, Observer {

            it?.let {
                binding.title.text = Editable.Factory.getInstance().newEditable(it.title)
                binding.content.text = Editable.Factory.getInstance().newEditable(it.content)
                binding.date.text = it.date
            }

        })

        binding.save.setOnClickListener {
            val updatedTittle = binding.title.text.toString()
            val updatedContent = binding.content.text.toString()
            val updatetdDate = getCurrentTime()

            val updatedNotes = Notes(updatedTittle,updatedContent,updatetdDate,id)
            notesViewModel.saveNotes(updatedNotes)
            Toast.makeText(requireContext(),"Note Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            var title = binding.title.text.toString()
            var content = binding.content.text.toString()
            var date = binding.date.text.toString()

            val notes = Notes(title,content,date,id)
            notesViewModel.deleteNotes(notes)
            Toast.makeText(requireContext(),"Note Deleted",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime():String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM-dd  HH:mm")
        return current.format(formatter)
    }
}