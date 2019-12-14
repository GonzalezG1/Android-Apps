package com.example.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsFragment extends Fragment implements Serializable {
    private static final String TAG = "NewsFragment";

    //need 2 strings for article and the counts
    private static final String ARTICLE= "ARTICLE";
    private static final String COUNT= "COUNT";

    public static NewsFragment newInstance(Article a, String c) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARTICLE, a);
        args.putString(COUNT, c);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //make everything easy by choosing first letter of whatever object/type we gotta implement

        final Article a = (Article) getArguments().getSerializable(ARTICLE); //a for article
        String c = getArguments().getString(COUNT); //c for count
        View v = inflater.inflate(R.layout.fragment_article, container, false);// v for View
        //layout assignation by ids

        TextView headline = v.findViewById(R.id.Headline);
        TextView date = v.findViewById(R.id.Date);
        TextView author = v.findViewById(R.id.Author);
        TextView description = v.findViewById(R.id.Text);
        TextView count = v.findViewById(R.id.Count);
        final ImageView image = v.findViewById(R.id.Image);

        count.setText(c); //set the String count here
//title, author, publishedAt, etc (5) things
        //also gotta fix the date -> professor said figure it out AKA google it

        if(a.getTitle()!=null && !a.getTitle().equals("null")){
            headline.setText(a.getTitle());
            headline.setMovementMethod(LinkMovementMethod.getInstance());
            headline.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(a.getUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        if(a.getAuthor()!=null && !a.getAuthor().equals("null")){
            author.setText(a.getAuthor());
            author.setMovementMethod(LinkMovementMethod.getInstance());
            author.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(a.getUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        if(a.getPublishedAt()!=null && !a.getPublishedAt().equals("null")){
            SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); //fIXED
            try {
                Date now = readFormat.parse(a.getPublishedAt());
                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy hh:mm aa", Locale.US);
                date.setText(sdf.format(now));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(a.getDescription()!=null && !a.getDescription().equals("null")){
            description.setText(a.getDescription());
            description.setMovementMethod(LinkMovementMethod.getInstance());
            description.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(a.getUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        if(a.getUrlToImage()!= null && !a.getUrlToImage().equals("null")){
            Picasso picasso = new Picasso.Builder(getContext()).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    final String changedUrl = a.getUrlToImage().replace("http:", "https:");
                    picasso.load(changedUrl)
                            .fit()
                            .centerInside()
                            .error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .into(image);
                }
            }).build();
            picasso.load(a.getUrlToImage())
                    .fit()
                    .centerInside()
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(image);

            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(a.getUrl()));
                    startActivity(browserIntent);
                }
            });
        }
        return v;
    }
}