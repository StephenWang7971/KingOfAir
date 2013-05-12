package com.octrois.koa.model;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.JetPlayer;
import android.media.JetPlayer.OnJetEventListener;
import android.view.MotionEvent;
import android.view.View;

import com.octrois.koa.R;
import com.octrois.koa.model.box.Box;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.magic.AtomBomb;
import com.octrois.koa.model.magic.MagicWeapon;
import com.octrois.koa.model.magic.SuperMisile;
import com.octrois.koa.model.role.boss.Boss;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.friend.Friend;
import com.octrois.koa.model.role.friend.Hero;
import com.octrois.koa.model.state.Playing;
import com.octrois.koa.model.state.StateFlyweight;
import com.octrois.koa.scene.Scene;
import com.octrois.koa.util.BitmapFlyweight;

//TODO allow sensor.
public class Game implements View.OnTouchListener, OnJetEventListener {

	private static final int TICK_INTERVAL = 30;
	public Hero hero;
	public List<Friend> friends = new ArrayList<Friend>();
	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Bullet> bullets = new ArrayList<Bullet>();
	public List<MagicWeapon> magicWeapons = new ArrayList<MagicWeapon>();

	public Score score = new Score();
	public Scene scene;

	public boolean preLoaded = false;
	public boolean loaded = false;

	private JetPlayer mJet;
	private boolean muteMask[][] = new boolean[9][32];

	State state;
	public int mCanvasWidth;
	public int mCanvasHeight;
	private List<GameEvent> mEventQueue = new ArrayList<GameEvent>();
	private List<GameEvent> mPreEventQueue = new ArrayList<GameEvent>();

	public List<Box> boxes = new ArrayList<Box>();
	public List<Coin> coins = new ArrayList<Coin>();

	public boolean soundOn = true;
	public boolean musicOn = false;
	public Boss boss;
	public boolean paused;
	private static Game instance = null;

