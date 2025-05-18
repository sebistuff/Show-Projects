package ac.at.univie.hci.learneasy;

import android.adservices.topics.Topic;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TopicPickerDialog extends DialogFragment {

    public interface ITopicPickerListener {
        public void onTopicConfirm(int topic);
    }

    ITopicPickerListener listener;
    String[] topics;

    public TopicPickerDialog(String[] topics) {
        this.topics = topics;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a Topic")
               .setItems(topics, (dialog, which) -> {
                    listener.onTopicConfirm(which);
               });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ITopicPickerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement ITopicPickerListener");
        }
    }
}
