package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.PurpleWeapon;

public class PurpleMonster extends Enemy {
	public PurpleMonster() {
		health = 1000;
		power = 30;
		weaponSpeed = -18;
		width = 64;
		height = 64;
		weapon = new PurpleWeapon();
		weapon.setOwner(this);
		picKey = "asteroid_";
		maxGesture = 12;
	}
}
