package com.draco.anyhome.recyclers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.draco.anyhome.R
import com.draco.anyhome.models.AppInfo
import com.draco.anyhome.views.LauncherActivity

class SelectRecyclerAdapter(
    private val context: Context,
    var appList: List<AppInfo>
) : RecyclerView.Adapter<SelectRecyclerAdapter.ViewHolder>() {
    private lateinit var sharedPrefs: SharedPreferences

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById(R.id.img) as ImageView
        val name = itemView.findViewById(R.id.name) as TextView

        val translationY = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y).apply {
            spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_MEDIUM)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = appList[position]

        holder.itemView.setOnClickListener {
            with (sharedPrefs.edit()) {
                putString("home_app", info.id)
                apply()
            }

            (context as AppCompatActivity).finish()

            val intent = Intent(context, LauncherActivity::class.java)
            context.startActivity(intent)
        }

        /* Setup app icons and labels */
        Glide.with(holder.img)
            .load(context.packageManager.getApplicationIcon(info.id))
            .circleCrop()
            .into(holder.img)

        holder.name.text = info.label
        holder.img.contentDescription = info.label
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun getItemId(position: Int): Long {
        return appList[position].hashCode().toLong()
    }
}