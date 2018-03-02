package cs340.TicketClient.CardFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs340.TicketClient.R;


public class HandFragment extends Fragment {

    RecyclerView destinationCards;
    RecyclerView trainCards;
    HandFragmentPresenter presenter;


    public HandFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hand, container, false);
        destinationCards = v.findViewById(R.id.destinationCards);
        trainCards = v.findViewById(R.id.trainCards);
        presenter = new HandFragmentPresenter(this);

        return v;
    }

}
