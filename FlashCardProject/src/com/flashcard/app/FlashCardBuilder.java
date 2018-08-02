package com.flashcard.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardBuilder {

	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	private JFrame jFrame;
	
	public FlashCardBuilder() {
		//Build User interface
		jFrame = new JFrame("Flash Card");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a panel to hold everything
		JPanel mainPanel = new JPanel();
		
		//Create font
		Font greatFont = new Font("Helvetica Neue",Font.BOLD, 21 );
		
		//Create TextArea
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(greatFont);
		
		//JScrollPane QuestionArea
		JScrollPane qJScrollPane = new JScrollPane(question);
		qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
		//Create TextArea
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(greatFont);
				
		
		//JScrollPane AnswerArea
		JScrollPane aJScrollPane = new JScrollPane(answer);
		aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Button
		JButton nextButton = new JButton("Next Card");
		
		cardList = new ArrayList<FlashCard>();
		System.out.println("Size of ArrayList" + cardList.size());
		
		//Create few Labels
		JLabel qJLabel = new JLabel("Question");
		JLabel aJLabel = new JLabel("Answer");
		
			
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		//Add eventListenets
		nextButton.addActionListener(new NextCardListener());
		newMenuItem.addActionListener(new NewMenuItemListener());
		saveMenuItem.addActionListener(new SaveMenuItemListener());
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		
		
		
		//Add Components to mainPanel
		mainPanel.add(qJLabel);
		mainPanel.add(qJScrollPane);
		mainPanel.add(aJLabel);
		mainPanel.add(aJScrollPane);
		mainPanel.add(nextButton);
		
		//Add to the frame
		
		
		jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		jFrame.setSize(480, 600);
		jFrame.setVisible(true);
		jFrame.setJMenuBar(menuBar);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FlashCardBuilder();
				
			}
			
		});

	}



	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Create a FlastCard
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	
	}

	class NewMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("New Menu clicked");
		
		}
	
	}

	class SaveMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			
			//Create a file dialog with file chooser
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(jFrame);
			saveFile(fileSave.getSelectedFile());
		}

		
	}
	
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
		
	}
	private void saveFile(File selectedFile) {
		 try {
			 
			 BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			 
			 Iterator<FlashCard> cardIterator = cardList.iterator();
			 while(cardIterator.hasNext()) {
				 FlashCard card = cardIterator.next();
				 writer.write(card.getQuestion() + "/");
				 writer.write(card.getAnswer() + "\r\n");
			 }
			 writer.close();
		 }catch(Exception e) {
			 System.out.println("Couldn't write file");
			 e.printStackTrace();
		 }
		
	}
}