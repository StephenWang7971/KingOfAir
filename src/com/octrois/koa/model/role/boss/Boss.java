package com.octrois.koa.model.role.boss;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.Role;

public abstract class Boss extends Role {

	public List<Carrier> carriers = new ArrayList<Carrier>();

	@Override
	public void move(Direction dir) {
		this.x += dir.getDiffX();
		this.y += dir.getDiffY();
		for (Carrier carrier : carriers) {
			carrier.move(dir);
		}
		if (!isLiving()) {
			life++;
		}
		if (isExploded()) {
			Game.getInstance().sendEvent(new GameEvent(GameEvent.CLEAR_STAGE));
		}
	}

	@Override
	public void render(Canvas canvas) {
		for (Carrier carrier : carriers) {
			carrier.render(canvas);
		}
	}

	public void collapse() {

	}

	public void combine() {

	}

	public void evolute() {

	}

	public boolean hasForce() {
		for (Carrier c : carriers) {
			if (c.isLiving() && c.attackable) {
				return true;
			}
		}
		return false;
	}

	public void explode() {
		life = EXPLODING_1;
	}
}
