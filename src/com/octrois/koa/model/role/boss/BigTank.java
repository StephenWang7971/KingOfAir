package com.octrois.koa.model.role.boss;

import com.octrois.koa.model.weapon.enemy.FireBallWeapon;

public class BigTank extends Boss {

	Carrier top = new Carrier();
	Carrier body = new Carrier();
	Carrier leftCannon = new Carrier();
	Carrier rightCannon = new Carrier();
	Carrier tier = new Carrier();

	private long lastFire = 0;

	public BigTank() {

		top.health = 1000;
		top.power = 50;
		top.weaponSpeed = 500;
		top.weapon = new FireBallWeapon();
		top.weapon.setOwner(top);
		top.width = 80;
		top.height = 60;
		top.x = 250;
		top.y = -240;
		top.picKey = "bigtank_1";
		top.attackable = true;

		body.health = 5000;
		body.power = 50;
		body.weaponSpeed = 800;
		body.weapon = new FireBallWeapon();
		body.weapon.setOwner(body);
		body.width = 100;
		body.height = 60;
		body.x = 240;
		body.y = -180;
		body.picKey = "bigtank_2";
		body.attackable = true;

		leftCannon.health = 1000;
		leftCannon.power = 50;
		leftCannon.weaponSpeed = 500;
		leftCannon.weapon = new FireBallWeapon();
		leftCannon.weapon.setOwner(leftCannon);
		leftCannon.width = 80;
		leftCannon.height = 60;
		leftCannon.x = 120;
		leftCannon.y = -180;
		leftCannon.picKey = "bigtank_3";
		leftCannon.attackable = true;

		rightCannon.health = 1000;
		rightCannon.power = 50;
		rightCannon.weaponSpeed = 500;
		rightCannon.weapon = new FireBallWeapon();
		rightCannon.weapon.setOwner(rightCannon);
		rightCannon.width = 80;
		rightCannon.height = 60;
		rightCannon.x = 360;
		rightCannon.y = -180;
		rightCannon.picKey = "bigtank_4";
		rightCannon.attackable = true;

		tier.health = 1000;
		tier.power = 50;
		tier.weaponSpeed = 200;
		tier.weapon = new FireBallWeapon();
		tier.width = 300;
		tier.height = 80;
		tier.x = 120;
		tier.y = -120;
		tier.picKey = "bigtank_5";
		tier.attackable = false;

		carriers.add(top);
		carriers.add(body);
		carriers.add(leftCannon);
		carriers.add(rightCannon);
		carriers.add(tier);
	}

	@Override
	public void fire() {
		long tick = System.currentTimeMillis();
		if (tick - lastFire > 2000 - weaponSpeed) {
			top.fire();
			body.fire();
			leftCannon.fire();
			rightCannon.fire();
			lastFire = tick;
		}
	}
}
