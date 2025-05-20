package ac.at.univie.hci.learneasy;

import android.content.Intent;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TopicPickerDialog.ITopicPickerListener,  GenderPickerDialog.IGenderPickerListener {
    private final String[] topics = {"Physics", "Probability", "Topic 3", "Topic 4", "Topic 5"};
    private final ArrayList<String> physicsLections = new ArrayList<>(
            List.of("Mechanics", "Electrodynamics", "Waves", "Quantum " + "Mechanics", "Astrophysics", "Nuclear Physics",
                    "Thermodynamics"));
    private final ArrayList<String> probabilityLections = new ArrayList<>(List.of("Introduction", "Lection 2", "Lection 3", "Lection 4",
                                                                                  "Lection 5", "Lection 6", "Lectin 7", "Lection 8"));
    private final ArrayList<String> templateLections = new ArrayList<>( List.of("Lection 1", "Lection 2", "Lection 3", "Lection 4",
                                                                                "Lection 5", "Lection 6", "Lection 7", "Lection 8"));
    private final HomeFragment homeFragment = HomeFragment.newInstance(physicsLections);
    private final ShopFragment shopFragment = new ShopFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    private int currentTopic = -1;

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

        // We want the home fragment to be displayed initially
        setFragment(homeFragment);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
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

        // get current coins and set them to 15 when first starting the app
        int coins = getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).getInt(ShopFragment.COINS_SHARED, 15);
        getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).edit().putInt(ShopFragment.COINS_SHARED, coins).apply();

    }

    /**
     * sets the fragment visible
     * @param fragment - Fragment to be displayed
     */
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    /**
     * display the topic picker dialog
	 */
    public void displayTopicPicker(View view) {
        TopicPickerDialog topicPicker = new TopicPickerDialog(topics);
        topicPicker.show(getSupportFragmentManager(), "TOPIC_PICKER");
    }

    /**
     * display the gender picker dialog
	 */
    public void displayGenderPicker(View view) {
        GenderPickerDialog genderPicker = new GenderPickerDialog();
        genderPicker.show(getSupportFragmentManager(), "GENDER_PICKER");
    }

    /**
     * Select a new topic and adjust lections accordingly
     * @param topic selected topic
     */
    @Override
    public void onTopicConfirm(int topic) {
        TextView topic_text = findViewById(R.id.text_topic);
        topic_text.setText(topics[topic]);
        switch (topic) {
            case 0:
                homeFragment.changeLections(physicsLections);
                break;
            case 1:
                homeFragment.changeLections(probabilityLections);
                break;
            default:
                homeFragment.changeLections(templateLections);
                break;
        }
        currentTopic = topic;
    }

    /**
     * change the gender of the avatar
     * @param gender gender to change to (0 = male, 1 = female)
     */
    @Override
    public void onGenderConfirm(int gender) {
        profileFragment.setGender(gender);
    }

    /**
     * set the glasses of the avatar
     * @param item resource id of the glasses
     */
    public void setGlasses(int item) {
        profileFragment.setGlasses(item);
    }

    /**
     * sell an item
     * @param item resource id of the item to sell
     */
    public void sellItem(int item) {
        profileFragment.sellItem(item);
    }

    public void startLection(int position) {
        switch(currentTopic) {
            case 1:
                if (position == 0) {
                    Intent intent = new Intent(this, LectionActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(R.id.main), "This lection is currently not implemented!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                Snackbar.make(findViewById(R.id.main), "This topic is currently not implemented!", Snackbar.LENGTH_SHORT).show();
        }
    }
}