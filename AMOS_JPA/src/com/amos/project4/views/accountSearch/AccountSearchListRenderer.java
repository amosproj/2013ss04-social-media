package com.amos.project4.views.accountSearch;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.amos.project4.socialMedia.AccountSearchResultItem;

public class AccountSearchListRenderer<T extends AccountSearchResultItem> implements ListCellRenderer<T> {
	
	protected DefaultListCellRenderer defaultRenderer;
	private Image default_img;

	public AccountSearchListRenderer() {
		super();
		defaultRenderer = new DefaultListCellRenderer();
		try {
			Image imgFondo = javax.imageio.ImageIO.read(new URL("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg"));
			default_img = imgFondo.getScaledInstance(50, 50, Image.SCALE_FAST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list,
			T value, int index, boolean isSelected, boolean cellHasFocus) {

		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
		
		if (value instanceof AccountSearchResultItem) {
			AccountSearchResultItem item = (AccountSearchResultItem) value;
			renderer.setText("<html><b>" + item.getTitle1() + "</b> <br/><i>"+ item.getTitle2() + "</i></html>");
			
			try{
				URL i_url = new URL(item.getPictureURL());
				Image imgFondo = javax.imageio.ImageIO.read(i_url);
				imgFondo = imgFondo.getScaledInstance(50, 50, Image.SCALE_FAST);
				renderer.setIcon(new ImageIcon(imgFondo));
			}catch(Exception e){
				renderer.setIcon(new ImageIcon(default_img));
			}
			
			
			if (isSelected) {
				renderer.setBackground(list.getSelectionBackground());
				renderer.setForeground(list.getSelectionForeground());
			} else {
				renderer.setBackground(list.getBackground());
				renderer.setForeground(list.getForeground());
			}
		}
		return renderer;
	}
}