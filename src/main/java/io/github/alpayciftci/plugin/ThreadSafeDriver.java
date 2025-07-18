package io.github.alpayciftci.plugin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeDriver {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ConcurrentHashMap<String, WebDriver> driverPool = new ConcurrentHashMap<>();

    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            driver.set(createDriver(browser));
            driverPool.put(Thread.currentThread().getName(), driver.get());
        }
        return driver.get();
    }

    private static WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            case "safari" -> new SafariDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}