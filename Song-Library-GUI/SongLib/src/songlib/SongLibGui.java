package songlib;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * 
 *@author Silvia Carbajal
 *@author Oskar Bero
 */

public class SongLibGui extends JFrame{
	JFrame songLib;
	static JTextField nameField;
	static JTextField artistField;
	static JTextField albumField;
	static JTextField yearField;
	JButton add,del,edit,ok;
	static JList<String> list;
	static DefaultListModel<String> listModel;
	
	public SongLibGui(String name) throws IOException {
		songLib=new JFrame(name);
 		
		JPanel listPanel, viewPanel,buttonPanel;
		
		//buttons and panels
 		ok = new JButton("confirm");
		add = new JButton("add");
		del = new JButton("delete");
		edit= new JButton("edit");	
 		listPanel = new JPanel();
		viewPanel = new JPanel();
		buttonPanel = new JPanel();
		
		//textfields
		nameField = new JTextField(null,25);
		artistField = new JTextField(null,25);
		albumField =  new JTextField(null,25);
		yearField = new JTextField(null,27);
		
		//labels
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		JLabel artistLabel = new JLabel("Artist");
		artistLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		JLabel albumLabel = new JLabel("Album");
		albumLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		JLabel yearLabel = new JLabel("Year");
		yearLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	
		//listeners
		addListener addListener = new addListener();
		editListener editListener = new editListener();
		delListener delListener = new delListener();
		okListener okListener = new okListener();
		ListListener ListListener = new ListListener();
				
		//adding labels to label panel
		JPanel labelPane = new JPanel();	
		labelPane.setLayout(new GridLayout(4,0));
		labelPane.add(nameLabel);
		labelPane.add(artistLabel);
		labelPane.add(albumLabel);
		labelPane.add(yearLabel);
		labelPane.setBorder(BorderFactory.createEmptyBorder(5,8,5,0));

		//adding textfields to field layout
		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new GridLayout(4,1));
		fieldPane.add(nameField);
		fieldPane.add(artistField);
		fieldPane.add(albumField);
		fieldPane.add(yearField);
		fieldPane.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		//adding label layout and textfield label to view panel
		viewPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		viewPanel.add(labelPane, BorderLayout.WEST);
		viewPanel.add(fieldPane);
		
		//adding listeners to buttons
		add.addActionListener(addListener);
		edit.addActionListener(editListener);
		del.addActionListener(delListener);
		ok.addActionListener(okListener);
		
		//adding buttons to button layout 
		buttonPanel.setLayout(new GridLayout(6,1));
 		buttonPanel.add(add);
		buttonPanel.add(del);
		buttonPanel.add(edit);
		buttonPanel.add(ok);
		
		//LIST layout and adding list to list Panel
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		listPanel.add(list);
	
		GridBagLayout gridbag= new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
     	listPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy= 0;
		c.weightx = .5 ; 
		c.weighty= .5;
		c.gridheight=4;
		c.gridwidth = 2;
		c.fill=GridBagConstraints.BOTH;
		gridbag.setConstraints(list, c);
		listPanel.add(list,c);
		JScrollPane listScrollPane = new JScrollPane(list);
		listPanel.add(listScrollPane,c);
		listPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		//adding all 3 panels to Jframe
		songLib.add(buttonPanel, BorderLayout.EAST); // add the button JPanel
		songLib.add(viewPanel, BorderLayout.SOUTH); // add the textfields JPanel
		songLib.add(listPanel); //add the list JPanel
		
