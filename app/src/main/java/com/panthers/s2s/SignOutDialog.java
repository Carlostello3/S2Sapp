package com.panthers.s2s;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutDialog extends AppCompatDialogFragment {

    private Toast signOutToast;
    FirebaseAuth mFirebaseAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);


        builder.setView(view)
                .setTitle("Sign Out")
                .setMessage("Do you want to sign out?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent signOutIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(signOutIntent);
                        signOutToast = Toast.makeText(getContext(), "You have signed out", Toast.LENGTH_SHORT);
                        signOutToast.show();


                    }
                });
        return builder.create();
    }

}
