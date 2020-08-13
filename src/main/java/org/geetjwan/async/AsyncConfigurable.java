package org.geetjwan.async;


import javax.management.timer.Timer;

import org.springframework.stereotype.Component;

@Component
public class AsyncConfigurable {

	private int maxRetryForFailed = 10;

	private int maxRetryForOnHold = 40;

	private int delayForResendingOnHeld = (int)(10L * Timer.ONE_SECOND);

	private int delayForResendingWait =  (int)(5L * Timer.ONE_MINUTE);

	private int delayForResendingFailed = (int)(Timer.ONE_MINUTE / 1000); // 1 mins

	private int maxWaitingTime = (int) Timer.ONE_DAY; // 24 hours


	public  int getMaxRetryForFailed() {
		return maxRetryForFailed;
	}

	public  void setMaxRetryForFailed(int maxRetryForFailed) {
		this.maxRetryForFailed = maxRetryForFailed;
	}

	public  int getDelayForResendingOnHeld() {
		return delayForResendingOnHeld;
	}

	public  void setDelayForResendingOnHeld(int delayForResendingOnHeld) {
		this.delayForResendingOnHeld = delayForResendingOnHeld;
	}

	public  long getMaxRetryForOnHold() {
		return maxRetryForOnHold;
	}

	public void setMaxRetryForOnHold(int maxRetryForOnHold) {
		this.maxRetryForOnHold = maxRetryForOnHold;
	}

	public  int getDelayForResendingWait() {
		return delayForResendingWait;
	}

	public  void setDelayForResendingWait(int delayForResendingWait) {
		this.delayForResendingWait = delayForResendingWait;
	}

	public  int getMaxWaitingTime() {
		return maxWaitingTime;
	}

	public  void setMaxWaitingTime(int maxWaitingTime) {
		this.maxWaitingTime = maxWaitingTime;
	}

	public int getDelayForResendingFailed() {
		return delayForResendingFailed;
	}

	public void setDelayForResendingFailed(int delayForResendingFailed) {
		this.delayForResendingFailed = delayForResendingFailed;
	}


}
