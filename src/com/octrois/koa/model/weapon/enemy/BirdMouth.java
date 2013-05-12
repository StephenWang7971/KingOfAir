package com.octrois.koa.model.weapon.enemy;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.bullet.enemy.DeltaBullet;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.weapon.Weapon;

public class BirdMouth extends Weapon {

	@Override
	public void fire() {
		Game game = Game.getInstance();
		Bullet delta = new DeltaBullet(owner);
		game.sendEvent(new GameEvent(GameEvent.ADD_BULLET, delta));
	}

}
