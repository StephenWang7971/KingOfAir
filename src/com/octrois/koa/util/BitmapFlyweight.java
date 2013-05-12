package com.octrois.koa.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.octrois.koa.R;

public class BitmapFlyweight {

	private static BitmapFlyweight instance = null;

	private Map<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();

	private BitmapFlyweight() {

	}

	public void preLoad(Resources res) {
		addBitmap(res, R.drawable.start_button, "start_button", 220, 100);
		addBitmap(res, R.drawable.settings, "settings", 48, 48);
		addBitmap(res, R.drawable.help, "help", 48, 48);
		addBitmap(res, R.drawable.badge, "badge", 48, 48);
		addBitmap(res, R.drawable.music_on, "music_on", 48, 48);
		addBitmap(res, R.drawable.music_off, "music_off", 48, 48);
		addBitmap(res, R.drawable.sound_on, "sound_on", 48, 48);
		addBitmap(res, R.drawable.sound_off, "sound_off", 48, 48);
		addBitmap(res, R.drawable.hero, "hero", 64, 64);
		addBitmap(res, R.drawable.tank_body, "tank_body", 64, 64);
		addBitmap(res, R.drawable.tank_cannon, "tank_cannon", 64, 16);
		addBitmap(res, R.drawable.lunar, "lunar", 20, 10);
		addBitmap(res, R.drawable.tank_body, "tank_1", 64, 64);
	}

