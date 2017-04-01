
package test;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import projet_info_TEAM_CHAT.Action;
import projet_info_TEAM_CHAT.Entreprise;

public class Test_draw_graph {

	private Entreprise en;
	private JFrame jf=new JFrame();
	Test_draw_graph(Entreprise e){
		
		this.en=new Entreprise(e);
		
		DefaultCategoryDataset dataset = createDataset();
         JFreeChart chart = createChart(dataset);
         ChartPanel chartPanel = new ChartPanel(chart);
         chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		
         jf.add(chartPanel);
         jf.setSize(600, 400);
         jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jf.setVisible(true);
	}
	
	public DefaultCategoryDataset createDataset(){
		
		int s_quantite=0;
		double valeur=0;
		String date;
		String plot="Valeur";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		int i=0;
		Iterator it=this.en.getListAction().iterator();
		while(it.hasNext()){
			Action a=new Action((Action) it.next());
			s_quantite=s_quantite+a.getQuantite();
			valeur=a.getPrixActuel()*s_quantite;
			date=a.getDate();
			dataset.addValue(valeur,plot,date);
		}
		
		return dataset;
	}
	
	public JFreeChart createChart( DefaultCategoryDataset dataset){
		
		JFreeChart chart = ChartFactory.createLineChart(
	            this.en.getS().getName(),      // chart title
	            "Date",                      // x axis label
	            "Valeur",                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );
		chart.setBackgroundPaint(Color.white);
		
		/*
		Plot plot = chart.getPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        */
        
      //  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
	}
	
	
	public static void main(String[] args) throws IOException{
		Entreprise e=new Entreprise("INTC","fiche_entreprise/Alice/INTC.txt");
		new Test_draw_graph(e);
	}
	
}
