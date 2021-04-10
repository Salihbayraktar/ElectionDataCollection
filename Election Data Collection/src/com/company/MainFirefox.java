package com.company;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.util.Scanner;

public class MainFirefox {

    private static FirefoxProfile profile;
    private static FirefoxOptions firefoxOptions;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static int electionLimit, electionTypeLimit, cityLimit, districtLimit, lastElectionNumber, lastElectionTypeNumber, lastCityNumber, lastDistrictNumber;

    public static void setUp() {
        profile = new FirefoxProfile();
        firefoxOptions = new FirefoxOptions();
        // profile.setPreference("browser.download.useDownloadDir", true); This is true by default. Add it if it's not working without it.
        profile.setPreference("browser.download.folderList",2); //Use for the default download directory the last folder specified for a download
        profile.setPreference("browser.download.dir", "C:\\Users\\Salih\\Desktop\\Secim Sonuclari"); //Set the last directory used for saving a file from the "What should (browser) do with this file?" dialog.
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-excel, application/x-msexcel, application/excel, application/vnd.ms-excel");
        firefoxOptions.setProfile(profile);
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Salih\\Desktop\\Secim Verileri Toplama\\libraries\\geckodriver.exe");
        driver = new FirefoxDriver(firefoxOptions);
        wait = new WebDriverWait(driver,10);
    }

    public static void openQuery() throws InterruptedException {
        // Web sayfasını açıyoruz
        driver.get("https://sonuc.ysk.gov.tr/sorgu");
        // Seçim adı ile sorgulaya tıklıyoruz.
        ///html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-secim/div/div/div[2]/div/div/div[3]/button/div/div/div/div[1]/span
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-secim/div/div/div[2]/div/div/div[3]/button/div/div/div/div[1]/span")).click();
    }

