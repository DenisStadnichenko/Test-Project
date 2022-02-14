package com.example.testproject.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.testproject.R

abstract class BaseBindingFragment<ViewBindingType : ViewBinding> : Fragment(), LifecycleObserver {

    private var _binding: ViewBindingType? = null

    protected val binding
        get() = requireNotNull(_binding)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = setupViewBinding(inflater, container)

        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(this)
        initUI()
        initModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackKeyReceived()
        }
    }

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBindingType

    abstract fun initUI()
    abstract fun initModel()
    abstract fun onBackKeyReceived()

    fun Fragment.navigateTo(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun Fragment.goBack() {
        val currentEntryId = findNavController().currentBackStackEntry?.destination?.id
        if (currentEntryId == R.id.usersFragment) {
            activity?.finish()
        } else {
            findNavController().popBackStack()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) // TODO: 12.01.2022 fix
    private fun clearViewBinding() {
        _binding = null
        viewLifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
