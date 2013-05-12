package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.FireBallWeapon;

public class MediumTank extends Enemy {

	public MediumTank() {
		health = 90;
		power = 30;
		weaponSpeed = -18;
		width = 64;
		height = 64;
		weapon = new FireBallWeapon();
		weapon.setOwner(this);
		picKey = "tank_";
		maxGesture = 1;
	}
	// TODO auto adjust cannon
	// TODO combined
}
