package knalum.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import knalum.com.myapplication.databinding.FragmentVareBinding;


public class VareFragment extends Fragment {

    VareFragmentInterface vareFragmentInterface;

    public String navn;

    public interface VareFragmentInterface{
        void slettVare(Fragment self);
    }

    public VareFragment() {

    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Fragment self = this;


        View view = inflater.inflate(R.layout.fragment_vare, container, false);

        FragmentVareBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vare, container, false);
        binding.getRoot();
        binding.setNavn(navn);

        this.navn = getArguments().getString("navn");
        ((EditText)view.findViewById(R.id.vareEditText)).setText(this.navn);

        ((EditText)view.findViewById(R.id.vareEditText)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        view.findViewById(R.id.slettButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Long click");
                vareFragmentInterface.slettVare(self);
            }
        });
        return view;
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            vareFragmentInterface = (VareFragment.VareFragmentInterface) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException("Must implement interface VareFragmentInterface");
        }
    }
}
