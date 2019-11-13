package com.example.nyt.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nyt.ArticleAdapter;
import com.example.nyt.AsyncTaskDelegate;
import com.example.nyt.BookAdapter;
import com.example.nyt.FakeAPI;
import com.example.nyt.FakeDatabase;
import com.example.nyt.InsertBooksAsyncTask;
import com.example.nyt.R;
import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Article;
import com.example.nyt.model.BestsellerList;
import com.example.nyt.model.BestsellerListResponse;
import com.example.nyt.model.Book;
import com.example.nyt.model.TopStoriesResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookRecyclerFragment extends Fragment implements AsyncTaskDelegate {
    private RecyclerView recyclerView;
    private RequestQueue mRequestQueue;
    private InsertBooksAsyncTask insertBooksAsyncTask;
    private AppDatabase appDatabase;
    //Database Books
    ArrayList<Book>dbBooks;





    public BookRecyclerFragment() {
        // Required empty public constructor iuhkvlfkjhgfd
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_book_recycler, container, false);

        final BookRecyclerFragment thisFragment = this;


        // We have reworked FakeDatabase to act as a place to store these Books, such that we
        // can access them via their isbn. This will allow our intents to the DetailView to keep
        // functioning.


        //link to the json file
        String url = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=wi0G6uADDMLZ12OLn8NKxqtBBWUIcA0P";

        //creates a RequestQueue, which is basically a list of requests
        mRequestQueue = Volley.newRequestQueue(view.getContext());

        //operates if there's a response from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            //"response" is basically the string that contains the json data
            public void onResponse(String response) {
                System.out.println("Thing works");



                //create a new gson object
                Gson gson = new Gson();

                //converts the string "response" which has the json data of the best sellers list into a BestsellerListResponse object,
                //note "BestsellerListResponse" is models what is in the json file, just in java object form, use firefox to view the link in the "url" string to see what i mean.
                BestsellerListResponse bestsellerListResponse = gson.fromJson(response, BestsellerListResponse.class);

                BestsellerList listFromResponse = bestsellerListResponse.getResults();
                List<Book> booksFromJson = listFromResponse.getBooks();

                Book[] bookArray = new Book[booksFromJson.size()];

                booksFromJson.toArray(bookArray);

                AppDatabase db = AppDatabase.getInstance(view.getContext());

                InsertBooksAsyncTask insertBooksAsyncTask = new InsertBooksAsyncTask();

                insertBooksAsyncTask.setDatabase(db);
                insertBooksAsyncTask.setDelegate(thisFragment);

                insertBooksAsyncTask.execute(bookArray);

            }
        };

        //operates if there is an error or simply no responses
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Failed");
            }
        };


        //asks for a string from the address contained in the "url" string via the GET method, using the the last two arguments to state the types of listeners to use if there is a response and if there is not.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);

        //adds previous request to the mRequestQueue.
        mRequestQueue.add(stringRequest);



        return view;
    }

    @Override
    public void handleTaskResult(ArrayList<Book> books){
        dbBooks = books;
        recyclerView = getView().findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BookAdapter(dbBooks));
    }

    public void handleTaskResult(String result){
        System.out.println("Result of Get: " +result);
    }
}