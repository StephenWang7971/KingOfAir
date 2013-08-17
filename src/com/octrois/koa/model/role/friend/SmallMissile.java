package com.octrois.koa.model.role.friend;

import com.octrois.koa.model.weapon.friend.StraightMissileWeapon;

public class SmallMissile extends Friend {

	public SmallMissile() {
		super();
		width = 15;
		height = 40;
		picKey = "small_missile";
		weaponSpeed = 300;
		weapon = new StraightMissileWeapon();
		weapon.setOwner(this);
		health = 60;
		power = 30;
	}

}
