package ac.at.univie.hci.learneasy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TopicPickerDialog.ITopicPickerListener,  GenderPickerDialog.IGenderPickerListener {
    private final String[] topics = {"Physics", "Topic 2", "Topic 3", "Topic 4", "Topic 5"};
    private final ArrayList<String> physicsLections = new ArrayList<>(
            List.of("Mechanics", "Electrodynamics", "Waves", "Quantum " + "Mechanics", "Astrophysics", "Nuclear Physics",
                    "Thermodynamics"));
    private final ArrayList<String> templateLections = new ArrayList<>( List.of("Lection 1", "Lection 2", "Lection 3", "Lection 4",
                                                                                "Lection 5", "Lection 6", "Lection 7", "Lection 8"));

    private final HomeFragment homeFragment = HomeFragment.newInstance(physicsLections);
    private final ShopFragment shopFragment = new ShopFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setFragment(homeFragment);BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                setFragment(homeFragment);
                return true;
            } else if (id == R.id.nav_shop) {
                setFragment(shopFragment);
                return true;
            } else if (id == R.id.nav_profile) {
                setFragment(profileFragment);
                return true;
            }
            return false;

        });

        getSharedPreferences("Coins", MODE_PRIVATE).edit().putInt("Coins", 22).apply();

    }

    @Override
    protected void onStop() {
        super.onStop();
        getSharedPreferences(ShopFragment.POSSESSED_ITEMS_SHARED, MODE_PRIVATE).edit().clear().apply();
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void displayTopicPicker(View view) {
        TopicPickerDialog topicPicker = new TopicPickerDialog(topics);
        topicPicker.show(getSupportFragmentManager(), "TOPIC_PICKER");
    }

    public void displayGenderPicker(View view) {
        GenderPickerDialog genderPicker = new GenderPickerDialog();
        genderPicker.show(getSupportFragmentManager(), "GENDER_PICKER");
    }

    @Override
    public void onTopicConfirm(int topic) {
        TextView topic_text = findViewById(R.id.text_topic);
        topic_text.setText(topics[topic]);
        switch (topic) {
            case 0:
                homeFragment.changeLections(physicsLections);
                break;
            default:
                homeFragment.changeLections(templateLections);
                break;
        }
    }

    @Override
    public void onGenderConfirm(int gender) {
        profileFragment.setGender(gender);
    }

    public void setGlasses(int item) {
        profileFragment.setGlasses(item);
    }
}