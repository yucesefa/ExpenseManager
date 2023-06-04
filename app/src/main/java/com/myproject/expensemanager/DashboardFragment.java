package com.myproject.expensemanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myproject.expensemanager.Model.Data;

import java.text.DateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {

    //floating button
    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    //floating button textview

    private TextView fab_income_text;
    private TextView fab_expense_text;


    //boolean

    private boolean isOpen=false;

    //Animation

    private Animation FadeOpen,FadeClose;


    //Firebase

    private FirebaseAuth auth;
    private DatabaseReference IncomeDatabase;
    private DatabaseReference ExpenseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        auth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser=auth.getCurrentUser();
        String uid=firebaseUser.getUid();

        IncomeDatabase= FirebaseDatabase.getInstance().getReference().child("ıncome data").child(uid);
        ExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("expense data").child(uid);


        //connect fab

        fab_main_btn=view.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn=view.findViewById(R.id.income_ft_btn);
        fab_expense_btn=view.findViewById(R.id.expense_ft_btn);

        //connect floating text

        fab_income_text=view.findViewById(R.id.income_ft_text);
        fab_expense_text=view.findViewById(R.id.expense_ft_text);



        //animation connect

        FadeOpen= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        FadeClose= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);




        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();

                if(isOpen){

                    fab_income_btn.startAnimation(FadeClose);
                    fab_expense_btn.startAnimation(FadeClose);
                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);

                    fab_income_text.startAnimation(FadeClose);
                    fab_expense_text.startAnimation(FadeClose);
                    fab_income_text.setClickable(false);
                    fab_expense_text.setClickable(false);
                    isOpen=false;
                }else{

                    fab_income_btn.startAnimation(FadeOpen);
                    fab_expense_btn.startAnimation(FadeOpen);
                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);

                    fab_income_text.startAnimation(FadeOpen);
                    fab_expense_text.startAnimation(FadeOpen);
                    fab_income_text.setClickable(true);
                    fab_expense_text.setClickable(true);
                    isOpen=true;

                }

            }
        });

        return view;
    }

    //floating button aniamtion


    private void ftAnimation(){

        if(isOpen){

            fab_income_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_text.startAnimation(FadeClose);
            fab_expense_text.startAnimation(FadeClose);
            fab_income_text.setClickable(false);
            fab_expense_text.setClickable(false);
            isOpen=false;
        }else{

            fab_income_btn.startAnimation(FadeOpen);
            fab_expense_btn.startAnimation(FadeOpen);
            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_text.startAnimation(FadeOpen);
            fab_expense_text.startAnimation(FadeOpen);
            fab_income_text.setClickable(true);
            fab_expense_text.setClickable(true);
            isOpen=true;

        }


    }

    private void addData(){

        //fab button income

        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incomeDataInsert();

            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expenseDataInsert();

            }
        });


    }

    public void incomeDataInsert(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.custom_layout_for_insertdata,null);
        builder.setView(view);
        final AlertDialog dialog=builder.create();

        dialog.setCancelable(false);

        EditText edtAmount=view.findViewById(R.id.amount_edt);
        EditText edtType=view.findViewById(R.id.type_edt);
        EditText edtNote=view.findViewById(R.id.note_edt);

        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnCancel=view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type=edtType.getText().toString().trim();
                String amount=edtAmount.getText().toString().trim();
                String note=edtNote.getText().toString().trim();

                if(TextUtils.isEmpty(type)){

                    edtType.setError("Required field:");
                    return;

                }
                if(TextUtils.isEmpty(amount)){

                    edtAmount.setError("Required field:");
                    return;

                }

                int ouramountint=Integer.parseInt(amount);

                if(TextUtils.isEmpty(note)){

                    edtNote.setError("Required field:");
                    return;

                }

                String id=IncomeDatabase.push().getKey();

                String mDate= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(ouramountint,type,note,id,mDate);

                IncomeDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(),"Data Added",Toast.LENGTH_LONG).show();

                ftAnimation();
                dialog.dismiss();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void expenseDataInsert(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view= inflater.inflate(R.layout.custom_layout_for_insertdata,null);
        builder.setView(view);

        final AlertDialog dialog=builder.create();

        dialog.setCancelable(false);

        EditText amount=view.findViewById(R.id.amount_edt);
        EditText type=view.findViewById(R.id.type_edt);
        EditText note=view.findViewById(R.id.note_edt);

        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnCancel=view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmAmount=amount.getText().toString().trim();
                String tmtype=type.getText().toString().trim();
                String tmnote=note.getText().toString().trim();

                if (TextUtils.isEmpty(tmAmount)){

                    amount.setError("Required Filed..");
                    return;
                }

                int inamount=Integer.parseInt(tmAmount);

                if (TextUtils.isEmpty(tmtype)){

                    type.setError("Required Filed..");
                    return;
                }
                if (TextUtils.isEmpty(tmnote)){

                    note.setError("Required Filed..");
                    return;
                }

                String id=ExpenseDatabase.push().getKey();
                String mDate=DateFormat.getDateInstance().format(new Date());

                Data data=new Data(inamount,tmtype,tmnote,id,mDate);
                ExpenseDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(),"Data Added",Toast.LENGTH_LONG).show();

                ftAnimation();
                dialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ftAnimation();
                dialog.dismiss();

            }
        });
        dialog.show();

    }

}