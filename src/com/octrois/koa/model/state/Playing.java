package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.box.Box;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.magic.AtomBomb;
import com.octrois.koa.model.magic.MagicWeapon;
import com.octrois.koa.model.magic.SuperMissile;
import com.octrois.koa.model.role.boss.Carrier;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.friend.Friend;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class Playing implements Game.State {

	private RectF backgroundRect = new RectF(0, 0, 480, 800);
	private RectF menuRect = new RectF(420, 10, 470, 60);
	private RectF leftRect = new RectF(0, 650, 50, 700);
	private RectF rightRect = new RectF(100, 650, 150, 700);
	private RectF upRect = new RectF(50, 600, 100, 650);
	private RectF downRect = new RectF(50, 700, 100, 750);
	private RectF leftUpRect = new RectF(0, 600, 50, 650);
	private RectF rightUpRect = new RectF(100, 600, 150, 650);
	private RectF leftDownRect = new RectF(0, 700, 50, 750);
	private RectF rightDownRect = new RectF(100, 700, 150, 750);

	private RectF missileRect = new RectF(400, 360, 480, 420);
	private RectF atomRect = new RectF(400, 480, 480, 540);
	// TODO player could buy more magic boxes up to 4.
	// TODO player could launch other magic, B52, Thunderstorm

	private boolean leftPressed;
	private boolean rightPressed;
	private boolean upPressed;
	private boolean downPressed;
	private boolean leftUpPressed;
	private boolean rightUpPressed;
	private boolean leftDownPressed;
	private boolean rightDownPressed;

	private Direction movingDir;
	private boolean moving = false;

	private int count = 0;
	private boolean controllable = true;

	@Override
	public void update() {
		Game game = Game.getInstance();

		backgroundRect.right = game.mCanvasWidth;
		backgroundRect.bottom = game.mCanvasHeight;

		menuRect.left = game.mCanvasWidth - 60;
		menuRect.top = 10;
		menuRect.right = menuRect.left + 50;
		menuRect.bottom = menuRect.top + 50;

		missileRect.left = game.mCanvasWidth - 80;
		missileRect.right = missileRect.left + 80;
		missileRect.top = game.mCanvasHeight - 300;
		missileRect.bottom = missileRect.top + 60;

		atomRect.left = missileRect.left;
		atomRect.right = missileRect.right;
		atomRect.top = missileRect.top + 100;
		atomRect.bottom = atomRect.top + 60;

		leftRect.top = game.mCanvasHeight - 2 * 75;
		leftRect.bottom = leftRect.top + 75;
		leftRect.left = 0;
		leftRect.right = 75;

		rightRect.top = leftRect.top;
		rightRect.bottom = leftRect.bottom;
		rightRect.left = leftRect.right + 75;
		rightRect.right = rightRect.left + 75;

		upRect.top = game.mCanvasHeight - 3 * 75;
		upRect.bottom = upRect.top + 75;
		upRect.left = 75;
		upRect.right = upRect.left + 75;

		downRect.top = game.mCanvasHeight - 75;
		downRect.bottom = game.mCanvasHeight;
		downRect.left = 75;
		downRect.right = downRect.left + 75;

		leftUpRect.top = upRect.top;
		leftUpRect.bottom = leftUpRect.top + 75;
		leftUpRect.left = leftRect.left;
		leftUpRect.right = leftUpRect.left + 75;

		rightUpRect.top = upRect.top;
		rightUpRect.bottom = rightUpRect.top + 75;
		rightUpRect.left = rightRect.left;
		rightUpRect.right = rightUpRect.left + 75;

		leftDownRect.top = downRect.top;
		leftDownRect.bottom = leftDownRect.top + 75;
		leftDownRect.left = leftRect.left;
		leftDownRect.right = leftDownRect.left + 75;

		rightDownRect.top = downRect.top;
		rightDownRect.bottom = rightDownRect.top + 75;
		rightDownRect.left = rightRect.left;
		rightDownRect.right = rightDownRect.left + 75;
	}

	@Override
	public void render(Canvas canvas) {

		Game game = Game.getInstance();
		if (game.hero == null) {
			return;
		}

		game.scene.render(canvas);
		for (Friend f : game.friends) {
			f.render(canvas);
		}

		for (Enemy enemy : game.enemies) {
			enemy.render(canvas);
		}

		for (Box box : game.boxes) {
			box.render(canvas);
		}

		for (Coin coin : game.coins) {
			coin.render(canvas);
		}

		if (game.boss != null) {
			game.boss.render(canvas);
		}
		game.hero.render(canvas);

		for (Bullet bullet : game.bullets) {
			bullet.render(canvas);
		}
		for (MagicWeapon effect : game.magicWeapons) {
			effect.render(canvas);
		}

		BitmapFlyweight bf = BitmapFlyweight.getInstance();

		Bitmap menu = bf.getBitmap("menu_button");
		canvas.drawBitmap(menu, menuRect.left, menuRect.top, null);

		game.scene.drawOverlap(canvas);

		drawButtons(canvas);

		drawMagicBoxes(canvas);
		game.scene.drawOverlap(canvas);
		game.scene.drawProgressBar(canvas);
		game.score.render(canvas);
		game.hero.drawHealth(canvas);

	}

	private void drawButtons(Canvas canvas) {
		Paint pressed = new Paint();
		pressed.setARGB(128, 0, 128, 0);

		Paint normal = new Paint();
		normal.setARGB(128, 0, 255, 0);
		if (leftPressed) {
			canvas.drawRect(leftRect, pressed);
		} else {
			canvas.drawRect(leftRect, normal);
		}
		if (rightPressed) {
			canvas.drawRect(rightRect, pressed);
		} else {
			canvas.drawRect(rightRect, normal);
		}
		if (upPressed) {
			canvas.drawRect(upRect, pressed);
		} else {
			canvas.drawRect(upRect, normal);
		}
		if (downPressed) {
			canvas.drawRect(downRect, pressed);
		} else {
			canvas.drawRect(downRect, normal);
		}
		if (leftUpPressed) {
			canvas.drawRect(leftUpRect, pressed);
		} else {
			canvas.drawRect(leftUpRect, normal);
		}
		if (rightUpPressed) {
			canvas.drawRect(rightUpRect, pressed);
		} else {
			canvas.drawRect(rightUpRect, normal);
		}
		if (leftDownPressed) {
			canvas.drawRect(leftDownRect, pressed);
		} else {
			canvas.drawRect(leftDownRect, normal);
		}
		if (rightDownPressed) {
			canvas.drawRect(rightDownRect, pressed);
		} else {
			canvas.drawRect(rightDownRect, normal);
		}
	}

	private void drawMagicBoxes(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap magicBox = bf.getBitmap("magic_box");
		Bitmap superMissile_1 = bf.getBitmap("super_missile_1");
		Bitmap superMissile_2 = bf.getBitmap("super_missile_2");
		Bitmap superMissile_3 = bf.getBitmap("super_missile_3");
		Bitmap superMissile_4 = bf.getBitmap("super_missile_4");
		Bitmap superMissile_5 = bf.getBitmap("super_missile_5");
		Bitmap superMissile_6 = bf.getBitmap("super_missile_6");

		Bitmap atomBomb = bf.getBitmap("atom_bomb");
		canvas.drawBitmap(magicBox, missileRect.left, missileRect.top, null);

		count++;
		if (count == 36) {
			count = 0;
		}

		if (count % 36 < 6) {
			canvas.drawBitmap(superMissile_1, missileRect.left + 5,
					missileRect.top, null);
		} else if (count % 36 < 12) {
			canvas.drawBitmap(superMissile_2, missileRect.left + 5,
					missileRect.top, null);
		} else if (count % 36 < 18) {
			canvas.drawBitmap(superMissile_3, missileRect.left + 5,
					missileRect.top, null);
		} else if (count % 36 < 24) {
			canvas.drawBitmap(superMissile_4, missileRect.left + 5,
					missileRect.top, null);
		} else if (count % 36 < 30) {
			canvas.drawBitmap(superMissile_5, missileRect.left + 5,
					missileRect.top, null);
		} else if (count % 36 < 36) {
			canvas.drawBitmap(superMissile_6, missileRect.left + 5,
					missileRect.top, null);
		}
		canvas.drawBitmap(magicBox, atomRect.left, atomRect.top, null);
		canvas.drawBitmap(atomBomb, atomRect.left + 5, atomRect.top, null);
	}

	@Override
	public void onTouch(MotionEvent event) {

		Game game = Game.getInstance();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			clear();
		} else {
			if (controllable) {
				if (MathUtil.inside(event.getX(), event.getY(), leftRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.WEST));
					clearDirs();
					leftPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(),
						rightRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.EAST));
					clearDirs();
					rightPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(), upRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.NORTH));
					clearDirs();
					upPressed = true;
				} else if (MathUtil
						.inside(event.getX(), event.getY(), downRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.SOUTH));
					clearDirs();
					downPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(),
						leftUpRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.NORTH_WEST));
					clearDirs();
					leftUpPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(),
						rightUpRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.NORTH_EAST));
					clearDirs();
					rightUpPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(),
						leftDownRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.SOUTH_WEST));
					clearDirs();
					leftDownPressed = true;
				} else if (MathUtil.inside(event.getX(), event.getY(),
						rightDownRect)) {
					game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
							Direction.SOUTH_EAST));
					clearDirs();
					rightDownPressed = true;
				} else {
					clear();
				}
			} else {
				clear();
			}
			if (MathUtil.inside(event.getX(), event.getY(), menuRect)) {
				game.sendEvent(new GameEvent(GameEvent.SHOW_MENU));
			} else if (MathUtil.inside(event.getX(), event.getY(), missileRect)
					&& !game.isMagicOnRoad() && game.hasMoreMissile()) {
				game.sendEvent(new GameEvent(GameEvent.LAUNCH_MISSILE));
			} else if (MathUtil.inside(event.getX(), event.getY(), atomRect)
					&& !game.isMagicOnRoad() && game.hasMoreAtom()) {
				game.sendEvent(new GameEvent(GameEvent.LAUNCH_ATOM));
			}
		}
	}

	@Override
	public void worldEvent() {
		Game game = Game.getInstance();
		if (game.hero == null) {
			return;
		}
		if (moving) {
			game.hero.move(movingDir);
			for (Friend friend : game.friends) {
				friend.keepAround();
			}
		}

		game.hero.fire();
		for (Friend friend : game.friends) {
			friend.fire();
		}

		for (Enemy enemy : game.enemies) {
			if (enemy.path.hasNext()) {
				enemy.move(enemy.path.next());
			}
			enemy.fire();
		}

		if (game.boss != null) {
			// TODO make a path for boss.
			if (game.boss.y < 400) {
				game.boss.move(Direction.SOUTH);
			} else {
				game.boss.move(Direction.NONE);
			}
			if (game.boss.y > 0) {
				game.boss.fire();
			}
		}

		for (MagicWeapon weapon : game.magicWeapons) {
			if (weapon.route.hasNext()) {
				weapon.move(weapon.route.next());
			}
		}

		for (Box box : game.boxes) {
			box.move(Direction.SOUTH);
		}

		for (Coin coin : game.coins) {
			coin.move(Direction.SOUTH);
		}

		game.scene.scrollUp();

		for (Bullet bullet : game.bullets) {
			if (bullet.route.hasNext()) {
				bullet.move(bullet.route.next());
			}
		}

		pickCoins();
		rescueFriends();
		// TODO remove immovable friends
	}

	private void rescueFriends() {

	}

	private void pickCoins() {
		Game game = Game.getInstance();
		for (Coin coin : game.coins) {
			if (MathUtil.isConflicted(game.hero.getRect(), coin.getRect())) {
				game.sendEvent(new GameEvent(GameEvent.PICK_COIN, coin));
			}
		}
	}

	private void stopMoving() {
		movingDir = null;
		moving = false;
	}

	public void keepMoving(Direction dir) {
		movingDir = dir;
		moving = true;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Game game = Game.getInstance();
		Bullet bullet;
		Box box;
		Coin coin;
		Carrier carrier;
		switch (event.eventCode) {
		case GameEvent.KEEP_MOVING:
			keepMoving((Direction) event.param[0]);
			break;
		case GameEvent.STOP_MOVING:
			stopMoving();
			break;
		case GameEvent.ADD_BULLET:
			game.bullets.add((Bullet) event.param[0]);
			break;
		case GameEvent.REMOVE_BULLET:
			game.bullets.remove((Bullet) event.param[0]);
			break;
		case GameEvent.ATTACK_ENEMY:
			// TODO play sound effect.
			Enemy enemy = (Enemy) event.param[0];
			bullet = (Bullet) event.param[1];

			game.beforeExplode(bullet);

			enemy.health -= bullet.power;
			if (enemy.health <= 0 && enemy.isLiving()) {
				enemy.explode();
			}
			break;
		case GameEvent.ATTACK_BOSS:
			// TODO play sound effect.
			carrier = (Carrier) event.param[0];
			bullet = (Bullet) event.param[1];
			game.beforeExplode(bullet);

			carrier.health -= bullet.power;
			if (carrier.health <= 0 && carrier.isLiving()) {
				carrier.explode();
			}

			if (!game.boss.hasForce()) {
				game.boss.explode();
			}
			break;
		case GameEvent.ATTACK_HERO:
			// TODO play sound effect.
			bullet = (Bullet) event.param[1];
			game.hero.health -= bullet.power;

			game.beforeExplode(bullet);

			if (game.hero.health <= 0) {
				game.beforeOver();
			}
			break;
		case GameEvent.CONFLICT_HERO:
			// TODO play sound effect.
			enemy = (Enemy) event.param[1];
			game.hero.health -= enemy.power;
			enemy.explode();
			if (game.hero.health <= 0) {
				game.beforeOver();
			}
			break;
		case GameEvent.ATTACK_FRIEND:
			// TODO play sound effect.
			bullet = (Bullet) event.param[1];
			game.beforeExplode(bullet);
			break;
		case GameEvent.GAME_OVER:
			game.over();
			break;
		case GameEvent.SHOW_MENU:
			game.pause();
			break;
		case GameEvent.EXPLODE_BULLET:
			bullet = (Bullet) event.param[0];
			bullet.explode();
			break;
		case GameEvent.EXPLODE_BOX:
			box = (Box) event.param[0];
			box.explode();
			bullet = (Bullet) event.param[1];
			// TODO play sound effect.
			game.beforeExplode(bullet);
			break;
		case GameEvent.REMOVE_BOX:
			box = (Box) event.param[0];
			game.boxes.remove(box);
			break;
		case GameEvent.REMOVE_ENEMY:
			enemy = (Enemy) event.param[0];
			game.enemies.remove(enemy);
			break;
		case GameEvent.REMOVE_BOSS:
			carrier = (Carrier) event.param[0];
			game.boss.carriers.remove(carrier);
			break;
		case GameEvent.PICK_COIN:
			coin = (Coin) event.param[0];
			game.score.pickCoin(coin);
			// TODO play sound effect.
			game.coins.remove(coin);
			break;
		case GameEvent.ADD_COIN:
			coin = (Coin) event.param[0];
			game.coins.add(coin);
			break;
		case GameEvent.REMOVE_COIN:
			coin = (Coin) event.param[0];
			game.coins.remove(coin);
			break;
		case GameEvent.LAUNCH_MISSILE:
			game.launchSuperMissile();
			break;
		case GameEvent.REMOVE_SUPER_MISSILE:
			SuperMissile missile = (SuperMissile) event.param[0];
			game.removeSuperMissile(missile);
			break;
		case GameEvent.LAUNCH_ATOM:
			game.launchAtom();
			break;
		case GameEvent.REMOVE_ATOM:
			AtomBomb atom = (AtomBomb) event.param[0];
			game.removeAtom(atom);
			break;
		case GameEvent.CLEAR_STAGE:
			controllable = false;
			game.clear();
			game.sendEventDelayed(new GameEvent(GameEvent.FORCE_MOVE));
			break;
		case GameEvent.FORCE_MOVE:
			moving = true;
			movingDir = new Direction(0, -10);
			game.hero.forceMove = true;
			break;
		case GameEvent.SHOW_SCORE_PANEL:
			moving = false;
			movingDir = Direction.NONE;
			game.hero.forceMove = false;
			game.showScorePanel(true);
			break;
		}
	}

	@Override
	public void fadeOut() {

	}

	@Override
	public void fadeIn() {

	}

	private void clearDirs() {
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
		leftUpPressed = false;
		rightUpPressed = false;
		leftDownPressed = false;
		rightDownPressed = false;
	}

	public void clear() {
		clearDirs();
		Game game = Game.getInstance();
		game.sendEventDelayed(new GameEvent(GameEvent.STOP_MOVING));
	}
}