	public void load(Resources res) {
		addBitmap(res, R.drawable.asteroid_explode1, "explosion_1", 64, 64);
		addBitmap(res, R.drawable.asteroid_explode2, "explosion_2", 64, 64);
		addBitmap(res, R.drawable.asteroid_explode3, "explosion_3", 64, 64);
		addBitmap(res, R.drawable.asteroid_explode4, "explosion_4", 64, 64);

		addBitmap(res, R.drawable.asteroid01, "asteroid_1", 64, 64);
		addBitmap(res, R.drawable.asteroid02, "asteroid_2", 64, 64);
		addBitmap(res, R.drawable.asteroid03, "asteroid_3", 64, 64);
		addBitmap(res, R.drawable.asteroid04, "asteroid_4", 64, 64);
		addBitmap(res, R.drawable.asteroid05, "asteroid_5", 64, 64);
		addBitmap(res, R.drawable.asteroid06, "asteroid_6", 64, 64);
		addBitmap(res, R.drawable.asteroid07, "asteroid_7", 64, 64);
		addBitmap(res, R.drawable.asteroid08, "asteroid_8", 64, 64);
		addBitmap(res, R.drawable.asteroid09, "asteroid_9", 64, 64);
		addBitmap(res, R.drawable.asteroid10, "asteroid_10", 64, 64);
		addBitmap(res, R.drawable.asteroid11, "asteroid_11", 64, 64);
		addBitmap(res, R.drawable.asteroid12, "asteroid_12", 64, 64);

		addBitmap(res, R.drawable.helicopter_1, "helicopter_1", 64, 64);
		addBitmap(res, R.drawable.helicopter_2, "helicopter_2", 64, 64);

		addBitmap(res, R.drawable.blue_monster_1, "weird_bird_1", 64, 64);
		addBitmap(res, R.drawable.blue_monster_2, "weird_bird_2", 64, 64);
		addBitmap(res, R.drawable.blue_monster_3, "weird_bird_3", 64, 64);
		addBitmap(res, R.drawable.blue_monster_4, "weird_bird_4", 64, 64);

		addBitmap(res, R.drawable.old_air_craft_1, "old_air_craft_1", 64, 64);
		addBitmap(res, R.drawable.old_air_craft_2, "old_air_craft_2", 64, 64);
		addBitmap(res, R.drawable.old_air_craft_3, "old_air_craft_3", 64, 64);
		addBitmap(res, R.drawable.old_air_craft_4, "old_air_craft_4", 64, 64);
		addBitmap(res, R.drawable.old_air_craft_5, "old_air_craft_5", 64, 64);
		addBitmap(res, R.drawable.old_air_craft_6, "old_air_craft_6", 64, 64);

		addBitmap(res, R.drawable.enemy_misile, "enemy_misile_1", 18, 60);

		addBitmap(res, R.drawable.bigtank_1, "bigtank_1", 80, 60);
		addBitmap(res, R.drawable.bigtank_2, "bigtank_2", 100, 60);
		addBitmap(res, R.drawable.bigtank_3, "bigtank_3", 80, 60);
		addBitmap(res, R.drawable.bigtank_4, "bigtank_4", 80, 60);
		addBitmap(res, R.drawable.bigtank_5, "bigtank_5", 260, 60);

		addBitmap(res, R.drawable.enemy_misile, "enemy_misile", 15, 40);
		addBitmap(res, R.drawable.misile_bullet, "misile_bullet", 15, 40);

		addBitmap(res, R.drawable.super_misile, "super_misile", 30, 150);
		addBitmap(res, R.drawable.atom_bomb_1, "atom_bomb_1", 10, 10);
		addBitmap(res, R.drawable.atom_bomb_2, "atom_bomb_2", 50, 50);
		addBitmap(res, R.drawable.atom_bomb_3, "atom_bomb_3", 90, 90);
		addBitmap(res, R.drawable.atom_bomb_4, "atom_bomb_4", 130, 130);
		addBitmap(res, R.drawable.atom_bomb_5, "atom_bomb_5", 170, 170);
		addBitmap(res, R.drawable.atom_bomb_6, "atom_bomb_6", 210, 210);
		addBitmap(res, R.drawable.atom_bomb_7, "atom_bomb_7", 250, 250);
		addBitmap(res, R.drawable.atom_bomb_8, "atom_bomb_8", 290, 290);
		addBitmap(res, R.drawable.atom_bomb_9, "atom_bomb_9", 330, 330);
		addBitmap(res, R.drawable.atom_bomb_10, "atom_bomb_10", 370, 370);

		addBitmap(res, R.drawable.laser, "laser", 10, 20);
		addBitmap(res, R.drawable.silver_box, "silver_box", 40, 20);
		addBitmap(res, R.drawable.gold_box, "gold_box", 40, 20);
		addBitmap(res, R.drawable.gold_box, "health_box", 40, 20);
		addBitmap(res, R.drawable.silver_coin, "silver_coin", 32, 32);
		addBitmap(res, R.drawable.gold_coin, "gold_coin", 32, 32);
		addBitmap(res, R.drawable.silver_coin, "health_coin", 32, 32);
		addBitmap(res, R.drawable.small_silver_coin, "small_silver_coin", 15,
				15);
		addBitmap(res, R.drawable.small_gold_coin, "small_gold_coin", 15, 15);

		addBitmap(res, R.drawable.super_misile_1, "super_misile_1", 56, 56);
		addBitmap(res, R.drawable.super_misile_2, "super_misile_2", 56, 56);
		addBitmap(res, R.drawable.super_misile_3, "super_misile_3", 56, 56);
		addBitmap(res, R.drawable.super_misile_4, "super_misile_4", 56, 56);
		addBitmap(res, R.drawable.super_misile_5, "super_misile_5", 56, 56);
		addBitmap(res, R.drawable.super_misile_6, "super_misile_6", 56, 56);
		addBitmap(res, R.drawable.atom_bomb, "atom_bomb", 56, 56);

		addBitmap(res, R.drawable.fire_ball, "fire_ball", 16, 16);
		addBitmap(res, R.drawable.snow_ball, "snow_ball", 16, 16);
		addBitmap(res, R.drawable.delta_bullet, "delta_bullet", 15, 30);

		addBitmap(res, R.drawable.time_bar, "time_bar", 360, 60);
		addBitmap(res, R.drawable.magic_box, "magic_box", 78, 64);

		addBitmap(res, R.drawable.left, "left", 48, 48);
		addBitmap(res, R.drawable.right, "right", 48, 48);
		addBitmap(res, R.drawable.up, "up", 48, 48);
		addBitmap(res, R.drawable.down, "down", 48, 48);
		addBitmap(res, R.drawable.left_up, "left_up", 48, 48);
		addBitmap(res, R.drawable.right_up, "right_up", 48, 48);
		addBitmap(res, R.drawable.left_down, "left_down", 48, 48);
		addBitmap(res, R.drawable.right_down, "right_down", 48, 48);
		addBitmap(res, R.drawable.left_up_pressed, "left_up_pressed", 48, 48);
		addBitmap(res, R.drawable.right_up_pressed, "right_up_pressed", 48, 48);
		addBitmap(res, R.drawable.left_down_pressed, "left_down_pressed", 48,
				48);
		addBitmap(res, R.drawable.right_down_pressed, "right_down_pressed", 48,
				48);
		addBitmap(res, R.drawable.left_pressed, "left_pressed", 48, 48);
		addBitmap(res, R.drawable.right_pressed, "right_pressed", 48, 48);
		addBitmap(res, R.drawable.up_pressed, "up_pressed", 48, 48);
		addBitmap(res, R.drawable.down_pressed, "down_pressed", 48, 48);
		addBitmap(res, R.drawable.close_button, "close_button", 200, 50);
		addBitmap(res, R.drawable.home_button, "home_button", 200, 50);
		addBitmap(res, R.drawable.menu_button, "menu_button", 50, 50);
		addBitmap(res, R.drawable.next_stage_button, "next_stage_button", 200,
				50);
		addBitmap(res, R.drawable.skills_button, "skills_button", 200, 50);
		addBitmap(res, R.drawable.retry_button, "retry_button", 200, 50);

		addBitmap(res, R.drawable.help_background, "help_background", 280, 500);
		addBitmap(res, R.drawable.menu_background, "menu_background", 280, 500);
		addBitmap(res, R.drawable.score_background, "score_background", 280,
				500);

		addBitmap(res, R.drawable.cloud_1, "cloud_1", 150, 70);
		addBitmap(res, R.drawable.cloud_2, "cloud_2", 150, 70);
		addBitmap(res, R.drawable.map_a, "map_a", 480, 400);
		addBitmap(res, R.drawable.map_b, "map_b", 480, 400);
		addBitmap(res, R.drawable.map_c, "map_c", 480, 400);
		addBitmap(res, R.drawable.map_d, "map_d", 480, 400);
		addBitmap(res, R.drawable.map_e, "map_e", 480, 400);
		addBitmap(res, R.drawable.map_f, "map_f", 480, 400);
		addBitmap(res, R.drawable.map_g, "map_g", 480, 400);
		addBitmap(res, R.drawable.map_h, "map_h", 480, 400);
		addBitmap(res, R.drawable.map_i, "map_i", 480, 400);
		addBitmap(res, R.drawable.map_j, "map_j", 480, 400);
		addBitmap(res, R.drawable.map_k, "map_k", 480, 400);
		addBitmap(res, R.drawable.map_l, "map_l", 480, 400);
	}

	@SuppressWarnings("deprecation")
	private void addBitmap(Resources res, int resId, String key, int width,
			int height) {
		InputStream is = res.openRawResource(resId);
		BitmapDrawable bmpDraw = new BitmapDrawable(is);
		Bitmap bitmap = bmpDraw.getBitmap();
		bitmaps.put(key, bitmap);
	}

	/**
	 * Note that this method does not check if the key is not exist
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmap(String key) {
		return bitmaps.get(key);
	}

	public static BitmapFlyweight getInstance() {
		if (instance == null) {
			instance = new BitmapFlyweight();
		}
		return instance;
	}
}
