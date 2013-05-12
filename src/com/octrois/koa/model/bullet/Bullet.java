package com.octrois.koa.model.bullet;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.Movable;
import com.octrois.koa.model.box.Box;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.boss.Carrier;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.friend.Friend;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public abstract class Bullet implements Movable {

	public int x, y;
	public boolean friend;
	public Direction dir;
	public int power;
	public int width;
	public int height;

	protected String picKey;

	protected int state;
	protected static final int NORMAL = 0;
	protected static final int EXPLOSION_1 = 1;
	protected static final int EXPLOSION_2 = 2;
	protected static final int EXPLOSION_3 = 3;
	protected static final int EXPLOSION_4 = 4;
	protected static final int VANISHED = 5;

	@Override
	public void move(Direction dir) {
		Game game = Game.getInstance();

		x += dir.getDiffX();
		y += dir.getDiffY();

		if (isExplosing()) {
			state++;
			return;
		}

		if (isVanished()) {
			Game.getInstance().sendEvent(
					new GameEvent(GameEvent.REMOVE_BULLET, this));
			return;
		}

		detectAttack();

		if (!MathUtil.inside(x, getTop(), game.getBackgroundRect())) {
			Game.getInstance().sendEvent(
					new GameEvent(GameEvent.REMOVE_BULLET, this));
		}
	}

	private boolean isExplosing() {
		return state > NORMAL && state < VANISHED;
	}

	private boolean isVanished() {
		return state == VANISHED;
	}

	protected void detectAttack() {
		Game game = Game.getInstance();
		if (friend) {
			for (Enemy enemy : game.enemies) {
				if (enemy.isLiving()
						&& MathUtil.isConflicted(getRect(), enemy.getRect())) {
					game.sendEvent(new GameEvent(GameEvent.ATTACK_ENEMY, enemy,
							this));
					break;
				}
			}
			for (Box box : game.boxes) {
				if (MathUtil.isConflicted(getRect(), box.getRect())) {
					game.sendEvent(new GameEvent(GameEvent.EXPLODE_BOX, box,
							this));
				}
			}
			if (game.boss != null) {
				for (Carrier carrier : game.boss.carriers) {
					if (carrier.attackable) {
						if (MathUtil.isConflicted(getRect(),
								carrier.getAttackableRect())) {
							game.sendEvent(new GameEvent(GameEvent.ATTACK_BOSS,
									carrier, this));
						}
					}
				}
			}
		} else {
			if (game.hero.isLiving()
					&& MathUtil.isConflicted(getRect(), game.hero.getRect())) {
				game.sendEvent(new GameEvent(GameEvent.ATTACK_HERO, game.hero,
						this));
				return;
			}

			for (Friend friend : game.friends) {
				if (friend.isLiving()
						&& MathUtil.isConflicted(getRect(), friend.getRect())) {
					game.sendEvent(new GameEvent(GameEvent.ATTACK_FRIEND,
							friend, this));
					break;
				}
			}
		}

	}

	public RectF getRect() {
		return new RectF(x, getTop(), x + width, getTop() + height);
	}

	public void render(Canvas canvas) {
		if (isVanished()) {
			return;
		}
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		if (!isExplosing()) {
			canvas.drawBitmap(bf.getBitmap(picKey), x, getTop(), null);
		} else {
			canvas.drawBitmap(bf.getBitmap(picKey), x, getTop(), null);
		}
	}

	protected float getTop() {
		return y;
	}

	public void explode() {
		state = EXPLOSION_1;
	}

}
