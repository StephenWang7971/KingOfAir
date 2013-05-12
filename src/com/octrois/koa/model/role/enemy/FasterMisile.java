package com.octrois.koa.model.role.enemy;

public class FasterMisile extends Enemy {

	public FasterMisile() {
		health = 60;
		power = 30;
		weaponSpeed = -5;
		width = 18;
		height = 60;
		picKey = "enemy_misile_";
		maxGesture = 1;
	}
}
