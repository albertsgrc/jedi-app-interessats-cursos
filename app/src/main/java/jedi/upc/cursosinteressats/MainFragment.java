package jedi.upc.cursosinteressats;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainFragment extends Fragment {
    public SharedPreferences prefs;
    public String lang = "ca";
    public Fragment f;
    public MainFragment() {}

    private String lastName, lastSurname, lastEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInfo();
        changeLanguage(lang, false);
        f = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.buttonCatalan: lang = "ca"; changeLanguage(lang, true); break;
            case R.id.buttonEnglish: lang = "en"; changeLanguage(lang, true); break;
            case R.id.buttonSpanish: lang = "es"; changeLanguage(lang, true); break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        guardaInfo();
    }

    private void guardaInfo() {
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("lang", lang);
        ed.apply();
    }

    public void changeLanguage(String lang, boolean refresh) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        if (refresh) refreshFragment();
    }

    private void refreshFragment() {
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        Fragment frag = f;
        fragTransaction.detach(frag);
        fragTransaction.attach(frag);
        NavigationDrawerFragment frag2 = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
        frag2.setAdapter();
        fragTransaction.commit();
    }

    private void setListeners() {
        Button button = (Button) getActivity().findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askConfirmation();
            }
        });
    }

    private void askConfirmation() {
        EditText name = (EditText) getActivity().findViewById(R.id.nameInput);
        EditText surname = (EditText) getActivity().findViewById(R.id.surnameInput);
        EditText email = (EditText) getActivity().findViewById(R.id.mailInput);

        lastName = name.getText().toString();
        lastSurname = surname.getText().toString();
        lastEmail = email.getText().toString();

        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("txtDialog", "Name: " + lastName + "\nSurname: " + lastSurname + "\nEmail: " + lastEmail);
        ed.apply();

        android.app.DialogFragment dialog = new DialogFragment();
        dialog.show(getFragmentManager(), "fragment_main");
    }

    public void saveUser() {
        BaseDades bd = new BaseDades(getActivity().getApplicationContext());
        bd.addUser(new Usuari(lastName, lastSurname, lastEmail, Calendar.getInstance().getTimeInMillis()));

    }

    private void restoreInfo() {
        prefs = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        lang = prefs.getString("lang", "ca");
    }
}
