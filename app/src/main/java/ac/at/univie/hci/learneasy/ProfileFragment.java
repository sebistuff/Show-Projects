package ac.at.univie.hci.learneasy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Use the {@link ProfileFragment#newInstance} factory method to create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

	private static final String ARG_PARAM1 = "param1";

	private List<String> mParam1;

	public ProfileFragment() {
	}

	/**
	 * Use this factory method to create a new instance of this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @return A new instance of fragment ProfileFragment.
	 */
	public static ProfileFragment newInstance(ArrayList<String> param1) {
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getStringArrayList(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}
}