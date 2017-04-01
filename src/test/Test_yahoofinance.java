
package test;

import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Test_yahoofinance {
        public static void main(String[] args) throws IOException{
        	Stock stock = YahooFinance.get("TSLA");
        	//if(stock.getName().equalsIgnoreCase("N/A")){System.out.println("c'est comme ca");}
        	stock.print();
        }
}