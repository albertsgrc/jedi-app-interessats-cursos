package jedi.upc.cursosinteressats;

import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.SharedPreferences;
        import android.os.Bundle;

public class DialogFragment extends android.app.DialogFragment {
    public DialogFragment newInstance() {
        return new DialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final SharedPreferences prefs = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        return new AlertDialog.Builder(getActivity()).setTitle("Correct?").setMessage(prefs.getString("txtDialog", null)).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                ((MainActivity) getActivity()).userConfirmed();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                dismiss();
            }
        }).create();
    }
}