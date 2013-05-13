package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.friend.Hero;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class MainScreen implements Game.State {

	RectF backgroundRect = new RectF(0, 0, 480, 800);
	RectF musicRect = new RectF(300, 20, 350, 70);
	RectF soundRect = new RectF(380, 20, 430, 70);
	RectF settingRect = new RectF(100, 20, 150, 70);
	RectF startRect = new RectF(120, 300, 340, 400);
	RectF continueRect = new RectF(120, 450, 340, 550);
	RectF achieveRect = new RectF(400, 700, 450, 750);
	RectF shopRect = new RectF(100, 700, 380, 750);
	RectF helpRect = new RectF(30, 20, 80, 70);
	// TODO provide a continue button.

	private long lastFire = 0;
	private Direction dir = Direction.EAST;

	@Override
	public void update() {
		Game game = Game.getInstance();
		backgroundRect.right = game.mCanvasWidth;
		backgroundRect.bottom = game.mCanvasHeight;

		startRect.left = game.mCanvasWidth / 2 - 110;
		startRect.right = startRect.left + 220;

		game.hero.x = game.mCanvasWidth - 200;
		game.hero.y = game.mCanvasHeight - 300; // TODO screen size.

		int count = (game.mCanvasWidth - 40) / 80;

		// FIXME cause a bug if in playing mode when return again (tanks are
		// appended).
		// game.enemies.clear();
		// for (int i = 0; i < count; i++) {
		// MediumTank tank = new MediumTank();
		// tank.x = 80 * i + 20;
		// tank.y = 130;
		// game.enemies.add(tank);
		// }
	}

	@Override
	public void render(Canvas canvas) {
		Paint button = new Paint();
		button.setStyle(Style.FILL);
		button.setARGB(255, 10, 220, 10);

		Paint off = new Paint();
		off.setStyle(Style.FILL);
		off.setARGB(255, 240, 230, 240);

		Paint background = new Paint();
		background.setARGB(255, 200, 130, 63);
		background.setStyle(Style.FILL);

		canvas.drawRect(backgroundRect, background);

		Game game = Game.getInstance();
		BitmapFlyweight bf = BitmapFlyweight.getInstance();

		if (game.musicOn) {
			Bitmap musicOn = bf.getBitmap("music_on");
			canvas.drawBitmap(musicOn, musicRect.left, musicRect.top, null);
		} else {
			Bitmap musicOff = bf.getBitmap("music_off");
			canvas.drawBitmap(musicOff, musicRect.left, musicRect.top, null);
		}

		if (game.soundOn) {
			Bitmap soundOn = bf.getBitmap("sound_on");
			canvas.drawBitmap(soundOn, soundRect.left, soundRect.top, null);
		} else {
			Bitmap soundOff = bf.getBitmap("sound_off");
			canvas.drawBitmap(soundOff, soundRect.left, soundRect.top, null);
		}

		// TODO show the name of 'King of Air'

		// Bitmap settings = bf.getBitmap("settings");
		// canvas.drawBitmap(settings, settingRect.left, settingRect.top,
		// null);

		if (game.loaded) {
			Bitmap start = bf.getBitmap("start_button");
			canvas.drawBitmap(start, startRect.left, startRect.top, null);

			// TODO show continue button.
		}

		Bitmap help = bf.getBitmap("help");
		canvas.drawBitmap(help, helpRect.left, helpRect.top, null);
		// XXX for ver 2.0 canvas.drawRect(shopRect, button);

		// Bitmap badge = bf.getBitmap("badge");
		// XXX for ver 2.0 canvas.drawBitmap(badge, achieveRect.left,
		// achieveRect.top, null);

		// XXX show coins count when start.

		// XXX show button for upgrade skills.

		for (Bullet bullet : game.bullets) {
			bullet.render(canvas);
		}

		for (Enemy enemy : game.enemies) {
			enemy.render(canvas);
		}

		game.hero.render(canvas);

	}

	@Override
	public void onTouch(MotionEvent event) {

		Game game = Game.getInstance();
		// Change pic if ACTION_DOWN.
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (MathUtil.inside(event.getX(), event.getY(), startRect)) {
				// XXX for ver 2.0 game.showSkillPanel();
				game.sendEvent(new GameEvent(GameEvent.START_GAME));
			}
			if (MathUtil.inside(event.getX(), event.getY(), soundRect)) {
				game.muteSound();
			}
			if (MathUtil.inside(event.getX(), event.getY(), musicRect)) {
				game.muteMusic();
			}
			if (MathUtil.inside(event.getX(), event.getY(), helpRect)) {
				game.showHelp();
			}
			if (MathUtil.inside(event.getX(), event.getY(), settingRect)) {
				game.showSetting();
			}
		}
	}

	@Override
	public void worldEvent() {
		Game game = Game.getInstance();
		if (dir.equals(Direction.EAST)) {
			if (game.hero.x < game.mCanvasWidth - 40) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.EAST));
			} else {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				dir = Direction.WEST;
			}
		} else if (dir.equals(Direction.WEST)) {
			if (game.hero.x > -20) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.WEST));
			} else {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				dir = Direction.EAST;
			}
		}

		for (Enemy enemy : game.enemies) {
			enemy.move(Direction.NONE);
		}
		long tick = System.currentTimeMillis();
		if (tick - lastFire > 1600) {
			game.hero.fire();
			lastFire = tick;
		}
		for (Bullet bullet : game.bullets) {
			bullet.move(new Direction(0, -3));
		}
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Game game = Game.getInstance();
		Enemy enemy;
		switch (event.eventCode) {
		case GameEvent.ADD_BULLET:
			game.bullets.add((Bullet) event.param[0]);
			break;
		case GameEvent.KEEP_MOVING:
			game.hero.move((Direction) event.param[0]);
			break;
		case GameEvent.STOP_MOVING:
			break;
		case GameEvent.GO_HOME:
			game.clear();
			game.restart();
			break;
		case GameEvent.START_GAME:
			game.clear();
			game.play();
			break;
		case GameEvent.REMOVE_BULLET:
			game.bullets.remove((Bullet) event.param[0]);
			break;
		case GameEvent.ATTACK_ENEMY:
			enemy = (Enemy) event.param[0];
			Bullet bullet = (Bullet) event.param[1];

			game.beforeExplode(bullet);

			enemy.health -= bullet.power;
			if (enemy.health <= 0 && enemy.isLiving()) {
				enemy.explode();
			}
			break;
		case GameEvent.REMOVE_ENEMY:
			enemy = (Enemy) event.param[0];
			game.enemies.remove(enemy);
			break;
		}
	}

	@Override
	public void fadeOut() {

	}

	@Override
	public void fadeIn() {

	}

	public MainScreen() {
		Game game = Game.getInstance();
		game.hero = new Hero();

	}
}
