package com.denkovski.mihail.les.quatres.android;

import com.denkovski.mihail.les.quatres.RevMobResolver;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.revmob.ads.link.RevMobLink;

public class RevMobIntegration implements RevMobResolver {

	private RevMob revmob;
	private RevMobFullscreen fullscreenAd;
	private RevMobBanner bannerAd;
	private RevMobAdsListener listener;
	private RevMobLink linkAd;
	
	private AndroidLauncher androidLauncher;
	
//	 private Handler handler = new Handler() {
//         public void handleMessage(Message msg) {
//             bannerAd.setVisibility(msg.what);       
//             
//         }
//     };
//	
	public RevMobIntegration(AndroidLauncher _androidLauncher) {
		this.androidLauncher = _androidLauncher;
		
		revmob = RevMob.start(androidLauncher);
//		revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
//		listener = new RevMobAdsListener() {
//
//	         @Override
//	         public void onRevMobAdClicked() {
//	            Log.i("[RevMob]", "Advertisement Clicked!");
//	            revmob.openAdLink(androidLauncher, this);
//	            //showInterstitialAds(false);
//	         }
//
//	         @Override
//	         public void onRevMobAdDismiss() {
//	            Log.i("[RevMob]", "Advertisement Closed!");
//	            fullscreenAd.hide();
//	         }
//
//	         @Override
//	         public void onRevMobAdDisplayed() {
//	            Log.i("[RevMob]", "Advertisement Displayed!");
//	         }
//
//	         @Override
//	         public void onRevMobAdNotReceived(String message) {
//	            Log.i("[RevMob]", "No Advertisement Available!");
//	         }
//
//	         @Override
//	         public void onRevMobAdReceived() {
//	            Log.i("[RevMob]", "Advertisement Pulled from network!");
//	         }
//
//	         @Override
//	         public void onRevMobSessionIsStarted() {}
//	         @Override
//	         public void onRevMobSessionNotStarted(String arg0) {}
//
//			@Override
//			public void onRevMobEulaIsShown() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onRevMobEulaWasAcceptedAndDismissed() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onRevMobEulaWasRejected() {
//				// TODO Auto-generated method stub
//				
//			}
//	    };
		fullscreenAd = revmob.createFullscreen(androidLauncher, null);
		bannerAd = revmob.createBanner(androidLauncher);
		linkAd = revmob.createAdLink(androidLauncher, null);
	}
	
	@Override
	public void showFullscreenAd(boolean show) {
		if(show) {
	         if(fullscreenAd == null) {
	            fullscreenAd = revmob.createFullscreen(androidLauncher, listener);
	            fullscreenAd.show();
	         } else {
	            fullscreenAd.show();
	         }
	      } else {
	         fullscreenAd.hide();
	      }
	}
	
	
//	private boolean bannerVisible = false;
	
	@Override
	public void showBannerAd(boolean show) {
		androidLauncher.showBanner(show);
//		 if(show) {
//			 if(bannerAd == null) {
//	            bannerAd = revmob.createBanner(androidLauncher);
//	         }
//			 handler.sendEmptyMessage(View.VISIBLE);
//		 } else {
//			 handler.sendEmptyMessage(View.INVISIBLE);
//		 }
	}
	@Override
	public void openLinkAd(boolean show) {
		if(show) {
			if(linkAd == null)
				linkAd = revmob.createAdLink(androidLauncher, null);
			linkAd.open();
			show = false;
		}
		
	}
	@Override
	public void loadLink() {
		linkAd = revmob.createAdLink(androidLauncher, null);
	}
	
	public RevMobBanner getBannerAd() {
		if(bannerAd == null) {
			bannerAd = revmob.createBanner(androidLauncher);
	    }
		return bannerAd;
	}

}
