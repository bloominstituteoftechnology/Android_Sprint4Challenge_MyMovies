package com.lambdaschool.sprint4challenge_mymovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.lambdaschool.sprint4challenge_mymovies.SQL.FavoriteMovieSQLDAO;

public class MainActivity extends AppCompatActivity {
    private MoviesList moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        final Context context=getApplicationContext();
        moviesList=new MoviesList(  );


        findViewById( R.id.buttonSearch ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread( (new Runnable() {
                    Handler handler=new Handler();
                    @Override
                    public void run() {
                        moviesList.removeAll();
                        moviesList=moviesList.getMoviesByTitle( (( EditText)findViewById( R.id.inputName)).getText().toString());
                        final ImageListAdapter ilaAdapter=new ImageListAdapter( moviesList );
                        handler.post( new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerViewChoosen=findViewById( R.id.recycler_view_choosen);
                                recyclerViewChoosen.setAdapter( ilaAdapter );
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager( context );
                                linearLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
                                recyclerViewChoosen.setLayoutManager( linearLayoutManager );


                            }
                        });

                    }

                }) ).start();
            }
        } );

        findViewById( R.id.buttonViewFavorites ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, FavoritesViewActivity.class);
                intent.putExtra("DATA_I_NEED","string");
                //       fromOtherScreen=true;
                startActivityForResult(intent,1);

            }
        } );
    }


}
