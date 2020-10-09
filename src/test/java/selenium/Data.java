package selenium;

import com.google.gson.annotations.SerializedName;

public class Data {
	
	@SerializedName(value = "account_id")
	private String accountId;
	private String amount;
	private String date;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
