package ac.at.univie.hci.learneasy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class GenderPickerDialog extends DialogFragment {

    public interface IGenderPickerListener {
        public void onGenderConfirm(int topic);
    }

    private IGenderPickerListener listener;
    private final String[] genders = {"Male", "Female"};

    public GenderPickerDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a gender for your Avatar")
               .setItems(genders, (dialog, which) -> {
                   listener.onGenderConfirm(which);
               });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (IGenderPickerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement ITopicPickerListener");
        }
    }
}
