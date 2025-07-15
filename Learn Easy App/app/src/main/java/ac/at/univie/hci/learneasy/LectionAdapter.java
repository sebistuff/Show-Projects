package ac.at.univie.hci.learneasy;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LectionAdapter extends RecyclerView.Adapter<LectionAdapter.ViewHolder> {

    private final List<String> lections;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button lectionButton;
        private int position;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            this.lectionButton = view.findViewById(R.id.lection_button);
            this.view = view;

            lectionButton.setOnClickListener(v -> {
                MainActivity main = (MainActivity) view.getContext();
                main.startLection(position);
            });
        }

        public void setLection(String lection, int position) {
            this.position = position;
            ColorStateList buttonColor = switch (position%6) {
                case 1 -> this.view.getResources().getColorStateList(R.color.blue, null);
                case 2 -> this.view.getResources().getColorStateList(R.color.orange, null);
                case 3 -> this.view.getResources().getColorStateList(R.color.green, null);
                case 4 -> this.view.getResources().getColorStateList(R.color.light_blue, null);
                case 5 -> this.view.getResources().getColorStateList(R.color.yellow, null);
                default -> this.view.getResources().getColorStateList(R.color.red, null);
            };
            lectionButton.setBackgroundTintList(buttonColor);
            lectionButton.setText(lection);
        }
    }

    public LectionAdapter(List<String> lections) {
        this.lections = lections;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lection_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data
     *                   set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setLection(lections.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lections.size();
    }
}
