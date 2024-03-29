package com.octrois.koa.model.box;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.coin.MissileCoin;

public class MissileBox extends Box {

	public MissileBox() {
		width = 60;
		height = 40;
		picKey = "missile_box";
		coins = new Coin[1];
		coins[0] = new MissileCoin();
	}

	@Override
	public void explode() {
		super.explode();
		Game game = Game.getInstance();
		coins[0].x = this.x;
		coins[0].y = this.y;
		game.addCoin(coins[0]);
	}
}
