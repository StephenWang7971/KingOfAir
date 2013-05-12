package com.octrois.koa.model.role.boss;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.octrois.koa.model.direction.Direction;
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

	public boolean isExploded() {
		for (Carrier c : carriers) {
			if (!c.isExploded() && c.attackable) {
				return false;
			}
		}
		return true;
	}
}
