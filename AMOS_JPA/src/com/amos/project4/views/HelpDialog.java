package com.amos.project4.views;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.util.Observable;
import javax.swing.JList;
import javax.swing.JTextArea;

import org.w3c.tidy.Tidy;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HelpDialog extends JDialog implements AbstractControlledView,
		HyperlinkListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JEditorPane helpPane;
	private URL url;

	/**
	 * Create the application.
	 * 
	 * @param frame
	 */
	public HelpDialog(JFrame frame) {
		super();
		init();
		setLocationRelativeTo(frame);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		

		url = this.getClass().getResource("/com/amos/project4/doc/amos_help/index.html");
		try {
			helpPane = new JEditorPane(url);
			helpPane.setEditable(false);
			helpPane.addHyperlinkListener(this);
			JScrollPane scrollPane = new JScrollPane(helpPane);
			springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, getContentPane());
			getContentPane().add(scrollPane, BorderLayout.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension screenSize = getToolkit().getScreenSize();
		int width = screenSize.width * 8 / 10;
		int height = screenSize.height * 8 / 10;
		setBounds(width / 8, height / 8, width, height);
		setVisible(true);
	}
		
		public void actionPerformed(ActionEvent event) {
		try {
			helpPane.setPage(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		setTitle("AMOS Project 4 - Help");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//
		//

	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent event) {
		 if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		      try {
		        helpPane.setPage(event.getURL());
		      } catch(IOException ioe) {
		    	  
		      }
		      }

	}
}
