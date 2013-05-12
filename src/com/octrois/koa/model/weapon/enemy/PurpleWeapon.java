package com.octrois.koa.model.weapon.enemy;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.bullet.enemy.LaserBullet;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.weapon.Weapon;

public class PurpleWeapon extends Weapon {

	@Override
	public void fire() {
		Game game = Game.getInstance();
		Bullet linear = new LaserBullet(owner);
		game.sendEvent(new GameEvent(GameEvent.ADD_BULLET, linear));
	}

}
