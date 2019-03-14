package com.evgen.scraper;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evgen.dao.ItemDao;
import com.evgen.dao.ItemDaoimpl;
import com.evgen.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebScraper {

    private static Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~start scrape~~~~~~~~~~~~~~~~~~");
        ItemDao itemDao = new ItemDaoimpl();

        String searchQuery = "iphone 6s";
        String baseUrl = "https://newyork.craigslist.org/";

        WebClient client = new WebClient();

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = baseUrl + "search/sss?sort=rel&query="
                    + URLEncoder.encode(searchQuery, "UTF-8");
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> items = page
                    .getByXPath("//li[@class='result-row']");
            if (items.isEmpty()) {
                LOGGER.trace("No items found !");
            } else {
                for (HtmlElement htmlItem : items) {
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem
                            .getFirstByXPath(".//p[@class='result-info']/a"));
                    HtmlElement spanPrice = ((HtmlElement) htmlItem
                            .getFirstByXPath(
                                    ".//a/span[@class='result-price']"));

                    // It is possible that an item doesn't have any price, we
                    // set the price to 0.0 in this case
                    String itemPrice = spanPrice == null ? "0.0"
                            : spanPrice.asText();

                    Item item = new Item();
                    item.setTitle(itemAnchor.asText());
                    item.setUrl(baseUrl + itemAnchor.getHrefAttribute());
                    item.setPrice(new BigDecimal(itemPrice.replace("$", "")));

                    itemDao.saveItem(item);
                    LOGGER.trace("Item is saved successfully !");

                    /*
                     * ObjectMapper mapper = new ObjectMapper();
                     * String jsonString = mapper.writeValueAsString(item) ;
                     */

                    // System.out.println(jsonString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
