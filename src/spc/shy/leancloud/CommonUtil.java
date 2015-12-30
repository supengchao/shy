package spc.shy.leancloud;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextPaint;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtil {


	private static CommonUtil commonUtil = new CommonUtil();

	public static CommonUtil getInstance(){
		return commonUtil;
	};


	public static boolean isNetworkConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
					.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}
	/**
	 * 描述：获取文字的像素�?
	 *
	 * @param str the str
	 * @param paint the paint
	 * @return the string width
	 */
	public static float getStringWidth(String str,TextPaint paint) {
		float strWidth  = paint.measureText(str);
		return strWidth;
	}
	/**
	 * 描述：根据分辨率获得字体大小.
	 *
	 * @param screenWidth the screen width
	 * @param screenHeight the screen height
	 * @param textSize the text size
	 * @return the int
	 */
	public static int resizeTextSize(int screenWidth,int screenHeight,int textSize){
		float ratio =  1;
		try {
			float ratioWidth = (float)screenWidth / 480; 
			float ratioHeight = (float)screenHeight / 800; 
			ratio = Math.min(ratioWidth, ratioHeight); 
		} catch (Exception e) {
		}
		return Math.round(textSize * ratio);
	}


	/**
	 * 描述：获取字符串的长�?
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 字符串的长度（中文字符计2个）
	 */
	public static int strLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度�?，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取�?��字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字�?
				if (temp.matches(chinese)) {
					// 中文字符长度�?
					valueLength += 2;
				} else {
					// 其他字符长度�?
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}


	/**
	 * 描述：判断是否是闰年()
	 * <p>(year能被4整除 并且 不能�?00整除) 或�? year能被400整除,则该年为闰年.
	 *
	 * @param year 年代（如2012�?
	 * @return boolean 是否为闰�?
	 *
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 描述：标准化日期时间类型的数据，不足两位的补0.
	 * 
	 * @param dateTime
	 *            预格式的时间字符串，�?2012-3-2 12:2:20
	 * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
	 */
	public static String dateTimeFormat(String dateTime) {
		StringBuilder sb = new StringBuilder();
		try {
			if (isEmpty(dateTime)) {
				return null;
			}
			String[] dateAndTime = dateTime.split(" ");
			if (dateAndTime.length > 0) {
				for (String str : dateAndTime) {
					if (str.indexOf("-") != -1) {
						String[] date = str.split("-");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append("-");
							}
						}
					} else if (str.indexOf(":") != -1) {
						sb.append(" ");
						String[] date = str.split(":");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append(":");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	/**
	 * 描述：不�?个字符的在前面补�?�?
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 至少2个字符的字符�?
	 */
	public static String strFormat2(String str) {
		try {
			if (str.length() <= 1) {
				str = "0" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}


	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
			// /data/data/
		}
	}

	public static boolean checkNetState(Context context) {
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}


	public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		System.out.println("pixels+++++++" + pixels);

		return output;
	}

	/**
	 * ����ֻ�ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {  
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
		return (int) (spValue * fontScale + 0.5f);  
	} 

	public static void showToask(Context context, String tip) {
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}


	/**
	 * ��֤�ֻ���Ƿ����?
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	/**
	 * ��֤�绰�Ÿ�ʽ�Ƿ���ȷ
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // ��֤����ŵ�?
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // ��֤û����ŵ�?
		if(str.length() >9)
		{	m = p1.matcher(str);
		b = m.matches();  
		}else{
			m = p2.matcher(str);
			b = m.matches(); 
		}  
		return b;
	}
	/**
	 * ��֤Email��ʽ�Ƿ���ȷ
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static long getTimestamp(String time){
		time=time+":00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		try {  
			ts = Timestamp.valueOf(time);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return ts.getTime();
	}

	/**
	 * 时间戳转化为日期格式
	 * @param time
	 * @return
	 */
	public static String timeToChange2(String time){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		return simpleDateFormat.format(new Date(Long.parseLong(time+"000")));
	}


	/**
	 * ʱ���ת��Ϊ����?
	 * @param time
	 * @return
	 */
	public static String timeToChange(String time){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		return simpleDateFormat.format(new Date(Long.parseLong(time+"000")));

	}

	/**
	 * 日期格式转换为时间戳
	 * @param time
	 * @return
	 */
	public static long changeToTimeStamp(String time){

		long rand = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.parse(time+" 00:00:00");

			rand = date1.getTime();
			return rand;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rand;


	}
	public static long changeToTimeStamp1(String time){
		long rand = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.parse(time);

			rand = date1.getTime();
			return rand;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rand;
	}



	public static String textFilter(String inputString){
		return inputString.replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "");
	}
}
