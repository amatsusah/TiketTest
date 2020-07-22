package com.loise

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.net.URL
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.squareup.picasso.Picasso
import retrofit2.http.Url


class ViewHolderUser(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.txt_name)
    private val image: ImageView = view.findViewById(R.id.image_icon)
    private var context: Context = view.context
    private var user: UserModel? = null

    init {
        view.setOnClickListener {
            user?.htmlUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(user: UserModel?) {
        if (user == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            image.setImageDrawable(context.getDrawable(R.drawable.ic_no_image))
        } else {
            showUserData(user)
        }
    }

    private fun showUserData(user: UserModel) {
       // image.setImageDrawable(context.getDrawable(R.drawable.ic_no_image))
        this.user = user
        name.text = user.login
        Picasso.get().load(user.avatarUrl).placeholder(R.drawable.ic_no_image).into(image)
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolderUser {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item, parent, false)
            return ViewHolderUser(view)
        }
    }
}