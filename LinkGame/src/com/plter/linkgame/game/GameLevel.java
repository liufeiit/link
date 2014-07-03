package com.plter.linkgame.game;

public enum GameLevel {
	
	L_3_4_10(1, 3, 4, 10),
	L_3_6_13(2, 3, 6, 13),
	L_4_5_15(3, 4, 5, 15),
	L_4_6_18(4, 4, 6, 18),
	L_5_6_25(5, 5, 6, 25),
	L_5_8_28(6, 5, 8, 28),
	L_6_7_30(7, 6, 7, 30),
	L_6_8_35(8, 6, 8, 35),
	L_6_9_38(9, 6, 9, 38),
	L_6_10_40(10, 6, 10, 40);

	public static final int MAX_H_CARDS_COUNT = 6;
	public static final int MAX_V_CARDS_COUNT = 10;
	
	public final int maxTime ;//当前关卡的最大时间
	public final int hCardCount ;//水平方向上的卡片的数量
	public final int vCardCount ;//垂直方向上的卡片的数量
	
	public final int level;

	private GameLevel(int level, int hCardCount, int vCardCount, int maxTime) {
		this.level = level;
		this.hCardCount = hCardCount;
		this.vCardCount = vCardCount;
		this.maxTime = maxTime;
	}
	
	public static GameLevel level(int level) {
		for(GameLevel l : values()) {
			if(l.level == level) {
				return l;
			}
		}
		return L_3_4_10;
	}
}
