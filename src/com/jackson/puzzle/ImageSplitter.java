package com.jackson.puzzle;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImageSplitter extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_splitter);

		Button b1 = (Button) findViewById(R.id.three);
		Button b2 = (Button) findViewById(R.id.four);
		Button b3 = (Button) findViewById(R.id.five);

		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		int chunkNumbers = 0;

		switch (view.getId()) {
		case R.id.three:
			chunkNumbers = 9;
			break;
		case R.id.four:
			chunkNumbers = 16;
			break;
		case R.id.five:
			chunkNumbers = 25;
		}

		// Getting the source image to split
		//Error
		ImageView image = (ImageView) findViewById(R.id.imageView1);

		// invoking method to split the source image
		splitImage(image, chunkNumbers);
	}

	private void splitImage(ImageView image, int chunkNumbers) {

		// For the number of rows and columns of the grid to be displayed
		int rows, cols;

		// For height and width of the small image chunks
		int chunkHeight, chunkWidth;

		// To store all the small image chunks in bitmap format in this list
		ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

		// Getting the scaled bitmap of the source image
		BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
				bitmap.getWidth(), bitmap.getHeight(), true);

		rows = cols = (int) Math.sqrt(chunkNumbers);
		chunkHeight = bitmap.getHeight() / rows;
		chunkWidth = bitmap.getWidth() / cols;

		// xCoord and yCoord are the pixel positions of the image chunks
		int yCoord = 0;
		for (int x = 0; x < rows; x++) {
			int xCoord = 0;
			for (int y = 0; y < cols; y++) {
				chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord,
						yCoord, chunkWidth, chunkHeight));
				xCoord += chunkWidth;
			}
			yCoord += chunkHeight;
		}

		/*
		 * Now the chunkedImages has all the small image chunks in the form of
		 * Bitmap class. You can do what ever you want with this chunkedImages
		 * as per your requirement. I pass it to a new Activity to show all
		 * small chunks in a grid for demo. You can get the source code of this
		 * activity from my Google Drive Account.
		 */

		// Start a new activity to show these chunks into a grid
		Intent intent = new Intent(this,
				ChunkedImageActivity.class);
		intent.putParcelableArrayListExtra("image chunks", chunkedImages);
		startActivity(intent);
	}
}
