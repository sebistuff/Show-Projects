package ac.at.univie.hci.learneasy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class LectionFragmentIntro extends Fragment {
    public LectionFragmentIntro() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_1, container, false);
        TextView ytLink = view.findViewById(R.id.probability_video);
        String text = (String) ytLink.getText();
        SpannableString underlineText = new SpannableString(text);
        underlineText.setSpan(new UnderlineSpan(), 0, underlineText.length(), 0);
        ytLink.setText(underlineText);
        return view;
    }
}