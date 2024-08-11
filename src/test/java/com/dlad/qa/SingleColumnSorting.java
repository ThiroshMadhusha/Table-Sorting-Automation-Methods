package com.dlad.qa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SingleColumnSorting {

	WebDriver driver;
	
    @Test
    public void verifySingleColumnSorting() throws InterruptedException {
        // Click on the "ID" column header to sort
        driver.findElement(By.xpath("//button[contains(text(),'ID')]")).click();

        Thread.sleep(3000);
        // Extract the table data
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='tbl']/tbody/tr"));
        List<String> originalIDs = new ArrayList<>();

        for (WebElement row : rows) {
            String idText = row.findElement(By.xpath(".//td[1]")).getText();
            originalIDs.add(idText);
        }

        // Print original table data
        for (String id : originalIDs) {
            System.out.println(id);
        }

        // Create a copy of the list to sort and compare
        List<String> sortedIDs = new ArrayList<>(originalIDs);

        // Custom comparator to handle IDs with prefix
        Collections.sort(sortedIDs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int id1 = Integer.parseInt(o1.replaceAll("\\D", ""));
                int id2 = Integer.parseInt(o2.replaceAll("\\D", ""));
                return Integer.compare(id1, id2);
            }
        });

        // Verify the sorting
        Assert.assertEquals(originalIDs, sortedIDs, "The table is not sorted in ascending order by ID.");
    }

}
