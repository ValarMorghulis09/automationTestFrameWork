package PageObjects;

import General.Action;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import javax.xml.xpath.XPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static General.Action.extractTextFromWebElement;
import static General.Action.webDriver;

public class HotelsPage extends Page{


    private final String SEARCHBUTTONID="searchform";
    HotelsPage(){
        super( "https://www.phptravels.net/hotels" ) ;
        PageFactory.initElements(webDriver, this);
    }

    ///Web Elements
    @FindAll(@FindBy(how= How.XPATH , using  = "//div[@class='rating']//input//parent::div"))
    List<WebElement> ratingRadioBtns;

    @FindAll(@FindBy(how= How.XPATH , using  = "//table[@class='bgwhite table table-striped']//div[@class='row']//span[@class='go-right mob-fs10']"))
    List<WebElement> ratingFilterResults;

    @FindAll(@FindBy(how = How.XPATH, using = "//div[@class='text-success fs18 text-left go-text-right go-right review ']/child::b"))
    List<WebElement> pricesResults;

    @FindAll(@FindBy(how = How.XPATH, using = "//div[@id='collapse4']//ins[@class='iCheck-helper']"))
    List<WebElement> amentiesCheckboxes;

    @FindAll(@FindBy(how = How.XPATH, using = "//div[@id='collapse4']//label"))
    List<WebElement> amentiesLabels;




    @FindBy(how = How.XPATH, using = "id('"+SEARCHBUTTONID+"')")
    protected WebElement searchButton;


    @FindBy(how = How.XPATH, using = "//div[@class='slider-track']/child::div[2]")
    protected WebElement sliderStart;

    @FindBy(how = How.XPATH, using = "//div[@class='slider-track']/child::div[3]")
    protected WebElement sliderEnd;

    @FindBy(how = How.XPATH, using = "//div[@class='slider-track']")
    protected WebElement slider;

    @FindBy(how = How.XPATH, using = "//div[@class='slider slider-horizontal']/child::input")
    protected WebElement sliderInput;



    public void clickOnRatingRadioButton(int stars){
        Action.mouseClick(ratingRadioBtns.get(stars-1));
    }

    public void clickOnSearchButton(){
        System.out.println("i am here man");
        Action.mouseClick(searchButton);
    }
    public boolean isFilterResultsCorrect(String chosenRating)
    {
        for(WebElement w :ratingFilterResults){
            List<WebElement> stars=w.findElements(By.className("icon-star-5"));
            System.out.println(stars.size());
            if(stars.size()!=Integer.parseInt(chosenRating))
                return false;
        }
        return true;
    }
    public void selectSliderPriceRange(int minReqPrice,int maxReqPrice)
    {
        int minPrice = Integer.parseInt(sliderInput.getAttribute("data-slider-min").replace(" ","")) ;
        int maxPrice = Integer.parseInt(sliderInput.getAttribute("data-slider-max").replace(" ","")) ;
        double Range=maxPrice-minPrice;
        double relativeMin=(minReqPrice-minPrice)/Range*100;
        double relativeMax=(maxReqPrice-minPrice)/Range*100;
        Action.selectSliderValues(relativeMin,relativeMax,sliderStart,sliderEnd, slider);
    }

    public int extractNumber (String text) {
        String output = "" ;
        for ( int i =0  ; i < text.length() ; i ++ ){
            if((int)text.charAt(i)>=48 &&(int)text.charAt(i)<=57 )
                output += text.charAt(i) ;
        }
        if(output=="")
        {
            output="-1";
        }
        return Integer.parseInt(output) ;
    }

    public  boolean checkPriceInRange (int minReqPrice , int maxReqPrice){

        for(WebElement w :pricesResults){
            int shownPrice=extractNumber(extractTextFromWebElement(w));
            System.out.println(shownPrice);
            if(shownPrice < minReqPrice || shownPrice > maxReqPrice)
                return false ;

        }
        return true ;
    }

    public void chooseAmenties(String [] requiredAmenities) {
        for(int i=0;i<requiredAmenities.length;i++)
        {
            System.out.println(String.valueOf(i) + " iteration " + amentiesLabels.size() + "  & " + amentiesCheckboxes.size());
            for( int j=0  ; j < amentiesLabels.size() ; j++)
            {
                if(requiredAmenities[i].equals(amentiesLabels.get(j).getText()))
                {
                    Action.mouseClick(amentiesCheckboxes.get(j));
                }
            }



        }
    }



}
