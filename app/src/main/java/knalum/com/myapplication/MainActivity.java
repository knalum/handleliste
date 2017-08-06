package knalum.com.myapplication;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity implements SkjemaFragment.SkjemaInterface,VareFragment.VareFragmentInterface{

    Map<String,Integer> fragmentValues;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        fragmentList.add(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((EditText)findViewById(R.id.vareTekst)).setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        ((Button)findViewById(R.id.clearAll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<fragmentList.size();i++){
                    getSupportFragmentManager().beginTransaction().remove(fragmentList.get(i)).commit();
                }
            }
        });

    }

    @Override
    public void leggTilVareButton(String vare) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.handlekurvFragment);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        VareFragment vareFragment = new VareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("navn",vare);
        vareFragment.setArguments(bundle);

        ft.add(R.id.handlekurvListe,vareFragment,"vare");
        ft.commit();
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void slettVare(Fragment fragment) {
        System.out.println("Slett vare i main");
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
