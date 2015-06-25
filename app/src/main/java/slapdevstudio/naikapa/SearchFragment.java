package slapdevstudio.naikapa;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 25/06/2015.
 */
public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment,container,false);

        final TextView youre = (TextView) v.findViewById(R.id.youre);
        final TextView location = (TextView) v.findViewById(R.id.location);
        final TextView submit = (TextView) v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "okay , submit your new location", Toast.LENGTH_SHORT).show();
            }
        });

        final ImageButton search = (ImageButton) v.findViewById(R.id.search);
        final ImageView mic = (ImageView) v.findViewById(R.id.mic);
        final EditText destination = (EditText) v.findViewById(R.id.destination);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youre.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                location.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                mic.setVisibility(View.VISIBLE);
                destination.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        return v;
    }
}
