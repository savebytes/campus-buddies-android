package com.savebytes.campusbuddy.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.savebytes.campusbuddy.databinding.FragmentChatsBinding
import com.savebytes.campusbuddy.domain.model.ChatItem
import com.savebytes.campusbuddy.presentation.adapters.ChatsAdapter
import com.savebytes.campusbuddy.presentation.dialogs.SearchChatsDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatsAdapter: ChatsAdapter
    private var allChats = listOf<ChatItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadChats()
    }

    private fun setupRecyclerView() {
        chatsAdapter = ChatsAdapter(
            onChatClick = { chat -> onChatClick(chat) },
            onSearchClick = { showSearchDialog() }
        )

        binding.rvChats.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatsAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadChats() {
        allChats = listOf(
            ChatItem(
                id = "1",
                communityId = "2",
                communityName = "Campus Coders",
                communityAvatar = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074&auto=format&fit=crop",
                lastMessage = "emma_davis: I'm in! What tech stack are you...",
                lastMessageTime = "Mar 1",
                unreadCount = 2
            ),
            ChatItem(
                id = "2",
                communityId = "1",
                communityName = "CS Study Group",
                communityAvatar = "https://plus.unsplash.com/premium_photo-1685086785054-d047cdc0e525?q=80&w=2532&auto=format&fit=crop",
                lastMessage = "Nice notes! Are those from the lecture?",
                lastMessageTime = "Mar 1",
                unreadCount = 0
            ),
            ChatItem(
                id = "3",
                communityId = "3",
                communityName = "Engineering Society",
                communityAvatar = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=2070&auto=format&fit=crop",
                lastMessage = "lisa_brown: Already registered! Super excite...",
                lastMessageTime = "Mar 1",
                unreadCount = 1
            ),
            ChatItem(
                id = "4",
                communityId = "4",
                communityName = "Startup Club",
                communityAvatar = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?q=80&w=2074&auto=format&fit=crop",
                lastMessage = "No messages yet",
                lastMessageTime = "",
                unreadCount = 0
            )
        )

        chatsAdapter.submitList(allChats)
    }

    private fun showSearchDialog() {
        val searchDialog = SearchChatsDialogFragment { query ->
            filterChats(query)
        }
        searchDialog.show(parentFragmentManager, "SearchChatsDialog")
    }

    private fun filterChats(query: String) {
        val filtered = if (query.isBlank()) {
            allChats
        } else {
            allChats.filter {
                it.communityName.contains(query, ignoreCase = true) ||
                        it.lastMessage.contains(query, ignoreCase = true)
            }
        }
        chatsAdapter.submitList(filtered)
    }

    private fun onChatClick(chat: ChatItem) {
        // Navigate to chat details
        // findNavController().navigate(
        //     ChatsFragmentDirections.actionToChatDetails(chat.communityId)
        // )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}