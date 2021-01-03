package tool.Analy.Antipattern;

import java.util.ArrayList;

public class imageLoadingList {
	public static ArrayList<String> list;
	static {

		list = new ArrayList<String>();
		list.add("<android.graphics.BitmapFactory: android.graphics.Bitmap decodeFile(java.lang.String,android.graphics.BitmapFactory$Options)>");
		list.add("<android.graphics.BitmapFactory: android.graphics.Bitmap decodeFileDescriptor(java.lang.String,android.graphics.BitmapFactory$Options)>");
		list.add("<android.graphics.BitmapFactory: android.graphics.Bitmap decodeStream(java.lang.String,android.graphics.BitmapFactory$Options)>");
		list.add("<android.graphics.BitmapFactory: android.graphics.Bitmap decodeByteArray(java.lang.String,android.graphics.BitmapFactory$Options)>");
		list.add("<com.bumptech.glide.RequestManager: com.bumptech.glide.DrawableTypeRequest load(android.net.Uri)>");
		list.add("<com.bumptech.glide.RequestManager: com.bumptech.glide.DrawableTypeRequest load(java.lang.String)>");
		list.add("<com.squareup.picasso.Picasso: com.squareup.picasso.RequestCreator load(java.lang.String)>");
		list.add("<com.squareup.picasso.Picasso: com.squareup.picasso.RequestCreator load(android.net.Uri)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,com.nostra13.universalimageloader.core.imageaware.ImageAware)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,com.nostra13.universalimageloader.core.imageaware.ImageAware,com.nostra13.universalimageloader.core.DisplayImageOptions)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,com.nostra13.universalimageloader.core.imageaware.ImageAware,com.nostra13.universalimageloader.core.DisplayImageOptions,com.nostra13.universalimageloader.core.listener.ImageLoadingListener)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,com.nostra13.universalimageloader.core.imageaware.ImageAware,com.nostra13.universalimageloader.core.DisplayImageOptions,com.nostra13.universalimageloader.core.listener.ImageLoadingListener,com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView,com.nostra13.universalimageloader.core.assist.ImageSize)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView,com.nostra13.universalimageloader.core.DisplayImageOptions)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView,com.nostra13.universalimageloader.core.DisplayImageOptions,com.nostra13.universalimageloader.core.listener.ImageLoadingListener)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView,com.nostra13.universalimageloader.core.DisplayImageOptions,com.nostra13.universalimageloader.core.listener.ImageLoadingListener,com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener)>");
		list.add("<com.nostra13.universalimageloader.core.ImageLoader: void displayImage(java.lang.String,android.widget.ImageView)>");
		
		

		
	}	
	
	

}
