package ac.at.univie.hci.learneasy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private final List<Integer> possessedItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private int viewedItem;

        public ViewHolder(View view) {
            super(view);
            this.image = view.findViewById(R.id.item_image);

            view.setOnClickListener(v -> {
                MainActivity main = (MainActivity) v.getContext();
                main.setGlasses(viewedItem);
            });
        }

        public void setItem(Integer item) {
            viewedItem = item;
            image.setImageResource(item);
        }
    }

    public ProfileAdapter(List<Integer> possessedItems) {
        this.possessedItems = possessedItems;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data
     *                   set.
     * @param id   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int id) {
        viewHolder.setItem(possessedItems.get(id));
    }

    @Override
    public int getItemCount() {
        return possessedItems.size();
    }
}
