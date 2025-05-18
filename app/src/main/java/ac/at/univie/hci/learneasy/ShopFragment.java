package ac.at.univie.hci.learneasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class ShopFragment extends Fragment {
    public static final String POSSESSED_ITEMS_SHARED = "PossessedItems";

    private int coins;

    public ShopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        LinearLayout itemContainer = view.findViewById(R.id.item_container);
        for (int i = 0; i < itemContainer.getChildCount(); i++) {
            FrameLayout item = (FrameLayout) itemContainer.getChildAt(i);
            int index = i;
            item.getChildAt(1).setOnClickListener((button_view) -> {
                itemBought(index + 1);
            });
        }
        view.findViewById(R.id.buy_random_button).setOnClickListener(this::randomItemBought);
        coins = getContext().getSharedPreferences("Coins", MODE_PRIVATE).getInt("Coins", 0);
        ((TextView) view.findViewById(R.id.text_coins)).setText("Coins: " + coins);
        return view;
    }

    private void itemBought(int item_id) {
        SharedPreferences.Editor edit = getContext().getSharedPreferences(POSSESSED_ITEMS_SHARED, MODE_PRIVATE).edit();
        switch (item_id) {
            case 1:
                if (coins >= 10) {
                    edit.putBoolean("Glasses 1", true).apply();
                    coins -= 10;
                    Snackbar.make(getView(), "You have successfully bought Glasses 1", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), "You don´t have enough coins to buy this!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (coins >= 7) {
                    edit.putBoolean("Glasses 2", true).apply();
                    coins -= 7;
                    Snackbar.make(getView(), "You have successfully bought Glasses 2", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), "You don´t have enough coins to buy this!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (coins >= 7) {
                    edit.putBoolean("Glasses 3", true).apply();
                    coins -= 7;
                    Snackbar.make(getView(), "You have successfully bought Glasses 3", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), "You don´t have enough coins to buy this!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                Snackbar.make(getView(), "This item is currently not implemented!", Snackbar.LENGTH_SHORT).show();
                break;
        }
        getContext().getSharedPreferences("Coins", MODE_PRIVATE).edit().putInt("Coins", coins).apply();
        ((TextView) getView().findViewById(R.id.text_coins)).setText("Coins: " + coins);
    }

    private void randomItemBought(View view) {
        if (coins >= 3) {
            coins -= 3;
            Random rand = new Random();
            int random = rand.nextInt(3);
            if (random == 0) {
                coins += 10;
            } else {
                coins += 7;
            }
            itemBought(random + 1);
        }
    }
}