    public static void selectElection(int i) throws InterruptedException {
        // Seçim adına ait listeyi açıyoruz.
        ///html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div/div/ng-select/div/span
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("[formcontrolname=secim]")).click();
        // Listeden sonucunu indirmek istediğimiz seçim adını seçiyoruz. //div[2]/div[degisken]/div   degisken olan kısımdan istenilen seçim seçilir.
        //driver.findElement(By.xpath("//div[2]/div[1]/div")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div/div/ng-select/ng-dropdown-panel/div/div[2]/div["+ String.valueOf(i) +"]/div")).click();
    }

    public static int getElectionNumber() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div/div/ng-select/div/span")).click();
        int i =  driver.findElements(By.className("ng-option")).size();
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div/div/ng-select/div/span")).click();
        return i;
    }

    public static void selectElectionType(int i) throws InterruptedException {
        // Seçim türüne ait listeyi açıyoruz.
        ///html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/div/span
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("[formcontrolname=secimTuru]")).click();
        // Listeden sonucunu indirmek istediğimiz seçim türünü seçiyoruz.
        ///html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/ng-dropdown-panel/div/div[2]/div[degisken]/span
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/ng-dropdown-panel/div/div[2]/div[" + String.valueOf(i) + "]/span")).click();
    }

    public static boolean isElectionTypeAvailable() throws InterruptedException {
        try {
            driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/div/span")).isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public static int getElectionTypeNumber() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/div/span")).click();
        int i = driver.findElements(By.xpath("//div[2]/div/span")).size();
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sorgu/div/div/div[2]/div[1]/div/div/form/div[1]/div[2]/div/ng-select/div/span")).click();
        return i;
    }

    public static void selectResultType() throws InterruptedException {
        // Sonuç türünü seçiyoruz. //div[degisken]/label/input  degisken = 1 Yurtiçi
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("[value='1']")).click();
        Thread.sleep(200);
        // Devam et butonuna tıklıyoruz.
        driver.findElement(By.xpath("//div[2]/div/div/button")).click();
    }

    public static int getResultTypeNumber() throws InterruptedException {
        return driver.findElements(By.cssSelector("[formcontrolname=sandikTuru]")).size();
    }

    public static void selectCity(int i) throws InterruptedException {
        // İl'e ait listeyi açıyoruz.
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("[formcontrolname=il]")).click();
        // İl'e ait listeden eleman seçiyoruz  //ng-dropdown-panel/div/div[2]/div[1]     div[1] ilk ili yani adanayı gösteriyor.
        Thread.sleep(200);
        driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div["+ String.valueOf(i) +"]")).click();
    }

    public static int getCityNumber() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sonuc/div/div/div[2]/div[1]/div/div/fieldset/form/div[1]/div[2]/div[1]/ng-select/div/span")).click();
        int i = driver.findElements(By.cssSelector("[role=option]")).size();
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sonuc/div/div/div[2]/div[1]/div/div/fieldset/form/div[1]/div[2]/div[1]/ng-select/div/span")).click();
        return i;
    }

    public static void selectDistrict(int i) throws InterruptedException {
        // İlçe'ye ait listeyi açıyoruz.
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("[formcontrolname=secimCevresi]")).click();
        // İlçe'ye ait listeden eleman seçiyoruz  //ng-dropdown-panel/div/div[2]/div[1]     div[1] ilk ilçeyi gösteriyor.
        Thread.sleep(200);
        driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div["+ String.valueOf(i) +"]")).click();
    }

    public static int getDistrictNumber() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sonuc/div/div/div[2]/div[1]/div/div/fieldset/form/div[1]/div[2]/div[2]/ng-select/div/span")).click();
        int i = driver.findElements(By.cssSelector("[role=option]")).size();
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sonuc/div/div/div[2]/div[1]/div/div/fieldset/form/div[1]/div[2]/div[2]/ng-select/div/span")).click();
        return i;
    }

    public static void download() throws InterruptedException {
        // Sorgula butonuna basıyoruz.
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/app-root/app-vatandas/div[2]/app-vatandas-asistan-arsiv-sonuc/div/div/div[2]/div[1]/div/div/fieldset/form/div[2]/div/div/button/span")).click();
        // İndirme butonuna basıyoruz.
        Thread.sleep(3000);
        driver.findElement(By.xpath("//fieldset/div[2]/div/button")).click();
        // İndirme şartını kabul et butonuna basıyoruz.
        Thread.sleep(200);
        driver.findElement(By.xpath("//ngb-modal-window/div/div/div[2]/div/button")).click();
        Thread.sleep(2000);
    }

    public static void save(int lastElectionNumber, int lastElectionTypeNumber, int lastCityNumber, int lastDistrictNumber) {
        FileWriter save = null;    // try ve cacth in içinde açsaydık finally de kullanamayacağımız için burada oluşturup null değerini verdik.
        String data = String.valueOf(lastElectionNumber)+ "," + String.valueOf(lastElectionTypeNumber)+ "," + String.valueOf(lastCityNumber) + "," + String.valueOf(lastDistrictNumber);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt", false))) {            //Buffered Writer tanımlanması
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] load(){

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("save.txt")))) {             //Buffered Reader tanımlanması
                String data = scanner.nextLine();
                String[] array = data.split(",");
                int[] values = new int[array.length];
                for(int i = 0 ; i< array.length ; i++) {
                   values[i] = Integer.valueOf(array[i]);
                }
                return values;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void checkLoad() {
        if(load()!=null){
            int[] load = load();
            lastElectionNumber = load[0];
            lastElectionTypeNumber = load[1];
            lastCityNumber = load[2];
            lastDistrictNumber = load[3];
        }
        else {
            lastElectionNumber = 3;
            lastElectionTypeNumber = 1;
            lastCityNumber = 1;
            lastDistrictNumber = 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        checkLoad();
        setUp();
        openQuery();
        electionLimit = getElectionNumber();
        for(int h = lastElectionNumber ; h <= electionLimit ; h++) {
        selectElection(h);
        if(isElectionTypeAvailable()) {
            electionTypeLimit = getElectionTypeNumber();
            for(int i = lastElectionTypeNumber ; i <= electionTypeLimit ; i++) {
               selectElectionType(i);
               selectResultType();
               cityLimit = getCityNumber();
               for(int j = lastCityNumber ; j <= cityLimit ; j++)
               {
                   selectCity(j);
                   districtLimit = getDistrictNumber();
                   System.out.println("Son Şehir : " + j);
                   for(int k = lastDistrictNumber ; k <= districtLimit ; k++)
                   {
                       System.out.println("Son ilçe : " + k);
                       selectDistrict(k);
                       download();
                       save(h,i,j,k);
                   }
                   lastDistrictNumber = 1;
               }
               lastCityNumber = 1;
               driver.quit();
               setUp();
               openQuery();
               selectElection(h);
            }
            lastElectionTypeNumber = 1;
            driver.quit();
            setUp();
            openQuery();
        }
        else {
            selectResultType();
            cityLimit = getCityNumber();
            for(int j = lastCityNumber ; j <= cityLimit ; j++)
            {
                selectCity(j);
                districtLimit = getDistrictNumber();

                for(int k = lastDistrictNumber ; k <= districtLimit ; k++)
                {
                    selectDistrict(k);
                    download();
                    save(h,1,j,k);
                }
                lastDistrictNumber = 1;
            }
            lastCityNumber = 1;
            driver.quit();
            setUp();
            openQuery();
            selectElection(h);
        }
            lastElectionTypeNumber = 1;
            driver.quit();
            setUp();
            openQuery();
        }
    }
}