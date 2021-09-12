package com.bosha.notespersistencesample.presentation.ui.addition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.requireViewById
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.data.utils.toISOFormat
import com.bosha.notespersistencesample.databinding.FragmentAdditionBinding
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.presentation.di.MainScreen
import com.bosha.notespersistencesample.presentation.ui.ViewModelFactory
import com.bosha.notespersistencesample.presentation.utils.ColorPickerMap
import com.bosha.notespersistencesample.presentation.utils.validateFields
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null
    val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AdditionViewModel by viewModels { viewModelFactory }

    private val isEdit by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getBoolean(KEY_IS_EDIT, false) == true
    }
    private val noteArgument by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable<Note>(KEY_NOTE_ARG)
    }

    //color that picked after click on colorPicker
    var checkedColor: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        if (isEdit) {
            binding.bDelete.visibility = View.VISIBLE
            binding.tvCreateDate.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isEdit) {
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.editNote)
        }
        _binding = FragmentAdditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initColorPicker()
        //if edit mode enabled, fill in the fields with the note data
        if (isEdit) initFields()
        //submit handler
        setSubmitListener()
        //handle add/edit/delete state as ActonState
        viewModel.actionStateFlow
            .onEach(::actionStateHandle)
            .launchIn(lifecycleScope)
    }

    private fun setSubmitListener() {
        binding.bSubmit.setOnClickListener {
            if (validateFields()) {
                binding.apply {
                    val note = Note(
                        title = tvTitle.editText?.text.toString(),
                        colorId = requireNotNull(checkedColor),
                        createDate = Date().time,
                        id = if (isEdit) noteArgument?.id.toString() else "",
                        description = etDescription.editText?.text?.toString() ?: "",
                        priority = Note.Priority.valueOf(sPriorityLayout.editText?.text.toString()).ordinal,
                        type = Note.Type.valueOf(
                            requireViewById<RadioButton>(
                                rgType,
                                rgType.checkedRadioButtonId
                            ).text.toString().uppercase()
                        ).ordinal
                    )
                    viewModel.addNote(note)
                }
            }
        }
    }

    private fun initFields() {
        val note = requireNotNull(noteArgument)
        binding.apply {
            tvTitle.editText?.text?.append(note.title)
            etDescription.editText?.text?.append(note.description)
            rgType.check(rgType[note.type].id)
            binding.priorityDropdown.setText(
                Note.Priority.values()[note.priority].toString(),
                false
            )
            checkedColor = note.colorId
            tvCreateDate.append(" ${Date(note.createDate).toISOFormat()}")
            bColorButton.setBackgroundColor(requireContext().getColor(requireNotNull(checkedColor)))
            //and set listener to the delete button
            binding.bDelete.setOnClickListener {
                viewModel.delete(note.id)
            }
        }
    }

    private fun initColorPicker() {
        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.frameColorPicker.visibility = View.VISIBLE
        }
        binding.includeColorPicker.layoutColorHolder.forEach {
            it.setOnClickListener {
                binding.bColorButton.apply {
                    checkedColor = ColorPickerMap().pickedColor(it)
                    setBackgroundColor(
                        requireContext().getColor(
                            checkedColor ?: R.color.secondaryColor_600
                        )
                    )
                    visibility = View.VISIBLE
                }
                binding.frameColorPicker.visibility = View.GONE
            }
        }
    }

    private fun initSpinner() {
        val priorityList = listOf(
            Note.Priority.LOW.name,
            Note.Priority.MEDIUM.name,
            Note.Priority.HIGH.name
        )
        binding.priorityDropdown.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.priority_dropdown_item,
                    priorityList
                )
            )
            //Priority.HIGH by default
            if (!isEdit) setText(Note.Priority.HIGH.name, false)
        }
    }

    private fun actionStateHandle(actionState: AdditionViewModel.ActionState) {
        when (actionState) {
            AdditionViewModel.ActionState.COMPLETE -> findNavController().navigateUp()

            AdditionViewModel.ActionState.EMPTY -> {
            }

            AdditionViewModel.ActionState.LOADING ->
                Toast.makeText(requireContext(), "In process...", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_IS_EDIT = "is_edit"
        const val KEY_NOTE_ARG = "note_argument"
    }
}