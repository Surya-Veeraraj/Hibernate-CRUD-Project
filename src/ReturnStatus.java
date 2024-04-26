
public class ReturnStatus {
	private boolean returnStatus = false;
	private Object returnObject;

	public ReturnStatus(boolean returnStatus, Object returnObject) {
		this.returnStatus = returnStatus;
		this.returnObject = returnObject;
	}

	public boolean isReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(boolean returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
}
