package com.novatech.notekeeper.fragments

import android.app.AlertDialog
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
import com.novatech.notekeeper.databinding.FragmentUpdateBinding
import com.novatech.notekeeper.model.Note
import com.novatech.notekeeper.viewmodel.NoteViewModel


class UpdateFragment : Fragment(R.layout.fragment_update) {
    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel

    // since the update fragment contains arguments in nav_graph
    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var currentNote: Note

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
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateFragment_to_homeFragment)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
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
            R.id.menu_delete -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}