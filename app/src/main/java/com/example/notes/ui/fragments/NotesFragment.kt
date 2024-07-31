package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.models.NotesAdapter
import com.example.notes.Notes
import com.example.notes.R
import com.example.notes.databinding.FragmentNotesBinding
import com.example.notes.ui.MainActivity
import com.example.notes.ui.NotesViewModel
import com.example.notes.ui.fragments.NotesFragmentDirections

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel:NotesViewModel
    lateinit var notesAdapter: NotesAdapter
//    lateinit var notes: Notes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()

        notesAdapter.setOnItemClickListener { notes ->
            val noteId = notes.id

            val bundle = bundleOf("noteId" to noteId)

            findNavController().navigate(R.id.action_notesFragment_to_notes_details_Fragment, bundle)

        }

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_notes_Full_Page_Fragment)
        }

        viewModel.getNotes().observe(viewLifecycleOwner, Observer { notes ->
            notesAdapter.differ.submitList(notes)
        })

        var searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchNotes(it).observe(requireActivity(), Observer { notes ->
                        notes?.let { notesAdapter.differ.submitList(it) }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchNotes(it).observe(requireActivity(), Observer {notes ->
                        notes?.let { notesAdapter.differ.submitList(it) }
                    })
                }
                return true
            }
        })


    }

    private fun setUpRecyclerView(){
        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }
}


