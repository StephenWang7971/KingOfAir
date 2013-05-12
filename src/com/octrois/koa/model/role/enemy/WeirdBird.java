package com.octrois.koa.model.role.enemy;

import com.octrois.koa.model.weapon.enemy.BirdMouth;

public class WeirdBird extends Enemy {

	public WeirdBird() {
		health = 400;
		power = 100;
		weaponSpeed = -19;
		width = 64;
		height = 64;
		weapon = new BirdMouth();
		weapon.setOwner(this);
		picKey = "weird_bird_";
		maxGesture = 4;
	}

}
