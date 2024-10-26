package processor.pipeline;

public class IF_EnableLatchType {

	boolean IF_enable;
	boolean isWaiting;
	boolean isStalled;
	boolean isBusy;

	public IF_EnableLatchType()
	{
		IF_enable = true;
		isWaiting = false;
		isStalled = false;
		isBusy = false;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setisWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public boolean isStalled() {
		return isStalled;
	}

	public void setIsStalled(boolean isStalled) {
		this.isStalled = isStalled;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setIsBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

}
