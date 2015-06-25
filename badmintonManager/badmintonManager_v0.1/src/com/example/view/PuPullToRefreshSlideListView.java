package com.example.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

public class PuPullToRefreshSlideListView extends ZrcListView implements
		GestureDetector.OnGestureListener, OnTouchListener{
	private GestureDetector mDetector;
	private String TAG = "DelSlideListView";

	private Handler alignTopHnadle = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					alignTop();
			}
			super.handleMessage(msg);
		}
	};
	private boolean isNotSlide = true;//是否需要滑动

	public PuPullToRefreshSlideListView(Context context) {
		super(context);
		init(context);
	}

	public PuPullToRefreshSlideListView(Context context, AttributeSet att) {
		super(context, att);
		init(context);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	private int standard_touch_target_size = 0;//左右滑动距离
	private float mLastMotionX;
	// 有item被拉出
	public boolean deleteView = false;
	// 当前拉出的view
	private PuScrollLinerLayout mScrollLinerLayout = null;
	// 滑动着
	private boolean scroll = false;
	// 禁止拖动
	private boolean forbidScroll = false;
	// 禁止拖动
	private boolean clicksameone = false;
	// 当前拉出的位置
	private int position;
	// 消息冻结
	private boolean freeze = false;
	private boolean isNeedInterceptTouchEvent;

	private boolean isNeedAlignTop;
	private boolean lastVerticaScrollMtchingState = true;//自动对齐第一项 false为向上 true向下
	private boolean isVerticalScroll;//是否垂直滚动
	private int topHeight;//顶部自动对齐条高度
	private boolean isAlignTop;//顶部自动对齐标记
	private boolean isFullscreen;

	private void init(Context mContext) {
		mDetector = new GestureDetector(mContext, this);
		 mDetector.setIsLongpressEnabled(false);

		setOnTouchListener(this);
		setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(ZrcAbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (isAlignTop || !isFullscreen) return;
//				Log.i(TAG,"===="+ scrollState +"===");
				if (scrollState == SCROLL_STATE_IDLE) {
					Message msg = new Message();
					msg.what = 1;
					alignTopHnadle.sendMessage(msg);
				}
			}

			@Override
			public void onScroll(ZrcAbsListView view, int firstVisibleItem, int visibleItemCount,
								 int totalItemCount) {
				// TODO Auto-generated method stub
				if(totalItemCount > visibleItemCount){
					isFullscreen = true;
				}else{
					isFullscreen = false;
				}
			}
		});
	}

	/**
	 * 设置滑动距离和顶部对齐条的高度
	 * @param topHeight 顶部对齐条 设置为0,则不会对齐
	 * @param touchTargetSize 滑动距离
	 */
	public void  setSize(int topHeight, int touchTargetSize) {
		standard_touch_target_size = touchTargetSize;
		this.topHeight = topHeight;
	}

	/**
	 * 自动对齐顶部
	 */
	public void alignTop() {
		if(!isNeedAlignTop) return;
		int pos = getFirstVisiblePosition();
		if(pos == 0) {
			View view = getChildAt(0);
			if (view != null) {
				isAlignTop = true;
				int itemY = view.getTop();
				int value = topHeight + itemY;
				if(itemY > 0 || itemY < -topHeight) return;
				if (itemY >= -topHeight / 4) {
					lastVerticaScrollMtchingState = true;
					setOffset(itemY, lastVerticaScrollMtchingState);
				} else if (value < topHeight / 4) {
					lastVerticaScrollMtchingState = false;
					setOffset(value, lastVerticaScrollMtchingState);
				}else{
					if(lastVerticaScrollMtchingState){
						setOffset(value, !lastVerticaScrollMtchingState);
						lastVerticaScrollMtchingState = false;
					}else {
						setOffset(itemY, !lastVerticaScrollMtchingState);
						lastVerticaScrollMtchingState = true;
					}
				}
			}
		}
	}

	private void setOffset(int value,boolean lastVerticaScroll){

		if(!isFullscreen){
			if(lastVerticaScroll){
				setFirstTopOffset(0);
			}else{
				setFirstTopOffset(-topHeight);
			}
		}else{
			setFirstTopOffset(0);
			smoothScrollBy(value, 200);
		}
		/*int pos = getFirstVisiblePosition();
		if(pos == 0) {
			View view = getChildAt(0);
			if (view != null) {
				int itemY = view.getTop();
				int value = topHeight + itemY;
				if (itemY >= -topHeight / 4) {
					setFirstTopOffset(0);
					lastVerticaScrollMtchingState = false;
				} else if (value < topHeight / 4) {
					setFirstTopOffset(-topHeight);
					lastVerticaScrollMtchingState = true;
				}else{
					if(lastVerticaScrollMtchingState){
						setFirstTopOffset(0);
						lastVerticaScrollMtchingState = false;
					}else {
						setFirstTopOffset(-topHeight);
						lastVerticaScrollMtchingState = true;
					}
				}
			}
		}*/
	}

	public void resetItems() {
		resetItems(false);
	}

	/**
	 * 
	 * @param noaction 是否需要缓动
	 */
	public void resetItems(boolean noaction) {
		position = -1;
		deleteView = false;
		if (mScrollLinerLayout != null) {
			if (!noaction) {
				mScrollLinerLayout.snapToScreen(0);
			} else {
				mScrollLinerLayout.scrollTo(0, 0);
			}
			mScrollLinerLayout = null;
		}
		scroll = false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.i(TAG, "onDown");
		mLastMotionX = e.getX();
		int p = pointToPosition((int) e.getX(), (int) e.getY()) -
				getFirstVisiblePosition();
		if (deleteView) {
			if (p != position) {
				// ???锛???ㄦ?娑??
				freeze = true;
				resetItems();
				scroll = true;
				return true;
			} else {
				clicksameone = true;
			}
		}
		position = p;
		scroll = false;
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		// Log.i(TAG, "onScroll" + e1.getX() + ":" + distanceX);
		// 第二次
		if (scroll) {
			int deltaX = (int) (mLastMotionX - e2.getX());
			if (deleteView) {
				deltaX += standard_touch_target_size;
			}
			if (deltaX >= 0 && deltaX <= standard_touch_target_size) {
				int dx = deltaX - mScrollLinerLayout.getScrollX();

				mScrollLinerLayout.scrollBy(deltaX - mScrollLinerLayout.getScrollX(), 0);
//				Log.i(TAG, "onScroll" + e1.getX() + ":" + deltaX + ":" + mScrollLinerLayout.getScrollX());
			}
			if(deltaX > standard_touch_target_size){
				mScrollLinerLayout.scrollTo(standard_touch_target_size, 0);
			}
			if(deltaX < 0){
				mScrollLinerLayout.scrollTo(0, 0);
			}
			return false;
		}
		if (!forbidScroll) {
			forbidScroll = true;
			// x 方向滑动，才开始拉动
			if (Math.abs(distanceX) > Math.abs(distanceY)) {
				View v = getChildAt(position);
				boolean ischild = v instanceof PuScrollLinerLayout;
				if (ischild) {
					mScrollLinerLayout = (PuScrollLinerLayout) v;
					scroll = true;
					int deltaX = (int) (mLastMotionX - e2.getX());
					if (deleteView) {
						// 再次点击的时候，要把deltax增加
						deltaX += standard_touch_target_size;
					}
					if (deltaX >= 0 && deltaX <= standard_touch_target_size) {
						mScrollLinerLayout.scrollBy((int) (e1.getX() - e2.getX()), 0);
					}
				}
			}
		}

		// 判断是否垂直滚动
		if(Math.abs(distanceY) > Math.abs(distanceX)){
			isVerticalScroll= true;
		}else{
			isVerticalScroll = false;
		}

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (scroll || deleteView) {
//        	clearFocus();
			getSelector().setState(StateSet.NOTHING);
			if(isNeedInterceptTouchEvent){
				onInterceptTouchEvent(event);
				isNeedInterceptTouchEvent = false;
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		//对齐第一项
		if(event.getAction() == MotionEvent.ACTION_DOWN) isAlignTop = false;
		if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() ==
				MotionEvent.ACTION_CANCEL) {
			if (isVerticalScroll) {
				alignTop();
				isVerticalScroll = false;
//				return false;
			}
		}

		if(!isNotSlide) mDetector.onTouchEvent(event);

		//滑动相关
		if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() ==
				MotionEvent.ACTION_CANCEL) {
			boolean isfreeze = freeze;
			boolean isclicksameone = clicksameone;
			forbidScroll = false;
			clicksameone = false;
			freeze = false;
			if (isfreeze) {
				// 上一个跟当前点击不一致 还原
//	                resetItems();
				return false;
			}
			int deltaX2 = (int) (mLastMotionX - event.getX());
			// 不存在
			// Log.i(TAG, "scroll:" + scroll + "deltaX2:" + deltaX2);
			if (scroll && deltaX2 >= standard_touch_target_size / 10) {
				mScrollLinerLayout.snapToScreen(standard_touch_target_size);
				deleteView = true;
				scroll = false;
				isNeedInterceptTouchEvent = true;
				return false;
			}
			if (scroll && deltaX2 <= -standard_touch_target_size / 10) {
				mScrollLinerLayout.snapToScreen(0);
				isNeedInterceptTouchEvent = true;
				deleteView = false;
				scroll = false;
				return true;
			}
			if (deleteView && scroll && deltaX2 >= -standard_touch_target_size / 2) {
				mScrollLinerLayout.snapToScreen(standard_touch_target_size);
				deleteView = true;
				scroll = false;
				return false;
			}
			if(isclicksameone||scroll){
				resetItems();
				return true;
			}

			resetItems();
		}
		if (freeze) {
			return false;
		}
		// Log.i(TAG, "onTouchEvent");
		return mDetector.onTouchEvent(event);
	}

	public void setIsNotSlide(boolean isNotSlide) {
		this.isNotSlide = isNotSlide;
	}

	public void setNeedAlignTop(boolean isNeedAlignTop) {
		this.isNeedAlignTop = isNeedAlignTop;
	}
}
