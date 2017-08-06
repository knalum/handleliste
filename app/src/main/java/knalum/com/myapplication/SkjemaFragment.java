package knalum.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class SkjemaFragment extends Fragment {

    Set<String> ITEMS;

    SkjemaInterface skjemaFragmentCallback;

    public interface SkjemaInterface{
        public void leggTilVareButton(String vare);
    }

    public SkjemaFragment() {
        // Required empty public constructor
    }


    public static SkjemaFragment newInstance(String param1, String param2) {
        return new SkjemaFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        loadItemsFromSharedPreferences();

        final View view = inflater.inflate(R.layout.fragment_skjema, container, false);
        view.findViewById(R.id.leggTilButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leggTilVare(view);

            }
        });

        ((EditText)view.findViewById(R.id.vareTekst)).setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( actionId == EditorInfo.IME_ACTION_DONE ){
                    leggTilVare(view);
                    return true;
                }
                return false;
            }
        });


        setAndSaveAutocompleteItems(view);
        ((AutoCompleteTextView) view.findViewById(R.id.vareTekst)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                leggTilVare(view);
            }
        });

        return view;
    }

    private void loadItemsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPreferences",Context.MODE_PRIVATE);
        this.ITEMS = sharedPreferences.getStringSet("ITEMS",new HashSet<String>());
    }

    private void setAndSaveAutocompleteItems(View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line,new LinkedList<>(ITEMS));
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.vareTekst);
        textView.setAdapter(adapter);

        FragmentActivity activity = getActivity();
        if( activity != null ){
            SharedPreferences preferences = getActivity().getSharedPreferences("myPreferences",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("ITEMS",ITEMS);
            editor.apply();
        }
    }

    private void leggTilVare(View view){
        EditText editText = (EditText) view.findViewById(R.id.vareTekst);
        skjemaFragmentCallback.leggTilVareButton(editText.getText().toString());
        ITEMS.add(editText.getText().toString());
        editText.setText("");

        setAndSaveAutocompleteItems(view);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            skjemaFragmentCallback = (SkjemaInterface) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException("Must implement interface");
        }
    }
}
