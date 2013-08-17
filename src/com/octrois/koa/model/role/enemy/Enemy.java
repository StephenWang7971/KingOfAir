package com.octrois.koa.model.role.enemy;

import android.graphics.Canvas;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public abstract class Enemy extends Role {

	protected int dyingTick = 0;

	protected int gesture = 0;
	protected int maxGesture;
	protected String picKey;

	@Override
	public void move(Direction dir) {

		Game game = Game.getInstance();
		x += dir.getDiffX();
		y += dir.getDiffY();

		if (!isLiving() && !isExploded()) {
			dyingTick++;
			if (dyingTick % 3 == 0) {
				life++;
			}
			return;
		}
		if (isExploded()) {
			path.clear();
			game.sendEvent(new GameEvent(GameEvent.REMOVE_ENEMY, this));
			return;
		}
		gesture++;
		if (gesture >= maxGesture) {
			gesture = 0;
		}

		// TODO detect if attacked friends.
		if (MathUtil.isConflicted(getRect(), game.hero.getRect())) {
			game.sendEvent(new GameEvent(GameEvent.CONFLICT_HERO, game.hero,
					this));
		}

		if (!MathUtil.isConflicted(getRect(), game.getBackgroundRect())) {
			path.clear();
			game.sendEvent(new GameEvent(GameEvent.REMOVE_ENEMY, this));
		}

	}

	@Override
	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		if (isLiving()) {
			canvas.drawBitmap(
					bf.getBitmap(picKey + String.valueOf(gesture + 1)), x,
					getTop(), null);
		}
		if (!isLiving() && !isExploded()) {
			String key = "";
			switch (life) {
			case EXPLODING_1:
				key = "explosion_1";
				break;
			case EXPLODING_2:
				key = "explosion_2";
				break;
			case EXPLODING_3:
				key = "explosion_3";
				break;
			case EXPLODING_4:
				key = "explosion_4";
				break;
			}
			canvas.drawBitmap(bf.getBitmap(key), x, getTop(), null);
		}

		if (isExploded()) {
		}
	}

	@Override
	public void fire() {
		if (!isLiving()) {
			return;
		}

		if (weapon == null) {
			return;
		}

		long tick = System.currentTimeMillis();
		if (lastFire == 0) {
			lastFire = tick;
			return;
		}
		if (tick - lastFire > (5000 - weaponSpeed)) {
			weapon.fire();
			lastFire = tick;
		}
	}

	public void explode() {
		life = EXPLODING_1;
	}

}
