package com.example.pruebascomunicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pruebascomunicacion.Interface.JsonPlaceHolderApi;
import com.example.pruebascomunicacion.model.Posts;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;


public class MainActivity extends AppCompatActivity {

    private TextView mJsonTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJsonTxtView = findViewById(R.id.jsonText);
        getPosts();
    }

    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("https://jsonplaceholder.typicode.com/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        JsonPlaceHolderApi jsonplaceholder = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call = jsonplaceholder.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if (!response.isSuccessful()){
                    mJsonTxtView.setText("Codigo: "+response.code());
                    return;
                }
                List<Posts> PostsList = response.body();
                for(Posts posts: PostsList){
                    String Content="";
                    Content+= "UserId:"+ posts.getUserId() + "\n";
                    Content+= "id:"+ posts.getId() + "\n";
                    Content+= "title:"+ posts.getTitle() + "\n";
                    Content+= "body:"+ posts.getBody() + "\n";
                    mJsonTxtView.append(Content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

                mJsonTxtView.setText(t.getMessage());
            }
        });
    }
}
