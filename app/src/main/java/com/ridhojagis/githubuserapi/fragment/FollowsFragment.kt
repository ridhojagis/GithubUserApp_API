package com.ridhojagis.githubuserapi.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ridhojagis.githubuserapi.DetailUserActivity
import com.ridhojagis.githubuserapi.adapter.UserAdapter
import com.ridhojagis.githubuserapi.data.model.UserListItem
import com.ridhojagis.githubuserapi.databinding.FragmentFollowsBinding
import com.ridhojagis.githubuserapi.viewmodel.DetailViewModel

class FollowsFragment : Fragment() {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollows.layoutManager = LinearLayoutManager(activity)
        when(arguments?.getInt(ARG_SECTION_NUMBER, 0)){
            1 -> {
                detailViewModel.getFollowers(arguments?.getString(USERNAME))
                detailViewModel.followers.observe(requireActivity()) { users ->
                    setUserData(users)
                }
            }
            2 -> {
                detailViewModel.getFollowing(arguments?.getString(USERNAME))
                detailViewModel.following.observe(requireActivity()) { users ->
                    setUserData(users)
                }
            }
        }
    }

    private fun setUserData(users: List<UserListItem>?) {
        val listUserAdapter = UserAdapter(users as ArrayList<UserListItem>)
        binding.rvFollows.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserListItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserListItem) {
        val detailUserIntent = Intent(activity, DetailUserActivity::class.java)
        detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user.username)
        startActivity(detailUserIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }
}