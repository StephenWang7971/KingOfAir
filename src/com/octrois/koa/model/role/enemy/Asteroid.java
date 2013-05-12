package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.LaserWeapon;

public class Asteroid extends Enemy {

	public Asteroid() {
		health = 30;
		power = 30;
		weaponSpeed = -18;
		width = 64;
		height = 64;
		weapon = new LaserWeapon();
		weapon.setOwner(this);
		picKey = "asteroid_";
		maxGesture = 12;
	}
}
