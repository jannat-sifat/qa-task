package selenium;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PTest {
	
	WebDriver driver;
	
	@Before
    public void startBrowser() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anik\\myfolder\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	
    }
	
	
	
	public void postStatement(String accountId, String amount, String currency, Date date) throws IOException {
		
		Statement statement = new Statement(accountId, amount, currency, date.getTime()/1000);
		StatementCreateBody body = new StatementCreateBody(statement);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(body));
		String jsonInputString =gson.toJson(body);
		
		
		URL url = new URL ("http://127.0.0.1:9999/statements");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    os.write(input, 0, input.length);			
		}
		
		System.out.println(con.getResponseCode());
		
		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
				}

	}
	
	public static String readFileAsString(String fileName)throws Exception { 
	    String data = ""; 
	    data = new String(Files.readAllBytes(Paths.get(fileName))); 
	    return data; 
	} 
	
	
	@Test
	public void t() throws Exception {
		
		String data = readFileAsString("src/test/resources/data.json");
		List<Data> list = Arrays.asList(new GsonBuilder().create().fromJson(data, Data[].class));
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Double balanceBefore = 0.0;
		Double balanceAfter = 0.0;
		
		for (Data d: list) {
			
			postStatement(d.getAccountId(), d.getAmount(), "EUR", formatter.parse(d.getDate()));
			
			balanceAfter += Double.parseDouble(d.getAmount());
			
			
			driver.get("http://127.0.0.1:9999/statements");
			
			String s = driver.findElement(By.xpath("/html/body/div[1]/div[1]")).getText();
			
			String bBefore = s.split("Balance before:")[1].split("EUR")[0];
			
			WebElement s1 = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div"));
			String last = s1.getText().split("Balance after ")[1];
			String bAfter = last.split("EUR")[0].trim();
			
			Double actualBalanceBefore = Double.parseDouble(bBefore);
			assertEquals(balanceBefore, actualBalanceBefore);
			
			Double actualBalanceAfter = Double.parseDouble(bAfter);
			assertEquals(balanceAfter, actualBalanceAfter);
			
			driver.close();
			

		}
		
	}
	
	

	
	@After
    public void tearDown() {
        
    }
}