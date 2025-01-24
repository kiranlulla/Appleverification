package mu.utility.service.impl;

public class PollArray {
	@Override
	public String toString() {
		return "PollArray [count=" + count + ", sourceIp=" + sourceIp + "]";
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	private int count = 0;
	private String sourceIp ;
}
