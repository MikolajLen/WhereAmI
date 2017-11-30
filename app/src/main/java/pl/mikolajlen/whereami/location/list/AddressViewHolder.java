package pl.mikolajlen.whereami.location.list;

import android.location.Address;
import android.support.v7.widget.RecyclerView;

import pl.mikolajlen.whereami.databinding.AddressItemBinding;

/**
 * Created by mikolaj on 22.07.2017.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder {

    private AddressItemBinding binding;

    public AddressViewHolder(AddressItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Address address) {
        binding.setAddress(address);
        binding.executePendingBindings();
    }
}
