package practice;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import util.Card;

public class ShowCardFrame extends JFrame{
	
	public static final long serialVersionUID = 998564736654354543L;
	
	private int sidesSeen = 0;
	private DisplayArea displayArea;
	private Card cardDisplayed;
	
	public ShowCardFrame(Card card){
		setLayout(new BorderLayout());
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cardDisplayed = card;
		displayArea = new DisplayArea(card.getFirstSide());
		add(displayArea, BorderLayout.CENTER);
		add(getNextButton(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private JButton getNextButton(){
		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sidesSeen++;
				if(sidesSeen==1){
					displayArea.show(cardDisplayed.getSecondSide());
				}else{
					new AnswerFrame();//TODO: make this better
					dispose();
				}
			}
		});
		return button;
	}
}
