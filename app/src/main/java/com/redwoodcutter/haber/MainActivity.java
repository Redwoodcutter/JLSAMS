package com.redwoodcutter.haber;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnFinans,btnSpor,btnHaberler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpor = (Button) findViewById(R.id.btnSpor);
        btnFinans = (Button) findViewById(R.id.btnFinans);
        btnHaberler = (Button) findViewById(R.id.btnHaberler);

        btnHaberler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ActivityHaberler.class));
            }
        });
        btnSpor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ActivitySpor.class));
            }
        });
        btnFinans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ActivityFinans.class));
            }
        });



    }
}
