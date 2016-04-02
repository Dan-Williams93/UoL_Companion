package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 23/01/2016.
 */
public class custom_list_adapter_socialcomments extends ArrayAdapter {

    //region GLOBAL VARIABLES
    private Activity context;   //GETS CONTEXT OF CALLING ACTIVITY
    private ArrayList<String> posterName;
    private ArrayList<String> comments;
    private ArrayList<String> postDate;
    //endregion

    //ADAPTER CONSTRUCTOR RECEIVING THE CALLING CONTEXT PLUS ARRAY LISTS CONTAINING THE REQUIRED DATA
    public custom_list_adapter_socialcomments(Activity context, ArrayList<String> strComments, ArrayList<String> strPosterName, ArrayList<String> strPostDate) {
        super(context, R.layout.custom_listrow_socialcomments, strComments);

        //region SETTING GLOBAL VARIABLES VALUES FROM PASSED DATA
        this.context = context;
        this.posterName = strPosterName;
        this.comments = strComments;
        this.postDate = strPostDate;
        //endregion
    }

    //CREATES AND RETURNS LIST ROW BY ROW
    public View getView(int position, View view, ViewGroup parent) {

        //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW
        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_socialcomments, null, true);

        //region CASTS ROW COMPONENTS
        TextView tvPosterName = (TextView) viewRow.findViewById(R.id.tvPosterName);
        TextView tvComment = (TextView) viewRow.findViewById(R.id.tvComment);
        TextView tvCommentDate = (TextView) viewRow.findViewById(R.id.tvCommentDate);
        //endregion

        //region SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN, AT THE POSITION OF THE CURRENT ROW
        tvPosterName.setText(posterName.get(position));
        tvComment.setText(comments.get(position));
        tvCommentDate.setText(postDate.get(position));
        //endregion

        return viewRow;
    }
}

