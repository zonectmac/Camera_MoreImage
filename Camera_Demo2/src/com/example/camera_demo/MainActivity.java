package com.example.camera_demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button bt;
	private List<Map<String, Bitmap>> list;
	// private RxPermissions mRxPermissions;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int REQUEST_SMALL = 111;
	private Calendar calendar;
	private long systemTime1;
	private long systemTime2;
	private GridView gridView;
	private View pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mRxPermissions = new RxPermissions(this);
		calendar = Calendar.getInstance();
		bt = (Button) findViewById(R.id.button1);
		pb = findViewById(R.id.pb);
		gridView = ((GridView) findViewById(R.id.ryMain));
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// mRxPermissions.requestEach(Manifest.permission.CAMERA)
				// .subscribe(new Action1<Permission>() {
				// @Override
				// public void call(Permission permission) {
				//
				// if (permission.granted) {
				// // ���������
				// takeOnCamera();
				//
				// } else if (permission.shouldShowRequestPermissionRationale) {
				// // �ܾ�
				// Toast.makeText(MainActivity.this,
				// "���ܾ��˴������Ȩ�ޣ��޷����",
				// Toast.LENGTH_SHORT).show();
				// } else {
				// // gotoSetting();
				// Toast.makeText(MainActivity.this,
				// "���ܾ��˴������Ȩ�ޣ��޷����",
				// Toast.LENGTH_SHORT).show();
				// }
				// }
				// });
				//
				// ���������
				takeOnCamera();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("data", "onActivityResult: " + data);
		// �ر����֮����ʱ�䣻2��
		pb.setVisibility(View.VISIBLE);
		systemTime2 = getSystemTime();

		if (requestCode == REQUEST_SMALL) {
			// ���������չ��ͬ��ť��������ķ�������ͬ�Ĳ���
			getContactList();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getContactList() {
		// mRxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
		// .subscribe(new Action1<Permission>() {
		// @Override
		// public void call(Permission permission) {
		// if (permission.granted) {
		// // ��ȡ��ƬȻ��ѡ����ʵ���Ƭ������list����
		// final String[] projection = {
		// MediaStore.Images.Media._ID,
		// MediaStore.Images.Media.DISPLAY_NAME,
		// MediaStore.Images.Media.DATA };
		// final String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
		// final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// List<MyBitmap> list2 = getContentProvider(
		// uri, projection, orderBy);// ��ʱ���ȡ�ӿ�
		// Log.e("list", "call: " + list2.toString()
		// + ".size" + list2.size());
		// if (list2 != null) {
		// if (list2.size() > 7) {// ���￴Ҫ����༸����Ƭ
		// list2 = list2.subList(
		// list2.size() - 7,
		// list2.size());
		// }
		// final List<MyBitmap> finalList = list2;
		// gridView.post(new Runnable() {
		// @Override
		// public void run() {
		// // TODO �õ�����ԴȻ����չ
		// gridView.setAdapter(new Myadapter(
		// MainActivity.this,
		// finalList));// �õ��˾Ϳ��Ը���
		// pb.setVisibility(View.GONE);
		//
		// }
		// });
		//
		// }
		//
		// }
		// }).start();
		//
		// } else if (permission.shouldShowRequestPermissionRationale) {
		// // �ܾ�
		// Toast.makeText(MainActivity.this, "���ܾ��˶�ȡ��Ƭ��Ȩ��",
		// Toast.LENGTH_SHORT).show();
		//
		// } else {
		// // gotoSetting();
		// Toast.makeText(MainActivity.this, "���ܾ��˶�ȡ��Ƭ��Ȩ��",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// });
		// ��ȡ��ƬȻ��ѡ����ʵ���Ƭ������list����
		final String[] projection = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATA };
		final String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
		final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<MyBitmap> list2 = getContentProvider(uri, projection,
						orderBy);// ��ʱ���ȡ�ӿ�
				Log.e("list",
						"call: " + list2.toString() + ".size" + list2.size());
				if (list2 != null) {
					if (list2.size() > 7) {// ���￴Ҫ����༸����Ƭ
						list2 = list2.subList(list2.size() - 7, list2.size());
					}
					final List<MyBitmap> finalList = list2;
					gridView.post(new Runnable() {
						@Override
						public void run() {
							// TODO �õ�����ԴȻ����չ
							gridView.setAdapter(new Myadapter(
									MainActivity.this, finalList));// �õ��˾Ϳ��Ը���
							pb.setVisibility(View.GONE);

						}
					});

				}

			}
		}).start();
	}

	/**
	 * ��ȡContentProvider
	 * 
	 * @param projection
	 * @param orderBy
	 */
	public List<MyBitmap> getContentProvider(Uri uri, String[] projection,
			String orderBy) {
		// TODO Auto-generated method stub

		List<MyBitmap> lists = new ArrayList<MyBitmap>();
		HashSet<String> set = new HashSet<String>();
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				orderBy);
		if (null == cursor) {
			return null;
		}

		while (cursor.moveToNext()) {
			Log.e("lengthpro", "getContentProvider: " + projection.length);
			for (int i = 0; i < projection.length; i++) {
				String string = cursor.getString(i);
				if (string != null) {
					int length = string.length();
					String ss = null;
					if (length >= 30) {// ����ʵ��·���õ��ġ���һ�㱣��
						ss = string.substring(length - 23, length);
						String substring = ss.substring(0, 4);// �����ж�һ����ϵͳͼƬ�������ϸ���ѡ
						String hen = ss.substring(12, 13);
						if (substring.equals("IMG_") && hen.equals("_")) {
							String laststring = ss.substring(4, 19).replace(
									"_", "");
							try {
								long time = Long.valueOf(laststring)
										.longValue();
								if (time > systemTime1 && time <= systemTime2) {
									set.add(string);
								}
							} catch (Exception e) {
								Log.e("exception",
										"getContentProvider: " + e.toString());
							}
						}
					}
				}
			}
		}

		for (String strings : set) {
			Log.e("setsize", "getContentProvider: " + strings);
			try {
				Bitmap bitmap = convertToBitmap(strings, 300, 300);

				MyBitmap myBitmap = new MyBitmap(strings, bitmap);
				lists.add(myBitmap);
			} catch (Exception e) {
				Log.e("exceptionee", "getSystemTime: " + e.toString());

			}

		}

		return lists;
	}

	public void takeOnCamera() {
		// �����֮ǰ����¼ʱ��1
		systemTime1 = getSystemTime();
		Intent intent = new Intent();
		// �˴�֮�������try catch������Ϊ�������ֻ���ȷ���ĸ�����
		try {
			intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
			startActivityForResult(intent, REQUEST_SMALL);
		} catch (Exception e) {
			try {
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
				startActivityForResult(intent, REQUEST_SMALL);

			} catch (Exception e1) {
				try {
					intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE);
					startActivityForResult(intent, REQUEST_SMALL);
				} catch (Exception ell) {
					Toast.makeText(MainActivity.this, "������ѡ��",
							Toast.LENGTH_SHORT).show();
				}
			}
		}

	}

	public long getSystemTime() {
		// ("yyyy��MM��dd�� HHʱMM��ss��"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long times = System.currentTimeMillis();
		System.out.println(times);
		Date date = new Date(times);
		String time = sdf.format(date).toString();
		Log.e("timeintimet", "timeint: " + time.toString());
		long timeint = 0;
		try {
			;
			timeint = Long.valueOf(time).longValue();

		} catch (Exception e) {
			Log.e("exception", "getSystemTime: " + e.toString());
		}

		return timeint;
	}

	/**
	 * ����·�������β�������ѹ��
	 * 
	 * @param filePath
	 *            ·��
	 * @param destWidth
	 *            ѹ�����Ŀ��
	 * @param destHeight
	 *            ѹ�����ĸ߶�
	 * @return
	 */
	public Bitmap convertToBitmap(String filePath, int destWidth, int destHeight) {
		// ��һ����
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		int sampleSize = 1;
		while ((outWidth / sampleSize > destWidth)
				|| (outHeight / sampleSize > destHeight)) {

			sampleSize *= 2;
		}
		// �ڶ��β���
		options.inJustDecodeBounds = false;
		options.inSampleSize = sampleSize;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return BitmapFactory.decodeFile(filePath, options);
	}
}
