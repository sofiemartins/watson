package practice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import util.Card;

public class ShowCardFrame extends JFrame{
	
	public static final long serialVersionUID = 998564736654354543L;
	
	private int sidesSeen = 0;
	private DisplayArea displayArea;
	
	public ShowCardFrame(Card card){
		setLayout(new BorderLayout());
		displayArea = new DisplayArea(card.getFirstSide());
		add(displayArea, BorderLayout.CENTER);
		add(getNextButton(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	public static void showCard(Card card){
		ShowCardFrame frame = new ShowCardFrame(card);
		while(frame.sidesSeen==0){}
		frame.displayArea.show(card.getSecondSide());
		while(frame.sidesSeen==1){}
		frame.dispose();
	}
	
	private JButton getNextButton(){
		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sidesSeen++;
			}
		});
		return button;
	}
}
