package com.octrois.koa.model.box;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.coin.HealthCoin;

public class HealthBox extends Box {

	public HealthBox() {
		picKey = "health_box";
		width = 60;
		height = 40;
		picKey = "silver_box";
		coins = new Coin[1];
		coins[0] = new HealthCoin();
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
