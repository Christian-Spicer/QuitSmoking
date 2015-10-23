package com.ChristianSpicer.quitsmoking;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
 
public class NoSmoking extends View {
	
	private InputStream gifInputStream;
	private Movie gifMovie;
	private long movieDuration;
	 	 
	public NoSmoking(Context context) 
	{
		super(context);
		 if(!isInEditMode())
		 {
             init(context);
         }
	}
	public NoSmoking(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		if(!isInEditMode())
		{
            init(context);
		}
	}
	public NoSmoking(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}
	private void init(Context context)
	{
		setFocusable(true);
		gifInputStream = context.getResources().openRawResource(R.drawable.no_smoking);
		gifMovie = Movie.decodeStream(gifInputStream);
		movieDuration = gifMovie.duration();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	public long getMovieDuration()
	{
		return movieDuration;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.TRANSPARENT);
		long now = android.os.SystemClock.uptimeMillis();  
    
		if (movieDuration == 0)
		{
			movieDuration = now;
		}
		if(gifMovie != null)
		{
			float scale = 1f;
			if(gifMovie.height() > getHeight() || gifMovie.width() > getWidth())
			{
				scale = ( 1f / Math.max(canvas.getHeight() / gifMovie.height(), canvas.getWidth() / gifMovie.width()) ) + 0.25f;
			}
			else
			{
				scale = Math.max(canvas.getHeight() / gifMovie.height(), canvas.getWidth() / gifMovie.width());
			}
			canvas.scale(scale, scale);
			canvas.translate(((float)getWidth() / scale - (float)gifMovie.width())/2f, ((float)getHeight() / scale - (float)gifMovie.height())/2f);
			int relTime = (int)((now - movieDuration) % gifMovie.duration());
			gifMovie.setTime(relTime);
			gifMovie.draw(canvas, 0, 0);
			invalidate();
		}
	}
} 
