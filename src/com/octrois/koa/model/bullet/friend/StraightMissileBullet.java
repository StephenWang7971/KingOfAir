package com.octrois.koa.model.bullet.friend;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.Role;

public class StraightMissileBullet extends Bullet {

	public StraightMissileBullet(Role role) {
		width = 15;
		height = 40;
		state = NORMAL;
		x = role.x + (role.width - width) / 2;
		y = role.y + height;
		friend = true;
		power = role.power;
		picKey = "small_misile";

		route.add(3, 0, 5);
		route.add(50, 0, -15);
	}
}
