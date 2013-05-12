package com.octrois.koa.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.util.BitmapFlyweight;

public class Score {

	long liveTime;
	long enemyKilled;
	long silver;
	long gold;

	public void pickCoin(Coin coin) {
		switch (coin.type) {
		case Coin.GOLD:
			gold += coin.volume;
			break;
		case Coin.SILVER:
			silver += coin.volume;
			if (silver >= 100) {
				gold++;
				silver -= 100;
			}
			break;
		}
	}

	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();

		canvas.drawBitmap(bf.getBitmap("time_bar"), 0, 0, null);

		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		canvas.drawBitmap(bf.getBitmap("small_gold_coin"), 200, 10, null);
		canvas.drawText(String.valueOf(gold), 240, 20, paint);
		canvas.drawBitmap(bf.getBitmap("small_silver_coin"), 300, 10, null);
		canvas.drawText(String.valueOf(silver), 340, 20, paint);
	}

}
