package com.denkovski.mihail.les.quatres.android;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.denkovski.mihail.les.quatres.ActionResolver;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.revmob.ads.banner.RevMobBanner;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	
	public GameHelper gameHelper;
	
//	public RelativeLayout bannerLayout;
	public RevMobIntegration revmob;
	public RelativeLayout applicationLayout;
	public RevMobBanner bannerAd;
	public boolean bannerVisible = false, resumed = false;
	
	private float screenHeight;
	private int bannerHeight;
	private RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
	        RelativeLayout.LayoutParams.WRAP_CONTENT, bannerHeight);
	private Message msg;

    private UiLifecycleHelper uiHelper;

    private FacebookDialog shareDialog;

	 private Handler handler = new Handler() {
         public void handleMessage(Message msg) {
        	 if(msg.arg1 == 0) {
        		 bannerAd.setVisibility(msg.what);       
        	 } else {
//        		 if(applicationLayout.)
//        		 applicationLayout.removeAllViews();
//        		 applicationLayout.addView(bannerAd, adParams);
        		 bannerAd.setVisibility(msg.what);       
        	 }
         }
     };
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//revmob.setTimeoutInSeconds(5); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		bannerHeight = (int) (0.095f*screenHeight);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		revmob = new RevMobIntegration(this);
//	    bannerLayout = new RelativeLayout(this);
	    applicationLayout = new RelativeLayout(this);
//		bannerLayout.setVisibility(View.GONE);
	    adParams = new RelativeLayout.LayoutParams(
		        RelativeLayout.LayoutParams.WRAP_CONTENT, bannerHeight);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
//		adParams.addRule(RelativeLayout.);
		  
//		bannerLayout.setLayoutParams(adParams);
		    
		View gdxView = initializeForView(new ChipMatch(this, revmob), config);
		applicationLayout.addView(gdxView);
		    
		if(revmob.getBannerAd() != null) {
		   bannerAd = revmob.getBannerAd();
		   applicationLayout.addView(bannerAd, adParams);
		}
		    
		setContentView(applicationLayout);
//		initialize(new LesQuatres(this), config);
		if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        gameHelper.setup(this);


        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                .setLink("https://play.google.com/store/apps/details?id=com.denkovski.mihail.les.quatres.android")
                .build();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		resumed = true;
        uiHelper.onResume();
//		revmob = RevMob.start(this);
//		revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		gameHelper.onStop();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);

        uiHelper.onActivityResult(request, response, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }


    @Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
//		Log.e("Score", ""+score);
		if(getSignedInGPGS()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIgsGs66MVEAIQDg", score);
		}
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
//		Log.e("Score", achievementId);
		if(getSignedInGPGS()) {
            Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
		}
		
	}

	@Override
	public void getLeaderboardGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIgsGs66MVEAIQDg"), 100);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
	}

	@Override
	public void getAchievementsGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

//    @Override
//    public void logE(String tag, String text)
//    {
//        Log.e(tag, text);
//        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//    }

	@Override
	public void shareLink(String url) {
//		Intent shareIntent = new Intent(Intent.ACTION_SEND);
//		shareIntent.setType("text/plain");
//        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
//		shareIntent.putExtra(Intent.EXTRA_TEXT, uri.toString());
//		startActivity(Intent.createChooser(shareIntent, "Share game"));
//


//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = "market://details?id=" + this.getPackageName();
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Chip Match");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));


        uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	
	@Override
	public void rateLink() {
		Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
		  startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
		}
	}
	
	public void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void logE(String tag, String text) {
        Log.e(tag, text);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showBanner(boolean show) {
		msg = new Message();
		if(show) {
			msg.what = View.VISIBLE;
			if(bannerAd == null || resumed) {
				resumed = false;
				msg.arg1 = 1;
	            bannerAd = revmob.getBannerAd();
	            handler.sendMessage(msg);
	        } else {
	        	msg.arg1 = 0;
	        	handler.sendMessage(msg);
	        }
//			handler.sendEmptyMessage(View.VISIBLE);
		 } else {
			 msg.what = View.INVISIBLE;
			 msg.arg1 = 0;
			 handler.sendMessage(msg);
//			 handler.sendEmptyMessage(View.INVISIBLE);
		 }
	}
}
