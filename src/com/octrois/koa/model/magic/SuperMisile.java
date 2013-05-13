package com.octrois.koa.model.magic;

import com.octrois.koa.model.direction.Direction;

public class SuperMisile extends MagicWeapon {

	public SuperMisile(int x, int y) {
		picKey = "super_misile";
		width = 30;
		height = 150;
		this.x = x;
		this.y = y;
		power = 20;
		dir = new Direction(0, -15);
		friend = true;
	}
}
