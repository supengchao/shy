package spc.shy.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.LogUtil.log;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class LeanChatManager {
	
	private static LeanChatManager instance = new LeanChatManager();
	private LeanChatManager(){}
	public static LeanChatManager getInstance(){
		return instance;
	}
	
	/**
	 * 
	 * 苏鹏超创建于20152015-5-26上午10:01:37
	 * void 
	 * TODO 初始化云端服务
	 */
	public  void init(Context mContext){

		AVOSCloud.initialize(mContext, "x3u9luhqxsj2evzmqy34yl9as12c5vf17rjpgxd835a2qze3", "39jplml76jg1ttubvlwmpv9k6ldd50vor74hatwucmgik07x");

	}
	/**
	 * 
	 * 苏鹏超创建于20152015-5-26上午10:12:17
	 * void
	 * TODO 当前用户登录到聊天系统
	 */
	public  void loginChatSystem(String name){
		AVIMClient imClient = AVIMClient.getInstance(name);
		imClient.open(new AVIMClientCallback(){

		@Override
		public void done(AVIMClient arg0, AVException  e) {
			
			 if (null != e) {
			      e.printStackTrace();
			    } else {
			    	log.e("成功登录，可以开始进行聊天了");
			    	System.out.print("成功登录，可以开始进行聊天了");
			      // 成功登录，可以开始进行聊天了（假设为 MainActivity）。
//			      Intent intent = new Intent(currentActivity, MainActivity.class);
//			      currentActivity.startActivity(intent);
			    };
		}
		});
	}
}
