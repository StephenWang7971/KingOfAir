package com.octrois.koa.model.box;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.coin.GoldCoin;

public class GoldBox extends Box {

	public GoldBox() {
		width = 60;
		height = 40;
		picKey = "gold_box";

		coins = new Coin[3];
		coins[0] = new GoldCoin();
		coins[1] = new GoldCoin();
		coins[2] = new GoldCoin();
	}

	@Override
	public void explode() {
		super.explode();
		Game game = Game.getInstance();
		// TODO need a mehtod to make sure all are in the screen. anyway.
		coins[0].x = this.x - 70;
		coins[0].y = this.y + 60;
		coins[1].x = this.x;
		coins[1].y = this.y + 60;
		coins[2].x = this.x + 100;
		coins[2].y = this.y + 60;

		game.addCoin(coins[0]);
		game.addCoin(coins[1]);
		game.addCoin(coins[2]);
	}
}
