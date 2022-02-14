package com.example.testproject.presentation.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testproject.data.repository.entity.User
import com.example.testproject.databinding.FragmentUserBinding
import com.example.testproject.databinding.FragmentUsersBinding
import com.example.testproject.extension.collectWhenStarted
import com.example.testproject.presentation.ui.base.BaseBindingFragment
import com.example.testproject.presentation.ui.adapter.ItemClickListener
import com.example.testproject.presentation.ui.adapter.UserAdapter
import com.example.testproject.presentation.ui.users.UsersViewAction
import com.example.testproject.presentation.ui.users.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseBindingFragment<FragmentUserBinding>() {

    private val args: UserFragmentArgs by navArgs()

    private val viewModel: UserViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        val user = args.user
        Glide.with(binding.ivAvatar.context)
            .load(user.avatar)
            .circleCrop()
            .into(binding.ivAvatar)

        binding.tvUsername.text = "${user.firstName} ${user.lastName}"
        binding.ivBackBtn.setOnClickListener { viewModel.processAction(UserViewAction.BackPressed) }
    }

    override fun initModel() {
        viewModel.viewState.collectWhenStarted(viewLifecycleOwner, ::processViewState)
    }

    private fun processViewState(state: UserViewState) {
        when (state) {
            UserViewState.BackPressed -> onBackKeyReceived()
        }
    }

    override fun onBackKeyReceived() = goBack()

}
