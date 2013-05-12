package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.octrois.koa.model.magic.SuperMisile;
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

	private RectF misileRect = new RectF(400, 360, 480, 420);
	private RectF atomRect = new RectF(400, 480, 480, 540);
	// TODO player could buy more magic boxes up to 4.
	// TODO player could launch other magic, B52, Thunderstom

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

	@Override
	public void update() {
		Game game = Game.getInstance();

		backgroundRect.right = game.mCanvasWidth;
		backgroundRect.bottom = game.mCanvasHeight;

		menuRect.left = game.mCanvasWidth - 60;
		menuRect.top = 10;
		menuRect.right = menuRect.left + 50;
		menuRect.bottom = menuRect.top + 50;

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
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		if (leftPressed) {
			Bitmap left = bf.getBitmap("left_pressed");
			canvas.drawBitmap(left, leftRect.left, leftRect.top, null);
		} else {
			Bitmap left = bf.getBitmap("left");
			canvas.drawBitmap(left, leftRect.left, leftRect.top, null);
		}
		if (rightPressed) {
			Bitmap right = bf.getBitmap("right_pressed");
			canvas.drawBitmap(right, rightRect.left, rightRect.top, null);
		} else {
			Bitmap right = bf.getBitmap("right");
			canvas.drawBitmap(right, rightRect.left, rightRect.top, null);
		}
		if (upPressed) {
			Bitmap up = bf.getBitmap("up_pressed");
			canvas.drawBitmap(up, upRect.left, upRect.top, null);
		} else {
			Bitmap up = bf.getBitmap("up");
			canvas.drawBitmap(up, upRect.left, upRect.top, null);
		}
		if (downPressed) {
			Bitmap down = bf.getBitmap("down_pressed");
			canvas.drawBitmap(down, downRect.left, downRect.top, null);
		} else {
			Bitmap down = bf.getBitmap("down");
			canvas.drawBitmap(down, downRect.left, downRect.top, null);
		}
		if (leftUpPressed) {
			Bitmap leftUp = bf.getBitmap("left_up_pressed");
			canvas.drawBitmap(leftUp, leftUpRect.left, leftUpRect.top, null);
		} else {
			Bitmap leftUp = bf.getBitmap("left_up");
			canvas.drawBitmap(leftUp, leftUpRect.left, leftUpRect.top, null);
		}
		if (rightUpPressed) {
			Bitmap rightUp = bf.getBitmap("right_up_pressed");
			canvas.drawBitmap(rightUp, rightUpRect.left, rightUpRect.top, null);
		} else {
			Bitmap rightUp = bf.getBitmap("right_up");
			canvas.drawBitmap(rightUp, rightUpRect.left, rightUpRect.top, null);
		}
		if (leftDownPressed) {
			Bitmap leftDown = bf.getBitmap("left_down_pressed");
			canvas.drawBitmap(leftDown, leftDownRect.left, leftDownRect.top,
					null);
		} else {
			Bitmap leftDown = bf.getBitmap("left_down");
			canvas.drawBitmap(leftDown, leftDownRect.left, leftDownRect.top,
					null);
		}
		if (rightDownPressed) {
			Bitmap rightDown = bf.getBitmap("right_down_pressed");
			canvas.drawBitmap(rightDown, rightDownRect.left, rightDownRect.top,
					null);
		} else {
			Bitmap rightDown = bf.getBitmap("right_down");
			canvas.drawBitmap(rightDown, rightDownRect.left, rightDownRect.top,
					null);
		}
	}

	private void drawMagicBoxes(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap magicBox = bf.getBitmap("magic_box");
		Bitmap superMisile_1 = bf.getBitmap("super_misile_1");
		Bitmap superMisile_2 = bf.getBitmap("super_misile_2");
		Bitmap superMisile_3 = bf.getBitmap("super_misile_3");
		Bitmap superMisile_4 = bf.getBitmap("super_misile_4");
		Bitmap superMisile_5 = bf.getBitmap("super_misile_5");
		Bitmap superMisile_6 = bf.getBitmap("super_misile_6");

		Bitmap atomBomb = bf.getBitmap("atom_bomb");
		canvas.drawBitmap(magicBox, misileRect.left, misileRect.top, null);

		count++;
		if (count == 36) {
			count = 0;
		}

		if (count % 36 < 6) {
			canvas.drawBitmap(superMisile_1, misileRect.left + 5,
					misileRect.top, null);
		} else if (count % 36 < 12) {
			canvas.drawBitmap(superMisile_2, misileRect.left + 5,
					misileRect.top, null);
		} else if (count % 36 < 18) {
			canvas.drawBitmap(superMisile_3, misileRect.left + 5,
					misileRect.top, null);
		} else if (count % 36 < 24) {
			canvas.drawBitmap(superMisile_4, misileRect.left + 5,
					misileRect.top, null);
		} else if (count % 36 < 30) {
			canvas.drawBitmap(superMisile_5, misileRect.left + 5,
					misileRect.top, null);
		} else if (count % 36 < 36) {
			canvas.drawBitmap(superMisile_6, misileRect.left + 5,
					misileRect.top, null);
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
			if (MathUtil.inside(event.getX(), event.getY(), leftRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.WEST));
				clearDirs();
				leftPressed = true;
			} else if (MathUtil.inside(event.getX(), event.getY(), rightRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.EAST));
				clearDirs();
				rightPressed = true;
			} else if (MathUtil.inside(event.getX(), event.getY(), upRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH));
				clearDirs();
				upPressed = true;
			} else if (MathUtil.inside(event.getX(), event.getY(), downRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.SOUTH));
				clearDirs();
				downPressed = true;
			} else if (MathUtil.inside(event.getX(), event.getY(), leftUpRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH_WEST));
				clearDirs();
				leftUpPressed = true;
			} else if (MathUtil.inside(event.getX(), event.getY(), rightUpRect)) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH_EAST));
				clearDirs();
				rightUpPressed = true;
			} else if (MathUtil
					.inside(event.getX(), event.getY(), leftDownRect)) {
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

			if (MathUtil.inside(event.getX(), event.getY(), menuRect)) {
				game.sendEvent(new GameEvent(GameEvent.SHOW_MENU));
			} else if (MathUtil.inside(event.getX(), event.getY(), misileRect)) {
				game.sendEvent(new GameEvent(GameEvent.LAUNCH_MISILE));
			} else if (MathUtil.inside(event.getX(), event.getY(), atomRect)) {
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
			weapon.move();
		}

		for (Box box : game.boxes) {
			box.move(Direction.SOUTH);
		}

		for (Coin coin : game.coins) {
			coin.move(Direction.SOUTH);
		}

		game.scene.scrollUp();

		for (Bullet bullet : game.bullets) {
			bullet.move(bullet.dir);
		}

		if (game.isStagePass()) {
			// TODO show ... pass animation.
			game.showScorePanel();
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
		case GameEvent.LAUNCH_MISILE:
			game.launchSuperMisile();
			break;
		case GameEvent.REMOVE_SUPER_MISILE:
			SuperMisile misile = (SuperMisile) event.param[0];
			game.removeSuperMisile(misile);
			break;
		case GameEvent.LAUNCH_ATOM:
			game.launchAtom();
			break;
		case GameEvent.REMOVE_ATOM:
			AtomBomb atom = (AtomBomb) event.param[0];
			game.removeAtom(atom);
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
