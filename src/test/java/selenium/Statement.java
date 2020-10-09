package selenium;

import java.sql.Timestamp;

import com.google.gson.annotations.SerializedName;

public class Statement {
	
	@SerializedName(value = "account_id")
	private String accountId;
	@SerializedName(value = "amount")
	private String amount;
	@SerializedName(value = "currency")
	private String currency;
	@SerializedName(value = "date")
	private long date;
	
	
	
	
	public Statement(String accountId, String amount, String currency, long date) {
		super();
		this.accountId = accountId;
		this.amount = amount;
		this.currency = currency;
		this.date = date;
	}
	
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	
}
