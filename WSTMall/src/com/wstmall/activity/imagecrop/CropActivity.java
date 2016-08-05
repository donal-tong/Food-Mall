
package com.wstmall.activity.imagecrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.util.FileUtil;

public class CropActivity extends Activity {
	
    public final static String IMAGE_URI = "image_uri";
    public final static String BITMAP = "bitmap";
    public final static String CROP_IMAGE_URI = "crop_image_uri";

	private FrameLayout layout_bg; // 装载跟浮层
	private Bitmap cutBitmap;

	private ImageView img_last; // 显示底图

	private Boolean isBanner = false;// 判断是否是banner

	private int width;
	private int height;
	private ContentResolver mContentResolver;

	private Uri targetUri;// 目标图片Uri
	private Bitmap mBitmap;// 目标图片
	private int BgWidth;// 背框宽
	private int BgHeight;// 背筐长

	private int screenWidth;
	private int screenHeight;
	private int bgLocationX;
	private int bgLocationY;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_imagecrop);

		Intent intent = getIntent();
		if (intent.getStringExtra("banner") != null) {
			isBanner = true;
		}
		Display display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();

		if (isBanner) {
			BgWidth = 640;
			BgHeight = 180;
		} else {
			BgWidth = 400;
			BgHeight = 400;
		}
		bgLocationX = (screenWidth - BgWidth) / 2;
		bgLocationY = (screenHeight - BgHeight) / 2 - 100;
		OverBg overBg = (OverBg) findViewById(R.id.view_OverBg);
		overBg.set(bgLocationX, bgLocationY, BgWidth, BgHeight, screenWidth, screenHeight);
		layout_bg = (FrameLayout) findViewById(R.id.layout_bg);

		findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		findViewById(R.id.save).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				layout_bg.setDrawingCacheEnabled(true);
				layout_bg.destroyDrawingCache();
				layout_bg.buildDrawingCache();
				Bitmap bitmap = layout_bg.getDrawingCache();

				int startX = bgLocationX + BgWidth;
				int startY = bgLocationY + BgHeight;
				if (startX <= screenWidth && startY <= screenHeight) {
					// 截图
					cutBitmap = Bitmap.createBitmap(bitmap, bgLocationX, bgLocationY, BgWidth, BgHeight);

					File file = FileUtil.getNewFile(CropActivity.this, UUID.randomUUID() + ".jpg");
					saveDrawableToCache(cutBitmap, file);

					Uri cropUri = Uri.fromFile(file);

					Intent intent = new Intent("inline-data");
					intent.putExtra(CROP_IMAGE_URI, cropUri);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(CropActivity.this, "截图超出范围", Toast.LENGTH_LONG).show();
				}

			}
		});

		targetUri = intent.getParcelableExtra(IMAGE_URI);
		if (targetUri == null) {
			mBitmap = intent.getParcelableExtra(BITMAP);
		}

		mContentResolver = getContentResolver();

		img_last = (ImageView) findViewById(R.id.img_last);
		if (mBitmap == null) {
			getBitmapSize();
			getBitmap();
			img_last.setImageBitmap(mBitmap);
		}

		if (mBitmap == null) {
			finish();
			return;
		}

		img_last.setOnTouchListener(new MulitPointTouchListener());
	}

	/***************************
	 * 截图方法
	 * 
	 * @param bitmap
	 *            源bitmap
	 * @param smallBitmap
	 *            相框bitmap
	 * @param x
	 *            起始x坐标
	 * @param y
	 *            起始y坐标
	 * @return
	 */
	public Bitmap getCutBitmap(Bitmap bitmap, Bitmap smallBitmap, int x, int y) {
		int w = smallBitmap.getWidth(); // 得到图片的宽，高
		int h = smallBitmap.getHeight();

		// 下面这句是关键
		return Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
	}

	/**
	 * 将Bitmap放入缓存，
	 */
	private boolean saveDrawableToCache(Bitmap bitmap, File file) {
		try {
			OutputStream outStream = new FileOutputStream(file);
			if (isBanner) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outStream);//banner压缩率是75%
			} else {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outStream);//头像压缩率是25%
			}
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取Bitmap分辨率
	 */
	private void getBitmapSize() {
		InputStream is = null;
		try {

			is = getInputStream(targetUri);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);

			width = options.outWidth;
			height = options.outHeight;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * 获取输入流
	 */
	private InputStream getInputStream(Uri mUri) throws IOException {
		try {
			if (mUri.getScheme().equals("file")) {
				return new java.io.FileInputStream(mUri.getPath());
			} else {
				return mContentResolver.openInputStream(mUri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	/**
	 * 根据Uri返回文件路径
	 */
	private String getFilePath(Uri mUri) {
		try {
			if (mUri == null) {
				FileOutputStream b = null;
				File file = FileUtil.getNewFile(this);
				b = new FileOutputStream(file);
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);// 把数据写入文件，大小80%
				return file.getAbsolutePath();
			} else {
				if (mUri.getScheme().equals("file")) {
					return mUri.getPath();
				} else {
					return getFilePathByUri(mUri);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
		String imgPath;
		Cursor cursor = mContentResolver.query(mUri, null, null, null, null);
		cursor.moveToFirst();
		imgPath = cursor.getString(1); // 图片文件路径
		return imgPath;
	}
	
	// 获取图片和压缩
	private Bitmap getBitmapAndCompress(Uri uri, int size) throws FileNotFoundException, IOException {
		double ratio = (width > size) ? (width / size) : 1.0;
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0) {
			k = 1;
		}
		bitmapOptions.inSampleSize = k;
		bitmapOptions.inDither = true;//开启抖动
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		InputStream input = getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		return bitmap;
	}

	private void getBitmap() {
		InputStream is = null;
		try {

/*			try {
				is = getInputStream(targetUri);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			/*
			 * DisplayMetrics dm = new DisplayMetrics();
			 * getWindowManager().getDefaultDisplay().getMetrics(dm); int
			 * screenWidth=dm.widthPixels; int
			 * screenHigth=dm.heightPixels;//高度要减去上面的通知栏
			 */
/*			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = computeSampleSize(options, screenWidth, screenHeight);
			options.inDither = true;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;*/
			mBitmap = getBitmapAndCompress(targetUri, 1440);

			Matrix matrix = new Matrix();

			float x = 0;
			while ((width - x > screenWidth) && (height - x > screenHeight)) {
				x++;
			}
			if (x != 0) {// 大图缩小
				matrix.postScale(1 - x / width, 1 - x / width); // 宽和高放大缩小的比例
				mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			} else {// 小图放大
				while ((width + x < screenWidth) || (height + x < screenHeight)) {
					x++;
				}
				matrix.postScale(1 + x / width, 1 + x / width); // 宽和高放大缩小的比例
				mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			}

		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	class MulitPointTouchListener implements OnTouchListener {
		private static final String TAG = "Touch";
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();

		static final int NONE = 0;
		static final int DRAG = 1;
		static final int ZOOM = 2;
		int mode = NONE;

		PointF start = new PointF();
		PointF mid = new PointF();
		float oldDist = 1f;

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			ImageView view = (ImageView) v;
			dumpEvent(event);

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:

				matrix.set(view.getImageMatrix());
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;

				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				if (oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;

				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				} else if (mode == ZOOM) {
					float newDist = spacing(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
					}
				}
				break;
			}

			view.setImageMatrix(matrix);
			return true;
		}

		private void dumpEvent(MotionEvent event) {
			String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?",
					"9?" };
			StringBuilder sb = new StringBuilder();
			int action = event.getAction();
			int actionCode = action & MotionEvent.ACTION_MASK;
			sb.append("event ACTION_").append(names[actionCode]);
			if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
				sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
				sb.append(")");
			}
			sb.append("[");
			for (int i = 0; i < event.getPointerCount(); i++) {
				sb.append("#").append(i);
				sb.append("(pid ").append(event.getPointerId(i));
				sb.append(")=").append((int) event.getX(i));
				sb.append(",").append((int) event.getY(i));
				if (i + 1 < event.getPointerCount())
					sb.append(";");
			}
			sb.append("]");
		}

		private float spacing(MotionEvent event) {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}

		private void midPoint(PointF point, MotionEvent event) {
			float x = event.getX(0) + event.getX(1);
			float y = event.getY(0) + event.getY(1);
			point.set(x / 2, y / 2);
		}
	}

/*	// 以下两个是Android提供压缩图片的代码
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
				Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}*/

}
