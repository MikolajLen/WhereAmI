package pl.mikolajlen.whereami.location.list;

import android.location.Address;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mikolajlen.whereami.databinding.AddressItemBinding;

/**
 * Created by mikolaj on 22.07.2017.
 */

public class AddressesListAdapter extends RecyclerView.Adapter<AddressViewHolder> {

    private List<Address> items = new ArrayList();

    @Inject
    public AddressesListAdapter() {
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new AddressViewHolder(AddressItemBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Address> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
