package com.cholee.e15_room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeviceAdapter(val context: Context, val devices: List<Device>):
    RecyclerView.Adapter<DeviceAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_devices, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(devices[position])
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val uIdTv = itemView?.findViewById<TextView>(R.id.tv_uId)
        val nameTv = itemView?.findViewById<TextView>(R.id.tv_device_name)
        val osTv = itemView?.findViewById<TextView>(R.id.tv_os)
        val versionTv = itemView?.findViewById<TextView>(R.id.tv_version)

        fun bind(device: Device) {
            uIdTv?.text = device.uid.toString()
            nameTv?.text = device.name
            osTv?.text = device.os
            versionTv?.text = device.version.toString()
        }
    }
}