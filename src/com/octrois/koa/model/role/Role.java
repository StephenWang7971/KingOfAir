package com.octrois.koa.model.role;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Movable;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.path.GamePath;
import com.octrois.koa.model.weapon.Weapon;

public abstract class Role implements Movable {

	public int maxHealth;
	public int health;
	public int power;
	public int weaponSpeed;
	public int x;
	public int y;
	public int width;
	public int height;
	public Weapon weapon;

	public GamePath path = new GamePath();

	public long lastFire = 0;

	protected int life;
	protected static final int LIVE = 0;
	protected static final int EXPLODING_1 = 1;
	protected static final int EXPLODING_2 = 2;
	protected static final int EXPLODING_3 = 3;
	protected static final int EXPLOING_4 = 4;
	protected static final int EXPLODED = 5;
	protected static final int VANISHED = 6;

	public abstract void fire();

	public abstract void move(Direction dir);

	public abstract void render(Canvas c);

	public RectF getRect() {
		return new RectF(x, getTop(), x + width, getTop() + height);
	}

	public boolean isExploded() {
		return life == EXPLODED;
	}

	public boolean isLiving() {
		return life == LIVE;
	}

	public int getTop() {
		return y;
	}

}
