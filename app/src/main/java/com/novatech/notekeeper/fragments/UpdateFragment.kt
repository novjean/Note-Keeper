package com.novatech.notekeeper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.novatech.notekeeper.MainActivity
import com.novatech.notekeeper.R
import com.novatech.notekeeper.adapter.NoteAdapter
import com.novatech.notekeeper.databinding.FragmentHomeBinding
import com.novatech.notekeeper.databinding.FragmentUpdateBinding
import com.novatech.notekeeper.model.Note
import com.novatech.notekeeper.viewmodel.NoteViewModel


class UpdateFragment : Fragment(R.layout.fragment_update) {
    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel

    private lateinit var currentNote: Note

    // since the update fragment contains arguments in nav_graph
    private val args : UpdateFragmentArgs by navArgs()

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        //if the user update the note
        binding.fabDone.setOnClickListener{
            val title = binding.etNoteTitleUpdate.text.toString()
            val body = binding.etNoteBodyUpdate.text.toString()

            if(title.isNotEmpty()){
                val note = Note(currentNote.id, title, body)
                notesViewModel.updateNote(note)

                view.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Please enter note title!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteNote() {
        
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(view: View){
        val noteTitle = binding.etNoteTitleUpdate.text.toString().trim()
        val noteBody = binding.etNoteBodyUpdate.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            // passing 0 as primary field is auto generate
            val note = Note(0, noteTitle, noteBody)

            notesViewModel.addNote(note)

            Toast.makeText(context, "Note saved successfully",
                Toast.LENGTH_LONG).show()

            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(context, "Please enter note title", Toast.LENGTH_LONG).show()
        }
    }


}