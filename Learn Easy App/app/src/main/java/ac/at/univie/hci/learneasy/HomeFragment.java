package ac.at.univie.hci.learneasy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";

	private List<String> lections;
	private RecyclerView lectionsRecyclerView;

    public HomeFragment() {
	}

	/**
	 * Use this factory method to create a new instance of this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @return A new instance of fragment ProfileFragment.
	 */
	public static HomeFragment newInstance(ArrayList<String> param1) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
            lections = getArguments().getStringArrayList(ARG_PARAM1);
        } else {
			throw new RuntimeException("The home fragment didn´t get any arguments!");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		lectionsRecyclerView = view.findViewById(R.id.lections_recycleview);
		lectionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		LectionAdapter adapter = new LectionAdapter(lections);
		lectionsRecyclerView.setAdapter(adapter);
		return view;
	}

	public void changeLections(List<String> lections) {
		LectionAdapter adapter = new LectionAdapter(lections);
		lectionsRecyclerView.swapAdapter(adapter, true);
	}
}