package spc.shy;

import java.util.Stack;

import spc.shy.activity.message.ChatActivity;
import spc.shy.socket.udp.UDPMessageListener;
import android.content.Context;

/**
 * @fileName ActivityCollectorUtils.java
 * @package szu.wifichat.android.util
 * @description 活动管理类
 **/
public class ActivitiesManager {

	private static Stack<BaseActivity> queue;
	private static UDPMessageListener mListener;
	private static ChatActivity mChatActivity;

	public static void init(Context context) {
		queue = new Stack<BaseActivity>();
		mListener = UDPMessageListener.getInstance(context);
	}

	public static void addActivity(BaseActivity activity) {
		queue.add(activity);
	}

	public static void finishActivity(BaseActivity activity) {
		if (activity != null) {
			queue.remove(activity);
		}
	}

	public static void finishAllActivities() {

		if(mListener!=null){
			mListener.notifyOffline();
			mListener.stopUDPSocketThread();
			mListener = null;
		}
		if(queue!=null){
			for (BaseActivity activity : queue) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
			queue.clear();
		}
	}

	public static int getActivitiesNum() {
		if (!queue.isEmpty()) {
			return queue.size();
		}
		return 0;
	}

	public static BaseActivity getCurrentActivity() {
		if (!queue.isEmpty()) {
			return queue.lastElement();
		}
		return null;
	}

	public static void initChatActivity(ChatActivity pActivity) {
		mChatActivity = pActivity;
	}

	public static void removeChatActivity() {
		mChatActivity = null;
	}

	public static ChatActivity getChatActivity() {
		return mChatActivity;
	}

}
