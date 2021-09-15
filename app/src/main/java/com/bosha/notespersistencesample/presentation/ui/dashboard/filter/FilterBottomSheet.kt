package com.bosha.notespersistencesample.presentation.ui.dashboard.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.databinding.FragmentFilterBottomSheetBinding
import com.bosha.notespersistencesample.presentation.ui.MainActivity
import com.bosha.notespersistencesample.presentation.ui.MainViewModel
import com.bosha.notespersistencesample.presentation.ui.ViewModelFactory
import com.bosha.notespersistencesample.presentation.utils.ColorPicker
import com.bosha.notespersistencesample.presentation.utils.ColorPicker.Companion.doOnColorClick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class FilterBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainScreenComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initColorPicker()

        //if filter params is not empty, fill them
        binding.switchFilterHigh.isChecked = viewModel.mOnlyHigh
        binding.etSearch.editText?.text?.append(viewModel.mSearchByTitle)

        binding.switchFilterHigh.setOnCheckedChangeListener { _, isChecked ->
            viewModel.search( binding.etSearch.editText?.text.toString(), isChecked)
        }

        binding.etSearch.editText?.doAfterTextChanged {
            viewModel.search(it.toString())
        }

        binding.ibCancelFilter.setOnClickListener {
            viewModel.clearFilter()
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initColorPicker() {
        binding.bColorButton.setBackgroundColor(requireContext().getColor(viewModel.mColorSearchFilter
            ?: R.color.material_on_background_disabled ))

        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.colorPicker.visibility = View.VISIBLE
        }
        /**
         * Set clicklistener on each slot from [ColorPicker]
         */
        binding.includeColorPicker.layoutColorHolder.doOnColorClick {
            binding.bColorButton.apply {
                viewModel.search(
                    searchByTitle = binding.etSearch.editText?.text.toString(),
                    colorSearchFilter = ColorPicker().getPickedColor(it)
                )
                setBackgroundColor(
                    requireContext().getColor(
                        viewModel.mColorSearchFilter ?: R.color.material_on_background_disabled
                    )
                )
                visibility = View.VISIBLE
            }
            binding.colorPicker.visibility = View.GONE
        }
    }
}