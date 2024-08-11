package com.dlad.qa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultipleColumnSorting {

	WebDriver driver;
	
	@Test(priority = 14, dataProvider = "verifyColumnSorting")
    public void verifyTableColumnSorting(final String columnName, String columnIndex) throws InterruptedException {
        // Click on the column header to sort
        driver.findElement(By.xpath("//button[contains(text(),'" + columnName + "')]")).click();

        Thread.sleep(3000);
        // Extract the table data
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='tbl']/tbody/tr"));
        List<String> originalValues = new ArrayList<>();

        for (WebElement row : rows) {
            String cellText = row.findElement(By.xpath(".//td[" + columnIndex + "]")).getText();
            originalValues.add(cellText);
        }

        // Create a copy of the list to sort and compare
        List<String> sortedValues = new ArrayList<>(originalValues);

        // Custom comparator to handle sorting based on the column type
        Collections.sort(sortedValues, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (columnName.equals("ID")) {
                    int id1 = Integer.parseInt(o1.replaceAll("\\D", ""));
                    int id2 = Integer.parseInt(o2.replaceAll("\\D", ""));
                    return Integer.compare(id1, id2);
                } else {
                    return o1.compareTo(o2);
                }
            }
        });

        // Print sorted table data
        System.out.println("Sorted " + columnName + " Data:");
        for (String value : sortedValues) {
            System.out.println(value);
        }

        // Verify the sorting
        Assert.assertEquals(originalValues, sortedValues, "The table is not sorted in ascending order by " + columnName + ".");
    }
    
    
	@DataProvider(name = "verifyColumnSorting")
	public Object[][] verifyColumnSorting() {
		String[] columnName = { "ID", "Lead Name", "Status", "Rating", "Email" };
		String[] columnIndex = { "1", "2", "3", "4", "5"};

		Object[][] data = new Object[columnName.length][2];

		for (int i = 0; i < columnName.length; i++) {
			data[i][0] = columnName[i];
			data[i][1] = columnIndex[i];
		}

		return data;
	}

}
