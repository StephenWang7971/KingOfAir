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
import com.octrois.koa.model.magic.SuperMissile;
import com.octrois.koa.model.role.boss.Boss;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.friend.Friend;
import com.octrois.koa.model.role.friend.Hero;
import com.octrois.koa.model.role.friend.SmallMissile;
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
	public List<Box> boxes = new ArrayList<Box>();
	public List<Coin> coins = new ArrayList<Coin>();

	public Score score = new Score();
	public Scene scene;

	private boolean preLoaded = false;
	public boolean loaded = false;

	private JetPlayer mJet;
	private boolean muteMask[][] = new boolean[9][32];

	public State state;
	public int mCanvasWidth;
	public int mCanvasHeight;
	private List<GameEvent> mEventQueue = new ArrayList<GameEvent>();
	private List<GameEvent> mPreEventQueue = new ArrayList<GameEvent>();

	public boolean soundOn = true;
	public boolean musicOn = false;
	public Boss boss;
	private boolean visible = true;
	private boolean magicOnRoad;
	private int atomCount;
	private int superMissileCount;
	private static Game instance = null;

	private Game() {
		atomCount = 3;
		superMissileCount = 3;
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
		boss = null;
		state.fadeOut();
		state = StateFlyweight.PLAYING;
		((Playing) state).clear();
		state.fadeIn();
		scene = new Scene();
		scene.top = scene.height - mCanvasHeight;
		hero = new Hero();
		Friend smallMissileLeft = new SmallMissile();
		Friend smallMissileRight = new SmallMissile();
		friends.add(smallMissileLeft);
		friends.add(smallMissileRight);
		adjustFriends();
	}

	private void adjustFriends() {
		friends.get(0).keepAround(hero, -20, 10);
		friends.get(1).keepAround(hero, 69, 10);
	}

	public void worldEvent() {

		long tick = System.currentTimeMillis();

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
		if (mJet != null) {
			if (!musicOn) { // TODO detect old, a bit harder..
				playMusic();
			} else {
				pauseMusic();
			}
		}
		musicOn = !musicOn;
	}

	public void showHelp() {
		state = StateFlyweight.HELP_PANEL;
	}

	public void pause() {
		if (state == StateFlyweight.PLAYING) {
			state = StateFlyweight.MENU_PANEL;
		}
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

	public void showScorePanel(boolean stageCleared) {
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
		if (mJet != null && !musicOn) {
			mJet.pause();
		}
	}

	public void playMusic() {
		if (mJet != null && musicOn) {
			mJet.play();
		}
	}

	public void launchSuperMissile() {
		magicOnRoad = true;
		superMissileCount--;
		magicWeapons.add(new SuperMissile(10, getBottom()));
		magicWeapons.add(new SuperMissile(90, getBottom()));
		magicWeapons.add(new SuperMissile(170, getBottom()));
		magicWeapons.add(new SuperMissile(250, getBottom()));
		magicWeapons.add(new SuperMissile(330, getBottom()));
		magicWeapons.add(new SuperMissile(410, getBottom()));
	}

	private int getBottom() {
		return mCanvasHeight;
	}

	public void removeSuperMissile(SuperMissile missile) {
		magicOnRoad = false;
		magicWeapons.remove(missile);
	}

	public void launchAtom() {
		magicOnRoad = true;
		atomCount--;
		magicWeapons.add(new AtomBomb(240, 300));
	}

	public void removeAtom(AtomBomb atom) {
		magicOnRoad = false;
		magicWeapons.remove(atom);
	}

	public void clear() {
		hero = new Hero();
		friends.clear();
		enemies.clear();
		bullets.clear();
		magicWeapons.clear();
		StateFlyweight.PLAYING.clear();
	}

	public void hide() {
		visible = false;
	}

	public void show() {
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isMagicOnRoad() {
		return magicOnRoad;
	}

	public boolean hasMoreMissile() {
		return superMissileCount > 0;
	}

	public boolean hasMoreAtom() {
		// TODO Auto-generated method stub
		return atomCount > 0;
	}

	public boolean isMainMenu() {
		return state.equals(StateFlyweight.MAIN_SCREEN);
	}
}
