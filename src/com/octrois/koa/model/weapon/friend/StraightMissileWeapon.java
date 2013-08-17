package com.octrois.koa.model.weapon.friend;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.bullet.friend.StraightMissileBullet;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.weapon.Weapon;

public class StraightMissileWeapon extends Weapon {

	@Override
	public void fire() {
		Game game = Game.getInstance();
		Bullet lunar = new StraightMissileBullet(owner);
		game.sendEvent(new GameEvent(GameEvent.ADD_BULLET, lunar));

	}

}
