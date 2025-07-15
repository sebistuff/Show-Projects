package ac.at.univie.hci.learneasy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

	private final String question;
	public static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView questionTextView;

		public ViewHolder(View view) {
			super(view);
			questionTextView = view.findViewById(R.id.text_question);
			Button falseButton = view.findViewById(R.id.button_false);
			Button trueButton = view.findViewById(R.id.button_true);

			falseButton.setOnClickListener(v -> {
				LectionActivity act = (LectionActivity) view.getContext();
				act.questionAnswered(false);
			});
			trueButton.setOnClickListener(v -> {
				LectionActivity act = (LectionActivity) view.getContext();
				act.questionAnswered(true);
			});
		}

		public void setQuestion(String question) {
			questionTextView.setText(question);
		}
	}

	public QuizAdapter(String question) {
		this.question = question;
	}

	/**
	 * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
	 * @param viewType The view type of the new View.
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_recycle_item, parent, false);
		return new ViewHolder(view);
	}

	/**
	 * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data
	 *                   set.
	 * @param position   The position of the item within the adapter's data set.
	 */
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		viewHolder.setQuestion(question);
	}

	@Override
	public int getItemCount() {
		return 1;
	}
}
