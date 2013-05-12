package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.CyanWeapon;

public class Helicopter extends Enemy {

	public Helicopter() {
		health = 30;
		power = 30;
		width = 64;
		height = 64;
		weapon = new CyanWeapon();
		weapon.setOwner(this);
		picKey = "helicopter_";
		maxGesture = 2;
		weaponSpeed = 1500;
	}
}
