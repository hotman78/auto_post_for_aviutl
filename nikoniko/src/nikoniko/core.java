package nikoniko;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
public class core {
    public static void main(String[] args) {

        // ブラウザ(Firefox)を起動
        WebDriver driver = new FirefoxDriver();

        // URLを開く
        driver.get("http://www.upload.nicovideo.jp/upload");

        // アカウントを取得
        WebElement mail = driver.findElement(By.id("input__mailtel"));
        WebElement password = driver.findElement(By.id("input__password"));
        String account[]=readAccount().split(":");
        mail.sendKeys(account[0]);
        password.sendKeys(account[1]);
        // submit
        password.submit();
        //アップロード
        WebElement upload = driver.findElement(By.id("upload"));
        String movie=readmovie();
        upload.sendKeys(movie);
        WebElement send = driver.findElement(By.name("submitbtn"));
        send.submit();
        alertOk(driver);
        //WebElement  movie_title= driver.findElement(By.id("movie_title"));
        // ブラウザを閉じる
        //driver.quit();
    }
   /* private static void sleep(int microtime) {
        try {
            Thread.sleep(microtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    public static void alertOk(WebDriver driver){
    	Wait<WebDriver> wait = new WebDriverWait(driver,10);
    	Alert alert = wait.until(alertIsPresent());

    	//アラートダイアログ操作
    	alert = driver.switchTo().alert();
    	alert.accept();//ダイアログ「OK」ボタン押下
    }
    public static String readmovie(){
    	try{
    		File file = new File("movie_path.txt");
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		String path = br.readLine();
    		br.close();
    		return(path);
    	}catch(FileNotFoundException e){
    		System.out.println(e);
    		return("error");
    	}catch(IOException e){
    		System.out.println(e);
    		return("error");
    	}
    }
    public static String readAccount(){
    try{
    	File file = new File("account.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));

    	String mail = br.readLine();
    	String password = br.readLine();
    	  br.close();
    	  return(mail+":"+password);
    	}catch(FileNotFoundException e){
    		System.out.println(e);
    	    return("error");
    	}catch(IOException e){
    	  System.out.println(e);
    	  return("error");
    	}
    }
}
