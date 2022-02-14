package com.example.testproject.presentation.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.data.repository.entity.User
import com.example.testproject.databinding.FragmentUsersBinding
import com.example.testproject.extension.collectWhenStarted
import com.example.testproject.presentation.ui.base.BaseBindingFragment
import com.example.testproject.presentation.ui.adapter.ItemClickListener
import com.example.testproject.presentation.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseBindingFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModels()

    private val usersAdapter by lazy {
        UserAdapter(object : ItemClickListener {
            override fun userClick(user: User) {
                viewModel.processAction(UsersViewAction.UserClick(user))
            }
        })
    }

    override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        initList()
    }

    override fun initModel() {
        viewModel.viewState.collectWhenStarted(viewLifecycleOwner, ::processViewState)
    }

    private fun initList() {
        with(binding) {
            rvUsers.adapter = usersAdapter
            rvUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun processViewState(state: UsersViewState) {
        when (state) {
            UsersViewState.BackPressed -> onBackKeyReceived()
            is UsersViewState.Error -> showError(state.error)
            is UsersViewState.ShowProgress -> showProgress(state.show)
            is UsersViewState.Users -> setUsers(state.users)
            is UsersViewState.OpenUserScreen -> openUserScreen(state.user)
        }
    }

    private fun openUserScreen(user: User) {
        navigateTo(UsersFragmentDirections.toUser(user))
    }

    private fun showError(error: String) {


    }

    private fun showProgress(show: Boolean) {


    }

    private fun setUsers(users: List<User>) {
        usersAdapter.setUsers(users)

    }

    override fun onBackKeyReceived() = goBack()

}
