package com.therafaelreis.presentation.model

data class ProjectView(
    val id: String, val name: String, val fullName: String,
    val starCount: String, val dateCreated: String,
    val ownerName: String, val ownerAvatar: String,
    val isBookmarked: Boolean
)