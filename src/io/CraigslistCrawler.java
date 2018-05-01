package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.IOException;
import java.util.List;

import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CraigslistCrawler {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36";
    private static String proxy_file = "./proxylist_bittiger.csv";

    private static final String authUser = "bittiger";
    private static final String authPassword = "cs504";
    private static List<String> proxyList;
    private static List<String> titleList;
    private static List<String> rentPriceList;
    private static List<String> detailUrlList;
    private static List<String> hoodList;

    private static HashSet crawledUrl;
    static BufferedWriter logBFWriter;
    private static int index = 0;

    public static void main(String[] args) throws IOException {
        CraigslistCrawler.Crawl("https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa");
    }

    public static void Crawl(String url) throws IOException {
        crawledUrl = new HashSet();

        initProxyList(proxy_file);
        setProxy();

        // Start crawling
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Language", "en-US,en;q=0.8");
        Document doc = Jsoup.connect(url).headers(headers).userAgent(USER_AGENT).timeout(100000).get();

        /*
            Extract title, rent_price, detail_url, and hood
         */
        titleList = new ArrayList<String>();
        rentPriceList = new ArrayList<String>();
        detailUrlList = new ArrayList<String>();
        hoodList = new ArrayList<String>();

        Elements resultLinks = doc.select("li[data-pid]");
        for(int i = 0; i < resultLinks.size(); i++) {

            Elements titleLinks = resultLinks.get(i).select("p[class] > a[href]");
            for(Element link : titleLinks) {
                // title
                titleList.add(link.text());

                // detail_url
                detailUrlList.add(link.attr("href"));
            }

            // rent_price
            Elements priceLinks = resultLinks.get(i).select("p[class] > span > span.result-price");
            for(Element link : priceLinks) {
                rentPriceList.add(link.text());
            }

            Elements hoodLinks = resultLinks.get(i).select("p[class] > span > span.result-hood");
            if(hoodLinks == null) {
                hoodList.add(" ");
            } else {
                hoodList.add(hoodLinks.text());
            }

        }

        for(int i = 0; i < titleList.size(); i++) {
            System.out.println("===========  " + Integer.toString(i+1) + ":");
            System.out.println(titleList.get(i));
            System.out.println(detailUrlList.get(i));
            System.out.println(rentPriceList.get(i));
            System.out.println(hoodList.get(i));
            System.out.println("\n");
        }


    }

    /*
        Proxy initialized and setting
     */
    private static void initProxyList(String proxy_file) {
        proxyList = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(proxy_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String ip = fields[0].trim();
                proxyList.add(ip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Authenticator.setDefault(
                new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                authUser, authPassword.toCharArray());
                    }
                }
        );

        System.setProperty("http.proxyUser", authUser);
        System.setProperty("http.proxyPassword", authPassword);
        System.setProperty("socksProxyPort", "61336"); // set proxy port
    }

    private static void setProxy() {
        //rotate, round robbin
        if (index == proxyList.size()) {
            index = 0;
        }
        String proxy = proxyList.get(index);
        System.setProperty("socksProxyHost", proxy); // set proxy server
        index++;
    }

}
