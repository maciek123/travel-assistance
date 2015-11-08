package com.hta.travelassistant.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hta.travelassistant.model.FlightInfo;

import java.util.List;

/**
 * TODO: add description...
 */
public class FlightInfoAdapter extends BaseAdapter {

    private List<FlightInfo> listData;
    private LayoutInflater layoutInflater;

    public FlightInfoAdapter(Context aContext, List<FlightInfo> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.flight_info_details, null);
            holder = new ViewHolder();
            holder.from = (TextView) convertView.findViewById(R.id.flightFrom);
            holder.to= (TextView) convertView.findViewById(R.id.flightTo);
            holder.date= (TextView) convertView.findViewById(R.id.flightDate);
            holder.terminal = (TextView) convertView.findViewById(R.id.terminal);
            holder.flightNo= (TextView) convertView.findViewById(R.id.flightNo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.flightNo.setText(listData.get(position).getFlightNo());
        holder.from.setText("From: " + listData.get(position).getFrom());
        holder.to.setText("To:" + listData.get(position).getTo());
        holder.terminal.setText("Terminal: " + listData.get(position).getTerminal());
        holder.date.setText("Departure: " + listData.get(position).getStartTime().toString());
        holder.flightNo.setText("No: " + listData.get(position).getFlightNo());
        return convertView;
    }

    static class ViewHolder {
        TextView from;
        TextView to;
        TextView date;
        TextView terminal;
        TextView flightNo;

    }
}
