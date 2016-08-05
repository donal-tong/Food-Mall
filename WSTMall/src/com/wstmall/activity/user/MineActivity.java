
package com.wstmall.activity.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;

import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.imagecrop.CropActivity;
import com.wstmall.api.UploadPic;
import com.wstmall.api.user.EditUserPhoto;
import com.wstmall.application.Const;
import com.wstmall.fragment.login.LoginFragment;
import com.wstmall.fragment.user.EditUserInfoFragment;
import com.wstmall.fragment.user.MineFragment;
import com.wstmall.util.FileUtil;

public class MineActivity  extends BaseActivity{
	public static boolean autoToMine=false;//用这个判断，是否自动跳转到Mine
	
	private final int CAMERA_PHOTO = 23;
	private final int CHOOSE_ALBUM = 24;
	private final int PHOTO_CROP = 25;
	
	private Uri selectedImageUri = null;
	private UploadPic uploadPic=new UploadPic();
	private EditUserPhoto editUserPhoto=new EditUserPhoto();
	public EditUserInfoFragment editUserInfoFragment;
	
	private String userPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new LoginFragment(LoginFragment.fromMainActivity), false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(autoToMine&&Const.isLogin){
			replaceFragment(new MineFragment(), false);
			autoToMine=false;
		}
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(uploadPic.getA())) {
			try {
				JSONObject jsonobj = new JSONObject(data);
				jsonobj=jsonobj.getJSONObject("Filedata");
				userPhoto=jsonobj.getString("savepath")+jsonobj.getString("savethumbname");
				editUserPhoto.tokenId=Const.cache.getTokenId();
				editUserPhoto.userPhoto=userPhoto;
				request(editUserPhoto);
			} catch (JSONException e) {
			}
		}else if (url.contains(editUserPhoto.getA())) {
			Const.user.userPhoto=userPhoto;
			editUserInfoFragment.refreshHeadImage();
		} 
	}

	public void startCamera() {// 用户点击拍照
		String FILE_NAME = UUID.randomUUID() + ".jpg";
		File photo = FileUtil.getNewFile(this, FILE_NAME);
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		if (photo != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			selectedImageUri = Uri.fromFile(photo);
			startActivityForResult(intent, CAMERA_PHOTO);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	if(whatFragmentTopNow().equals("com.wstmall.fragment.user.MineFragment")||whatFragmentTopNow().equals("com.wstmall.fragment.login.LoginFragment")){
	    		MainActivity.mHost.setCurrentTab(0);
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
	    return super.onKeyDown(keyCode, event);
	}

	public void startSelectPhoto() {// 用户点击 从相册获取
		Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(mIntent, CHOOSE_ALBUM);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 拍照前检查SDCard是否存在
			if (!isSDAvailable()) {
				return;
			}
			switch (requestCode) {
			case CAMERA_PHOTO:// 拍照后
				startPhotoCrop(selectedImageUri);
				break;
			case PHOTO_CROP:// 照片完成裁剪后
				readCropImage(data);
				break;
			case CHOOSE_ALBUM:// 选择相册
				if (data != null) {
					Uri originalUri = data.getData();
					startPhotoCrop(originalUri);
				}
				break;
			}
		}
	}

	/**
	 * 检查SD卡是否可用
	 */
	private boolean isSDAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 读取裁剪好的图片
	 */
	private void readCropImage(Intent data) {

		if (data != null) {
			Uri uri = data.getParcelableExtra(CropActivity.CROP_IMAGE_URI);
			uploadPic.Filedata=new File(getFilePath(uri));
			request(uploadPic);
		}
	}
	
	/**
	 * 根据Uri返回文件路径
	 */
	private String getFilePath(Uri mUri) {
		try {
			if (mUri.getScheme().equals("file")) {
				return mUri.getPath();
			} else {
				return getFilePathByUri(mUri);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
		String imgPath;
		Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
		cursor.moveToFirst();
		imgPath = cursor.getString(1); // 图片文件路径
		return imgPath;
	}

	// 获取的照片，进行裁剪，无论拍照还是相册选择
	private void startPhotoCrop(Uri uri) {
		Intent intent = new Intent(this, CropActivity.class);
		intent.putExtra(CropActivity.IMAGE_URI, uri);
		startActivityForResult(intent, PHOTO_CROP);
	}
	
}
