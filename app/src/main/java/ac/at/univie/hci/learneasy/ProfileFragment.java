package ac.at.univie.hci.learneasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private ImageView profilePicture;

    private int currentProfilePicture;
    private boolean firstTime = true;
    private boolean isFemale;
    private RecyclerView profileRecyclerView;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (firstTime) {
            MainActivity main = (MainActivity) inflater.getContext();
            main.displayGenderPicker(null);
            firstTime = false;
        }
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profile_img);
        profilePicture.setImageResource(currentProfilePicture);
        profileRecyclerView = view.findViewById(R.id.profile_recycleview);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfileAdapter adapter = new ProfileAdapter(getPossessedItemsList());
        profileRecyclerView.setAdapter(adapter);
        return view;
    }

    public void setGender(int gender) {
        this.isFemale = (gender == 1);
        if (gender == 0) {
            this.currentProfilePicture = R.drawable.man_avatar;
        } else {
            this.currentProfilePicture = R.drawable.woman_avatar;
        }
        profilePicture.setImageResource(currentProfilePicture);
    }

    private List<Integer> getPossessedItemsList() {
        List<Integer> possessedItems = new ArrayList<>();
        SharedPreferences possessed = getContext().getSharedPreferences(ShopFragment.POSSESSED_ITEMS_SHARED, MODE_PRIVATE);
        if (possessed.contains("Glasses 1")) {
            possessedItems.add(R.drawable.glasses_1);
        }
        if (possessed.contains("Glasses 2")) {
            possessedItems.add(R.drawable.glasses_2);
        }
        if (possessed.contains("Glasses 3")) {
            possessedItems.add(R.drawable.glasses_3);
        }
        return possessedItems;
    }

    public void setGlasses(int item) {
        if (isFemale) {
            if (item == R.drawable.glasses_1) {
                this.currentProfilePicture = R.drawable.woman_glasses_1;
            } else if (item == R.drawable.glasses_2) {
                this.currentProfilePicture = R.drawable.woman_glasses_2;
            } else if (item == R.drawable.glasses_3) {
                this.currentProfilePicture = R.drawable.woman_glasses_3;
            }
        } else {
            if (item == R.drawable.glasses_1) {
                this.currentProfilePicture = R.drawable.man_glasses_1;
            } else if (item == R.drawable.glasses_2) {
                this.currentProfilePicture = R.drawable.man_glasses_2;
            } else if (item == R.drawable.glasses_3) {
                this.currentProfilePicture = R.drawable.man_glasses_3;
            }
        }
        profilePicture.setImageResource(currentProfilePicture);
    }

    public void sellItem(int item) {
        SharedPreferences.Editor edit = getContext().getSharedPreferences(ShopFragment.POSSESSED_ITEMS_SHARED, MODE_PRIVATE).edit();
        int coins = getContext().getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).getInt(ShopFragment.COINS_SHARED, Integer.MIN_VALUE);
        if (item == R.drawable.glasses_1) {
            edit.remove("Glasses 1");
            coins += 4;
        } else if (item == R.drawable.glasses_2) {
            edit.remove("Glasses 2");
            coins += 4;
        } else if (item == R.drawable.glasses_3) {
            edit.remove("Glasses 3");
            coins += 4;
        }
        edit.apply();
        getContext().getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).edit().putInt(ShopFragment.COINS_SHARED, coins).apply();
        ProfileAdapter adapter = new ProfileAdapter(getPossessedItemsList());
        profileRecyclerView.setAdapter(adapter);
    }
}