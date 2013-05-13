package com.octrois.koa.model.event;

public class GameEvent {

	public int eventCode;

	public Object[] param;

	public GameEvent(int eventCode) {
		this.eventCode = eventCode;
	}

	public GameEvent(int eventCode, Object... obj) {
		this.eventCode = eventCode;
		this.param = obj;
	}

	public static final int KEEP_MOVING = 0x0001;
	public static final int STOP_MOVING = 0x0002;

	public static final int ADD_BULLET = 0x0020;
	public static final int REMOVE_BULLET = 0x0021;
	public static final int EXPLODE_BULLET = 0x0022;

	public static final int ATTACK_ENEMY = 0x0030;
	public static final int ATTACK_FRIEND = 0x0031;
	public static final int ATTACK_HERO = 0x0032;
	public static final int EXPLODE_BOX = 0x0033;
	public static final int REMOVE_BOX = 0x0034;
	public static final int CONFLICT_HERO = 0x0035;
	public static final int REMOVE_ENEMY = 0x0036;
	public static final int ATTACK_BOSS = 0x0037;
	public static final int REMOVE_BOSS = 0x0038;

	public static final int PICK_COIN = 0x0040;
	public static final int ADD_COIN = 0x0041;
	public static final int REMOVE_COIN = 0x0042;

	public static final int LAUNCH_MISILE = 0x0050;
	public static final int LAUNCH_B52 = 0x0051;
	public static final int LAUNCH_ATOM = 0x0052;
	public static final int LAUNCH_THUNDER_STORM = 0x0053;
	public static final int REMOVE_SUPER_MISILE = 0x0054;
	public static final int REMOVE_ATOM = 0x0055;
	public static final int REMOVE_B52 = 0x0056;
	public static final int REMOVE_THUNDER_STORM = 0x0057;

	public static final int START_GAME = 0x0091;
	public static final int GO_HOME = 0x0092;
	public static final int CLEAR_STAGE = 0x0093;
	public static final int FORCE_MOVE = 0x0094;
	public static final int GAME_OVER = 0x0099;

	public static final int SHOW_MENU = 0x00A0;
	public static final int SHOW_SCORE_PANEL = 0x00A1;

}
