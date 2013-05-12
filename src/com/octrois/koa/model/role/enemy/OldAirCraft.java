package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.MisileWeapon;

public class OldAirCraft extends Enemy {

	public OldAirCraft() {
		health = 180;
		power = 30;
		weaponSpeed = -14;
		width = 64;
		height = 64;
		weapon = new MisileWeapon();
		weapon.setOwner(this);
		picKey = "old_air_craft_";
		maxGesture = 6;
		weaponSpeed = 1000;
	}
}
