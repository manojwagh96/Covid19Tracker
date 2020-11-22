package manoj.application.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context context;
    List<CountriesModel> countriesModelList;

    public CountryAdapter(Context context, List<CountriesModel> countriesModelList) {
        this.context = context;
        this.countriesModelList = countriesModelList;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_row, parent, false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        holder.countryName.setText(countriesModelList.get(position).getCountry());
        Glide.with(context).load(countriesModelList.get(position).getFlagUrl()).into(holder.flagImage);

    }

    @Override
    public int getItemCount() {
        return countriesModelList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView flagImage;
        TextView countryName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImage = itemView.findViewById(R.id.country_flag);
            countryName = itemView.findViewById(R.id.country_name);
        }
    }
}