	private Game() {

	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	private void initializeJetPlayer(Resources res) {
		mJet = JetPlayer.getJetPlayer();
		mJet.clearQueue();
		mJet.loadJetFile(res.openRawResourceFd(R.raw.level1));
		mJet.setEventListener(this);
		byte sSegmentID = 0;

		mJet.queueJetSegment(0, 0, 0, 0, 0, sSegmentID);

		mJet.queueJetSegment(1, 0, 4, 0, 0, sSegmentID);

		mJet.queueJetSegment(1, 0, 4, 1, 0, sSegmentID);

		mJet.setMuteArray(muteMask[0], true);
	}

	public void start(final Resources res) {
		preLoaded = false;
		final BitmapFlyweight bf = BitmapFlyweight.getInstance();
		bf.preLoad(res);
		initializeJetPlayer(res);
		preLoaded = true;
		state = StateFlyweight.MAIN_SCREEN;
		new Thread() {
			public void run() {
				if (musicOn) {
					playMusic();
				}
				bf.load(res);
				// TODO Load game scenes from xml.
				loaded = true;
			}
		}.start();
	}

	public void restart() {
		state = StateFlyweight.MAIN_SCREEN;
	}

	public void render(Canvas canvas) {
		if (preLoaded) {
			state.render(canvas);
		}
	}

	public interface State {
		public void render(Canvas canvas);

		public void onTouch(MotionEvent event);

		public void worldEvent();

		public void onGameEvent(GameEvent event);

		public void fadeOut();

		public void fadeIn();

		public void update();
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (loaded) {
			state.onTouch(event);
		}
		return true;
	}

	public void play() {
		enemies.clear();
		friends.clear();
		bullets.clear();
		boxes.clear();
		coins.clear();
		boss = null;
		state.fadeOut();
		state = StateFlyweight.PLAYING;
		((Playing) state).clear();
		state.fadeIn();
		scene = new Scene();
		// scene.top = scene.height - mCanvasHeight;
		scene.top = 1800;
		hero = new Hero();
	}

	public void worldEvent() {

		long tick = System.currentTimeMillis();

		tick = System.currentTimeMillis();

		processPreGameEvent();
		state.worldEvent();
		processGameEvent();

		long now = System.currentTimeMillis();
		long diff = now - tick;
		if (diff < TICK_INTERVAL) {
			try {
				Thread.sleep(TICK_INTERVAL - diff);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void processPreGameEvent() {
		synchronized (mEventQueue) {
			for (GameEvent event : mPreEventQueue) {
				mEventQueue.add(event);
			}
			mPreEventQueue.clear();
		}
	}

	private void processGameEvent() {
		synchronized (mEventQueue) {
			for (GameEvent event : mEventQueue) {
				state.onGameEvent(event);
			}
			mEventQueue.clear();
		}
	}

	public void sendEvent(GameEvent event) {
		synchronized (mEventQueue) {
			mEventQueue.add(event);
		}
	}

	public RectF getBackgroundRect() {
		return new RectF(0, 0, mCanvasWidth, mCanvasHeight);
	}

	public void beforeOver() {
		sendEventDelayed(new GameEvent(GameEvent.GAME_OVER));
	}

	public void over() {
		// FIXME Hero's exploding..animation.
		// FIXME let background move for seconds.
		hero.explode();
		state = StateFlyweight.SCORE_PANEL;
	}

	public void sendEventDelayed(GameEvent gameEvent) {
		synchronized (mEventQueue) {
			mPreEventQueue.add(gameEvent);
		}
	}

	public void muteSound() {
		soundOn = !soundOn;
	}

	public void muteMusic() {
		musicOn = !musicOn;
		if (mJet != null) {
			if (musicOn) {
				playMusic();
			} else {
				pauseMusic();
			}
		}
	}

	public void showHelp() {
		state = StateFlyweight.HELP_PANEL;
	}

	public void pause() {
		if (state == StateFlyweight.PLAYING) {
			state = StateFlyweight.MENU_PANEL;
		}
		paused = true;
		// TODO if it is in Main Screen, it also need to pause.
	}

	public void resume() {

		if (state == StateFlyweight.MENU_PANEL) {
			state = StateFlyweight.PLAYING;
			hero.lastFire = System.currentTimeMillis();
			for (Enemy enemy : enemies) {
				enemy.lastFire = System.currentTimeMillis();
			}
			for (Friend friend : friends) {
				friend.lastFire = System.currentTimeMillis();
			}
		}
		paused = false;
	}

	public void showSetting() {
		state = StateFlyweight.SETTING_PANEL;
	}

	public void beforeExplode(Bullet bullet) {
		sendEventDelayed(new GameEvent(GameEvent.EXPLODE_BULLET, bullet));

	}

	public void clearBox(Box box) {
		sendEventDelayed(new GameEvent(GameEvent.REMOVE_BOX, box));
	}

	public boolean isPlaying() {
		return state == StateFlyweight.PLAYING;
	}

	public boolean isStagePass() {
		if (boss != null) {
			return boss.isExploded();
		}
		return false;
	}

	public void showScorePanel() {
		state = StateFlyweight.SCORE_PANEL;
	}

	public void addCoin(Coin coin) {
		sendEventDelayed(new GameEvent(GameEvent.ADD_COIN, coin));
	}

	@Override
	public void onJetEvent(JetPlayer player, short segment, byte track,
			byte channel, byte controller, byte value) {
		// XXX it seems to be repeatedly play the music.

	}

	@Override
	public void onJetNumQueuedSegmentUpdate(JetPlayer player, int nbSegments) {

	}

	@Override
	public void onJetPauseUpdate(JetPlayer player, int paused) {

	}

	@Override
	public void onJetUserIdUpdate(JetPlayer player, int userId, int repeatCount) {

	}

	public void showSkillPanel() {
		state = StateFlyweight.SKILLS_PANEL;
	}

	public void pauseMusic() {
		if (mJet != null && musicOn) {
			mJet.pause();
		}
	}

	public void playMusic() {
		if (mJet != null && musicOn) {
			mJet.play();
		}
	}

	public void launchSuperMisile() {
		magicWeapons.add(new SuperMisile(10, getBottom()));
		magicWeapons.add(new SuperMisile(90, getBottom()));
		magicWeapons.add(new SuperMisile(170, getBottom()));
		magicWeapons.add(new SuperMisile(250, getBottom()));
		magicWeapons.add(new SuperMisile(330, getBottom()));
		magicWeapons.add(new SuperMisile(410, getBottom()));
	}

	private int getBottom() {
		return mCanvasHeight;
	}

	public void removeSuperMisile(SuperMisile misile) {
		magicWeapons.remove(misile);
	}

	public void launchAtom() {
		magicWeapons.add(new AtomBomb(240, 300));
	}

	public void removeAtom(AtomBomb atom) {
		magicWeapons.remove(atom);
	}

	public void clear() {
		hero = new Hero();
		enemies.clear();
		bullets.clear();
		magicWeapons.clear();
		StateFlyweight.PLAYING.clear();
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void save() {
		// TODO Auto-generated method stub

	}

}
