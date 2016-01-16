package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class CellRenderer extends DefaultListCellRenderer{
	
	public static final long serialVersionUID = 994886785762114365L;
	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if(index%2 == 0){
			component.setBackground(new Color(240, 240, 240));
		}else{
			component.setBackground(Color.white);
		}
		return component;
	}

}
