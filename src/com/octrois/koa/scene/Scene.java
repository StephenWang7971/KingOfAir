package com.octrois.koa.scene;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.box.Box;
import com.octrois.koa.model.box.BoxFactory;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.boss.BigTank;
import com.octrois.koa.model.role.boss.Boss;
import com.octrois.koa.model.role.enemy.Enemy;
import com.octrois.koa.model.role.enemy.MonsterFactory;

public class Scene {

	public int width = 480;
	public int height = 100000;
	public int top;

	public int scrollSpeed;

	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Integer> enemyToken = new ArrayList<Integer>();

	private List<Box> boxes = new ArrayList<Box>();
	private List<Integer> boxToken = new ArrayList<Integer>();

	private List<GameMap> near = new ArrayList<GameMap>();
	private List<GameMap> far = new ArrayList<GameMap>();
	private List<GameMap> overlap = new ArrayList<GameMap>();

	private Boss boss;

	public Scene() {
		// TODO load from xml file for more extensibility.
		int[] enemyTokens = new int[] { 0, 2000, 5005, 8002, 10003, 12007,
				14000, 17002, 17003, 18007, 18008, 22002, 22006, 26000, 28000,
				30000, 32000, 32000, 32000, 32000, 35005, 38002, 40003, 42007,
				44000, 47002, 47003, 48007, 48008, 52002, 52006, 56000, 58000 };
		for (int t : enemyTokens) {
			enemyToken.add(t);
		}

		String[] colors = new String[] { "Red", "Red", "Red", "Green", "Gray",
				"Gray", "Blue", "Gray", "Cyan", "Gray", "Green", "Gray",
				"Gray", "Orange", "Gray", "Gray", "Gray", "Gray", "Gray",
				"Gray", "Gray", "Green", "Gray", "Purple", "Blue", "Gray",
				"Gray", "Gray", "Blue", "Gray", "Purple", "Gray", "Gray",
				"Gray" };
		for (int i = 0; i < colors.length; i++) {
			Enemy enemy = MonsterFactory.getMonster(colors[i]);
			enemy.x = (60 * i) % Game.getInstance().mCanvasWidth;
			enemies.add(enemy);
		}

		for (int i = 0; i < 3; i++) {
			enemies.get(i).path.add(60 * (i + 1), 0, 2);
			enemies.get(i).path.add(800, 1, 1);
		}

		for (int i = 0; i < colors.length; i++) {
			enemies.get(i).path.add(1000, 0, 2);
		}

		int[] boxTokens = new int[] { 5000, 15000, 30000, 60000, 90000 };
		for (int i : boxTokens) {
			boxToken.add(i);
		}

		String[] boxColors = new String[] { "Silver", "Silver", "Gold",
				"Health", "Missile" };
		for (int i = 0; i < boxColors.length; i++) {
			Box box = BoxFactory.createBox(boxColors[i]);
			box.x = (100 * i) / Game.getInstance().mCanvasWidth;
			boxes.add(box);
		}

		boss = new BigTank();

		GameMap cloud1 = new GameMap();
		cloud1.speed = -16;
		cloud1.width = 150;
		cloud1.height = 70;
		cloud1.x = 100;
		cloud1.y = height - 1600;
		cloud1.picKey = "cloud_1";
		overlap.add(cloud1);

		GameMap tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 99000;
		tile.picKey = "map_a";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 98600;
		tile.picKey = "map_b";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 98200;
		tile.picKey = "map_c";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 97800;
		tile.picKey = "map_d";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 97400;
		tile.picKey = "map_e";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 97000;
		tile.picKey = "map_f";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 96600;
		tile.picKey = "map_g";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 96200;
		tile.picKey = "map_h";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 95800;
		tile.picKey = "map_i";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 95400;
		tile.picKey = "map_j";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 95000;
		tile.picKey = "map_k";
		far.add(tile);

		tile = new GameMap();
		tile.width = 480;
		tile.height = 400;
		tile.speed = -19;
		tile.x = 0;
		tile.y = 94600;
		tile.picKey = "map_l";
		far.add(tile);
	}

	public void scrollUp() {
		Game game = Game.getInstance();
		if (top <= 0) {
			return;
		}
		// TODO make the whole map slower. have a 'speed' variable.
		// if (top <= height - 0 && top >= height - 1000) {
		// scrollSpeed = 10;
		// } else {
		scrollSpeed = 20;
		// }
		top -= scrollSpeed;

		for (GameMap tile : far) {
			tile.move(Direction.SOUTH);
		}

		for (GameMap tile : near) {
			tile.move(Direction.SOUTH);
		}

		for (GameMap tile : overlap) {
			tile.move(Direction.SOUTH);
		}

		if (enemyToken.size() > 0) {
			int pos = enemyToken.get(0);

			if (height - game.mCanvasHeight - top >= pos) {
				Enemy enemy = enemies.get(0);
				enemy.y = 0;
				game.enemies.add(enemy);
				enemies.remove(0);
				enemyToken.remove(0);
			}
		}

		if (boxToken.size() > 0) {
			int pos = boxToken.get(0);

			if (height - game.mCanvasHeight - top >= pos) {
				Box box = boxes.get(0);
				box.y = 0;
				game.boxes.add(box);
				boxes.remove(0);
				boxToken.remove(0);
			}
		}

		if (top < 1000) {
			game.boss = boss;
		}

		// TODO Show friends
	}

	public void render(Canvas canvas) {
		Game game = Game.getInstance();
		Paint paint = new Paint();
		paint.setARGB(255, 60, 105, 37);
		canvas.drawRect(game.getBackgroundRect(), paint);

		drawFar(canvas);

		// drawNear
	}

	private void drawFar(Canvas canvas) {
		for (GameMap tile : far) {
			tile.render(canvas);
		}
	}

	public void drawOverlap(Canvas canvas) {
		for (GameMap tile : overlap) {
			tile.render(canvas);
		}
	}

	public void drawProgressBar(Canvas canvas) {
		Game game = Game.getInstance();

		float progress = (float) top / (float) (height - game.mCanvasHeight);
		int progPos = 120 + (int) (progress * 200);
		RectF onGoingBar = new RectF(game.mCanvasWidth - 30, 120,
				game.mCanvasWidth - 20, progPos);
		Paint onGoingPaint = new Paint();
		onGoingPaint.setStyle(Style.FILL_AND_STROKE);
		onGoingPaint.setARGB(255, 255, 185, 50);

		RectF finishedBar = new RectF(game.mCanvasWidth - 30, progPos,
				game.mCanvasWidth - 20, 320);
		Paint finishedPaint = new Paint();
		finishedPaint.setStyle(Style.STROKE);
		finishedPaint.setARGB(255, 255, 185, 50);

		canvas.drawRect(onGoingBar, onGoingPaint);
		canvas.drawRect(finishedBar, finishedPaint);
	}
}
