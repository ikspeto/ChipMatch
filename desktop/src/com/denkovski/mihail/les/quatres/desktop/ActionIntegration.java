package com.denkovski.mihail.les.quatres.desktop;

import com.denkovski.mihail.les.quatres.ActionResolver;

public class ActionIntegration implements ActionResolver {

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboardGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shareLink(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rateLink() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void logE(String tag, String text) {
        System.out.println(text);
    }


    public void exitApp() {

    }

}
