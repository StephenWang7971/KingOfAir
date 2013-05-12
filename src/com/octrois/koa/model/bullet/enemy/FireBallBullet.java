package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;

public class FireBallBullet extends Bullet {

	public FireBallBullet(Role role) {
		width = 16;
		height = 16;
		x = role.x + (role.width - width) / 2;
		y = role.y;
		dir = new Direction(0, 5);
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "fire_ball";
	}

}