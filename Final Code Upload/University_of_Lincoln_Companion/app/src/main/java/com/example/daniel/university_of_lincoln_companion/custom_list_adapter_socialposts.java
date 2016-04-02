package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Daniel on 18/01/2016.
 */
public class custom_list_adapter_socialposts extends ArrayAdapter {

    //region GLOBAL VARIABLES
    private Activity context;   //GETS CONTEXT OF CALLING ACTIVITY
    private ArrayList<String> posterName;
    private ArrayList<String> posts;
    private ArrayList<String> postDate;
    private ArrayList<String> numberOfComments;
    //endregion

    //ADAPTER CONSTRUCTOR RECEIVING THE CALLING CONTEXT PLUS ARRAY LISTS CONTAINING THE REQUIRED DATA
    public custom_list_adapter_socialposts(Activity context, ArrayList<String> strPosts, ArrayList<String> strPosterName, ArrayList<String> strPostDate, ArrayList<String> strNumComments) {
        super(context, R.layout.custom_listrow_socialposts, strPosts);

        //region SETTING GLOBAL VARIABLES VALUES FROM PASSED DATA
        this.context = context;
        this.posterName = strPosterName;
        this.posts = strPosts;
        this.postDate = strPostDate;
        this.numberOfComments = strNumComments;
        //endregion
    }

    //CREATES AND RETURNS LIST ROW BY ROW
    public View getView(int position, View view, ViewGroup parent) {

        //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW
        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_socialposts, null, true);

        //region CASTS ROW COMPONENTS
        TextView tvPosterName = (TextView) viewRow.findViewById(R.id.tvPosterName);
        TextView tvPost = (TextView) viewRow.findViewById(R.id.tvPost);
        TextView tvPostDate = (TextView) viewRow.findViewById(R.id.tvPostDate);
        TextView tvNumComments = (TextView) viewRow.findViewById(R.id.tvNumComments);
        //endregion

        //region SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN, AT THE POSITION OF THE CURRENT ROW
        tvPosterName.setText(posterName.get(position));
        tvPost.setText(posts.get(position));
        tvPostDate.setText(postDate.get(position));
        tvNumComments.setText("Comments: " + numberOfComments.get(position));
        //endregion

        return viewRow;
    }
}