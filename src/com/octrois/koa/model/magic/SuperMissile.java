package com.octrois.koa.model.magic;

public class SuperMissile extends MagicWeapon {

	public SuperMissile(int x, int y) {
		picKey = "super_missile";
		width = 30;
		height = 150;
		this.x = x;
		this.y = y;
		power = 20;
		friend = true;

		route.add(50, 0, -15);
	}
}
