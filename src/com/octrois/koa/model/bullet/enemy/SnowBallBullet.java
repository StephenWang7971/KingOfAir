package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.Role;

public class SnowBallBullet extends Bullet {

	public SnowBallBullet(Role role) {
		width = 16;
		height = 16;
		x = role.x + (role.width - width) / 2;
		y = role.y + role.height;
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "snow_ball";
		route.add(200, 0, 5);
	}

}
