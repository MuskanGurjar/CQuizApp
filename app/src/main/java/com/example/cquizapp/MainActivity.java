package com.example.cquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBhelper dbHelper;
    TextView t;
    RadioButton rd1, rd2, rd3, rd4;
    String Ans = "";
    int count = 0;
    ArrayList<Suitcase> arrQues = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.ques);
        rd1 = findViewById(R.id.rd1);

        rd2 = findViewById(R.id.rd2);

        rd3 = findViewById(R.id.rd3);

        rd4 = findViewById(R.id.rd4);

        dbHelper = DBhelper.getDb(this);

        if (!dbHelper.checkDb()) {
            dbHelper.createDB(this);
        }

        dbHelper.openDb();

        Suitcase suitcase = new Suitcase();
        suitcase.Ques = "New Ques";
        suitcase.OptA = "A";
        suitcase.OptB = "B";
        suitcase.OptC = "C";
        suitcase.OptD = "D";
        suitcase.Ans = "A";

        dbHelper.insertQues(suitcase);

        arrQues = dbHelper.getQues();

        for (int i = 0; i < arrQues.size(); i++) {
            Log.d("Ques", arrQues.get(i).Ques + " " + arrQues.get(i).OptA + " " + arrQues.get(i).OptB + " " + arrQues.get(i).OptC + " " + arrQues.get(i).OptD + " " + arrQues.get(i).Ans);
        }

        loadQues();




//        rd1.setText("option 1");
//        rd2.setText("option 2");
//        rd3.setText("option 3");
//        rd4.setText("option 4");
    }

    public void Check(View a) {
        if (a.getId() == R.id.rd1 && Ans.equals("A") ||
                a.getId() == R.id.rd2 && Ans.equals("B") ||
                a.getId() == R.id.rd3 && Ans.equals("C") ||
                a.getId() == R.id.rd4 && Ans.equals("D")){
            Toast.makeText(this, "BIngo!!..you nailed it.", Toast.LENGTH_SHORT).show();
            if (count<arrQues.size())
            loadQues();
            else
                Toast.makeText(this, "Well Played.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Game Over..Better luck next time.", Toast.LENGTH_SHORT).show();
            count = 0;
            loadQues();
        }

    }

    public void loadQues(){
        t.setText(arrQues.get(count).Ques);
        rd1.setText(arrQues.get(count).OptA);
        rd2.setText(arrQues.get(count).OptB);
        rd3.setText(arrQues.get(count).OptC);
        rd4.setText(arrQues.get(count).OptD);
        Ans = arrQues.get(count).Ans;

        count++;
    }
}
