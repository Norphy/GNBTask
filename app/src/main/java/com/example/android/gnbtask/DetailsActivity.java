package com.example.android.gnbtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created on 10/14/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        ImageView detailsImageView = (ImageView) findViewById(R.id.iv_details_image);
        TextView descriptionTitleTextView = (TextView) findViewById(R.id.tv_details_description_title);
        TextView priceTextView = (TextView) findViewById(R.id.tv_details_price);
        TextView descriptionBodyTextView = (TextView) findViewById(R.id.tv_details_description_text);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String price = intent.getStringExtra("price");

        descriptionTitleTextView.setText(R.string.description_title);
        priceTextView.setText(price + "$");
        descriptionBodyTextView.setText(description);
        Picasso.with(this).load(imageUrl).fit().into(detailsImageView);
    }
}
