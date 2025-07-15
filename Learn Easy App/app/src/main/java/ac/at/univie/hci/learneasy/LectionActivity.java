package ac.at.univie.hci.learneasy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class LectionActivity extends AppCompatActivity implements QuizFinishDialog.IQuizFinishListener {
    private final LectionFragmentIntro introFragment = new LectionFragmentIntro();
    private final LectionFragmentExamples examplesFragment = new LectionFragmentExamples();
    private final LectionFragmentQuiz quizFragment = new LectionFragmentQuiz();

    private int currentProgress = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setFragment(introFragment);
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.lection_fragment_container, fragment).commit();
    }

    public void nextPart(View view) {
        if (currentProgress == 1) {
            setFragment(examplesFragment);
        }
        if (currentProgress == 2) {
            setFragment(quizFragment);
        }
        if (currentProgress == 3){
            int coins = getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).getInt(ShopFragment.COINS_SHARED, 0);
            getSharedPreferences(ShopFragment.COINS_SHARED, MODE_PRIVATE).edit().putInt(ShopFragment.COINS_SHARED, coins+5).apply();
            getOnBackPressedDispatcher().onBackPressed();
        }
        currentProgress++;
    }

    public void openProbabilityVideo(View view) {
        String websiteUrl = "https://www.youtube.com/watch?v=92Rjn9mkfaU";
        try {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
            startActivity(webIntent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(this.getCurrentFocus(), "No application can open this website. Please install a webbrowser", Snackbar.LENGTH_SHORT).show();
            System.err.println(e.getMessage());
        }
    }

    public void questionAnswered(boolean answer) {
        quizFragment.questionAnswered(answer);
    }

    @Override
    public void onTryAgain() {
        quizFragment.onTryAgain();
    }

    @Override
    public void onConfirm() {
        quizFragment.onConfirm();
    }
}