		//setting frame components
		setFrame();	
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.addListSelectionListener(ListListener);
		list.setSelectedIndex(0);
		SongLib.getList(listModel);
	}
	
	boolean addOK , editOK;
	public class addListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			nameField.setText("");
			artistField.setText("");
			albumField.setText("");
			yearField.setText("");
			nameField.setEditable(true);
			artistField.setEditable(true);
			albumField.setEditable(true);
			yearField.setEditable(true);
			edit.setEnabled(false);
			del.setEnabled(false);
			ok.setEnabled(true);
			add.setEnabled(false);
			addOK=true;
		}
	}
	
	public class delListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!list.isSelectionEmpty())
			{
				edit.setEnabled(true);
				del.setEnabled(true);
				ok.setEnabled(false);
				add.setEnabled(true);
				Song deleteSong =SongLib.getSelectedSong();
				int i = getSelectedSongIndex();
				
				//outofbounds case
				int last = listModel.size() - 1;
				if(i == last) {
					setSelectedSong(i-1);
				} else {
					setSelectedSong(i-1);
				}
				
				SongLib.deleteSong(deleteSong, listModel);
				//clear all fields
				nameField.setText("");
				artistField.setText("");
				albumField.setText("");
				yearField.setText("");
			}
		
		}
	}
	
	public class editListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if(!list.isSelectionEmpty())
			{
				nameField.setEditable(true);
				yearField.setEditable(true);
				artistField.setEditable(true);
				albumField.setEditable(true);	
				edit.setEnabled(false);
				del.setEnabled(false);
				ok.setEnabled(true);
				add.setEnabled(false);
				editOK=true;
			}
		}
	}
	public class okListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if(!nameField.getText().equals("")&&!artistField.getText().equals(""))
			{
				String artist=artistField.getText().toString();
				String song=nameField.getText().toString();
				String year=yearField.getText();
				String album=albumField.getText();
				edit.setEnabled(true);
				del.setEnabled(true);
				ok.setEnabled(false);
				add.setEnabled(true);
				nameField.setEditable(false);
				yearField.setEditable(false);
				artistField.setEditable(false);
				albumField.setEditable(false);
				int p;			
			
				if(addOK==true)
				{
					Song s=new Song(song,artist,album,year);
					//added list model as an argument so i can keep addSong static
					SongLib.addSong(s, listModel);
					addOK=false;
					p = listModel.size() - 1;
					setSelectedSong(p);
				}
				if(editOK==true)
				{
					Song editedSong = new Song();
					Song s = SongLib.getSelectedSong();
					editedSong.setArtist(artistField.getText());
					editedSong.setSong(nameField.getText());
					editedSong.setAlbum(albumField.getText());
					editedSong.setYear(yearField.getText());
					
					SongLib.deleteSong(s, listModel);
                    SongLib.addSong(editedSong, listModel);
					 editOK=false;

				}
			}
		}
	}
			
	//these 2 methods are suppose to be in songLib
	public static int getSelectedSongIndex() {
		int x =list.getSelectedIndex();
		return x;
	}
	
	public void setSelectedSong(int i) {
		//checking out of bounds
		if(listModel.getSize() > 0 && i < listModel.getSize()) {
			list.setSelectedIndex(i);
		}
	}
	
	public class ListListener implements ListSelectionListener {
	
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int o=getSelectedSongIndex();
			setSelectedSong(o);
			Song s = SongLib.getSelectedSong();

			edit.setEnabled(true);
			del.setEnabled(true);
			ok.setEnabled(false);
			add.setEnabled(true);
			nameField.setEditable(false);
			yearField.setEditable(false);
			artistField.setEditable(false);
			albumField.setEditable(false);	
			
			if(s != null) { //if object song is null 
				nameField.setText(s.getSong());
				artistField.setText(s.getArtist());
				albumField.setText(s.getAlbum());
				yearField.setText(s.getYear());
			}
		}
	}

	public static void errorMessage(){
		nameField.setText("DUPLICATE");
		albumField.setText("DUPLICATE");
		artistField.setText("DUPLICATE");
		yearField.setText("DUPLICATE");

	}
	
	public void setFrame(){
		songLib.setSize(400,600);
		songLib.setLocationRelativeTo(null);
		songLib.setResizable(true);
		songLib.setVisible(true);
		nameField.setEditable(false);
		yearField.setEditable(false);
		artistField.setEditable(false);
		albumField.setEditable(false);	
		
		if(list.isSelectionEmpty()){
			edit.setEnabled(false);
			del.setEnabled(false);
			ok.setEnabled(false);
			add.setEnabled(true);
		}
		else{
			edit.setEnabled(true);
			del.setEnabled(true);
			ok.setEnabled(false);
			add.setEnabled(true);	
		}
		songLib.setDefaultCloseOperation(EXIT_ON_CLOSE);
		songLib.addWindowListener(new WindowAdapter() 
		{
			public void	windowClosing(WindowEvent e) 
			{
				try {
					Storage save=null;
					SongLib.storeList(save);
					
				} catch(Exception ex) {}
			}
		});
	}

	
}