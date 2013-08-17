package com.octrois.koa.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.state.StateFlyweight;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new KoaThread(holder);
		this.setOnTouchListener(Game.getInstance());
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
		StateFlyweight.ACHIEVEMENT.update();
		StateFlyweight.HELP_PANEL.update();
		StateFlyweight.MAIN_SCREEN.update();
		StateFlyweight.PLAYING.update();
		StateFlyweight.SCORE_PANEL.update();
		StateFlyweight.SKILLS_PANEL.update();
		StateFlyweight.TREASURE_BOX.update();
		StateFlyweight.MENU_PANEL.update();
		StateFlyweight.SETTING_PANEL.update();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!thread.running) {
			thread.setRunning(true);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// FIXME move to MainActivity.onDestroy().
		// boolean retry = true;
		// while (retry) {
		// try {
		// thread.join();
		// retry = false;
		// thread.setRunning(false);
		// } catch (InterruptedException e) {
		// retry = false;
		// e.printStackTrace();
		// }
		// }

	}

	KoaThread thread;

	class KoaThread extends Thread {
		private boolean running = false;

		private SurfaceHolder mSurfaceHolder;

		public KoaThread(SurfaceHolder surfaceHolder) {
			mSurfaceHolder = surfaceHolder;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

		public void setSurfaceSize(int width, int height) {
			synchronized (mSurfaceHolder) {
				Game game = Game.getInstance();
				game.mCanvasWidth = width;
				game.mCanvasHeight = height;
			}
		}

		public void run() {
			Game game = Game.getInstance();
			while (running) {

				if (!game.isVisible()) {
					continue;
				}
				Canvas canvas = null;
				try {
					canvas = mSurfaceHolder.lockCanvas(null);
					if (canvas != null) {
						doDraw(canvas);
					}
					game.worldEvent();
				} finally {
					if (canvas != null) {
						mSurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

		private void doDraw(Canvas canvas) {
			Game.getInstance().render(canvas);
		}
	}

}
