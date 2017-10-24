package com.denkovski.mihail.les.quatres;

public interface ActionResolver {
	
	
	// Google Play Services
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS();
	public void getAchievementsGPGS();
	
	
	// Revmob
//	public void showFullscreenAd(boolean show);
//	public void showBannerAd(boolean show);
	
	// Share, rate, etc.
	public void shareLink(String url);
	public void rateLink();

    public void exitApp();

    public void logE(String tag, String text);
	
	
}