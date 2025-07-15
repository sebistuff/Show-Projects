package ac.at.univie.hci.learneasy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class QuizFinishDialog extends DialogFragment {

	private final boolean finished;

	public interface IQuizFinishListener {
		void onTryAgain();
		void onConfirm();
	}

	private IQuizFinishListener listener;

	public QuizFinishDialog(boolean finished) {
		this.finished = finished;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (finished) {
			builder.setTitle("Congratulations you finished the quiz successfully!").setNegativeButton("Try again", (dialog, which) -> {
				listener.onTryAgain();
			}).setPositiveButton("Confirm", (dialog, which) -> {
				listener.onConfirm();
			});
		} else {
			builder.setTitle("Ooops you answered too many questions incorrectly. Please try again").setNegativeButton("Try Again", (dialog, which) -> {
						listener.onTryAgain();
			});
		}
		return builder.create();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			listener = (IQuizFinishListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement ITopicPickerListener");
		}
	}
}
