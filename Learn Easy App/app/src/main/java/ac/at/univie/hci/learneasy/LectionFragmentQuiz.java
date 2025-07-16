package ac.at.univie.hci.learneasy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LectionFragmentQuiz extends Fragment {
    private RecyclerView questionRecyclerView;
    private int currentQuestion = 0;
    private int correctQuestions = 0;

    private List<String> questions;
    private List<Boolean> answers;

    public LectionFragmentQuiz() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_3, container, false);
        questions = Arrays.asList(getResources().getStringArray(R.array.probability_lection_1_questions));
        answers = Arrays
                .stream(getResources().getIntArray(R.array.probability_lection_1_questions_answers))
                .mapToObj(a -> a == 1)
                .collect(Collectors.toList());

        questionRecyclerView = view.findViewById(R.id.quiz_recycler_view);
        QuizAdapter adapter = new QuizAdapter(questions.get(currentQuestion));
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        questionRecyclerView.setAdapter(adapter);
        return view;
    }

    public void questionAnswered(boolean answer) {
        if (answer == answers.get(currentQuestion)) {
            correctQuestions++;
            Snackbar.make(getView(), "Good job. You answered correctly!", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getView(), "Oh no. That was not correct!", Snackbar.LENGTH_SHORT).show();
        }
        if (++currentQuestion >= questions.size()) {
            QuizFinishDialog finishDialog = new QuizFinishDialog(correctQuestions >= 4);
            finishDialog.show(getActivity().getSupportFragmentManager(), "FINISH_QUIZ_DIALOG");
        } else {
            QuizAdapter adapter = new QuizAdapter(questions.get(currentQuestion));
            questionRecyclerView.swapAdapter(adapter, true);
        }
    }

    public void onTryAgain() {
        correctQuestions = 0;
        currentQuestion = 0;
        QuizAdapter adapter = new QuizAdapter(questions.get(currentQuestion));
        questionRecyclerView.swapAdapter(adapter, true);
    }

    public void onConfirm() {
        Button finishButton = getView().findViewById(R.id.finish_button);
        finishButton.setVisibility(View.VISIBLE);
    }
}