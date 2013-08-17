package com.octrois.koa.model.bullet.friend;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.enemy.Enemy;

public class TraceableMissileBullet extends Bullet {

	public TraceableMissileBullet() {

	}

	public Enemy lookForTarget() {
		Game game = Game.getInstance();
		for (Enemy enemy : game.enemies) {
		}
		return null;
	}

	public void lockTarget(Enemy enemy) {
		// ...route....
	}
}
