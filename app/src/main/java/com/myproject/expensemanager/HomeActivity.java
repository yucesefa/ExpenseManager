package com.myproject.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    //Fragment
    private  DashboardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private  ExpenseFragment expenseFragment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Toolbar toolbar=findViewById(R.id.myToolbar);
        toolbar.setTitle("Expense Manager");
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();

        bottomNavigationView=findViewById(R.id.bottomNavigationBar);
        frameLayout=findViewById(R.id.main_frame);

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView=findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        dashBoardFragment= new DashboardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();

        setFragment(dashBoardFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.dashboard:
                    setFragment(dashBoardFragment);
                    bottomNavigationView.setItemBackgroundResource(R.color.color_dashboard);
                    return true;
                case R.id.income:
                    setFragment(incomeFragment);
                    bottomNavigationView.setItemBackgroundResource(R.color.Income_color);
                    return true;
                case R.id.expense:
                    setFragment(expenseFragment);
                    bottomNavigationView.setItemBackgroundResource(R.color.expense_color);
                    return true;

                default:
                    return false;
            }
        });




    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
            DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

            if(drawerLayout.isDrawerOpen(GravityCompat.END)){

                drawerLayout.closeDrawer(GravityCompat.END);
            }else{
                super.onBackPressed();
            }
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    public void displaySelectedListener(int itemId){

        Fragment fragment=null;

        switch (itemId){
            case R.id.dashboard:
                fragment=new DashboardFragment();
                break;
            case R.id.income:
                fragment=new IncomeFragment();
                break;
            case R.id.expense:
                fragment=new ExpenseFragment();
                break;


        }

    }

}