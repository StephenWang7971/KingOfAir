package com.octrois.koa.model.state;

public class StateFlyweight {

	public static MenuPanel MENU_PANEL = new MenuPanel();

	public static Achievement ARCHIEVEMENT = new Achievement();

	public static MainScreen MAIN_SCREEN = new MainScreen();

	public static Playing PLAYING = new Playing();

	public static ScorePanel SCORE_PANEL = new ScorePanel();

	public static SkillsPanel SKILLS_PANEL = new SkillsPanel();

	public static TreasureBox TREASURE_BOX = new TreasureBox();

	public static HelpPanel HELP_PANEL = new HelpPanel();

	public static SettingPanel SETTING_PANEL = new SettingPanel();
}
