package com.reloading.browser.component;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class ComponentPanel extends JDialog  {
  //private static Logger log4j = org.apache.log4j.Logger.getLogger(ComponentPanel.class);
  private static ComponentPanel csp;
  protected static final String SELECT  = "Select";
  protected static final String HELP  = "Help";
  private JButton selectButton;
  private JButton helpButton;
  private ComponentListener changeServerListener;
  //TODO: SWITCH THIS BACK TO PRIVATE
  protected JList<HashMap<String, String>> serverList;
  private DefaultListModel<HashMap<String, String>> serverModel;
  //private ModelIndexDAO modelIndex;
  //private CV2ServerDAO server;
  
  //private ComponentPanel(CV2ServerDAO server) {
  private ComponentPanel() {
    super();
    //this.server = server;
    //if (System.getProperty(CV2Properties.DEBUG).equalsIgnoreCase("true")) {
    //  log4j.setLevel(Level.DEBUG);
    //}
    
    ComponentHandler csHandler = new ComponentHandler(this);
    setLayout(new BorderLayout());
    setTitle("Change Server");
    
    //add in the drop down
    serverModel = new DefaultListModel<HashMap<String,String>>();
    serverList = new JList<HashMap<String, String>>(serverModel);
    serverList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    serverList.setCellRenderer(getListCellRenderer());
    buildServerList();
    // load library on double click
    serverList.addMouseListener(csHandler);
    
    JScrollPane jspServerPane = new JScrollPane(serverList);
    jspServerPane.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED 
        & JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    add(jspServerPane,BorderLayout.CENTER);
    
    //Add the help and select buttons
    JPanel jpButtonPanel = new JPanel();
    
    jpButtonPanel.setLayout(new FlowLayout());

    helpButton = new JButton(HELP);
    helpButton.addActionListener(csHandler);
    jpButtonPanel.add(helpButton);
    
    selectButton = new JButton(SELECT);
    selectButton.addActionListener(csHandler);
    jpButtonPanel.add(selectButton);    
    add(jpButtonPanel,BorderLayout.SOUTH);
    pack();
    //setSize(640, 480);
    setLocationByPlatform(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
    toFront();
  }
  
  /**
   * Reads Servers from CV2DAO adn
   * and adds it to the drop down
   */
  private void buildServerList() {
	  //String currentServerName = server.getCurrentServer().getName();
	  String serverName;
	  String url;
	  int index = 0;
	  int selectedIndex = 0;
	  serverModel.clear();
	  //List<CV2Server> servers = server.getServers();
	  //Iterator<CV2Server> iterator = servers.iterator();
	  //Add the servers to the serverModel
	
	  //Set the selection in the list
	  serverList.setSelectedIndex(selectedIndex);
  }
 
  private ListCellRenderer<HashMap<String, String>> getListCellRenderer() {
	  
	    ListCellRenderer<HashMap<String, String>> listCellRenderer = new ListCellRenderer<HashMap<String, String>>() {
	      @Override
	      public Component getListCellRendererComponent(JList<? extends HashMap<String, String>> list,
	          HashMap<String, String> value, int index, boolean isSelected, boolean cellHasFocus) {
	      
	        JLabel label = new JLabel(value.get("serverName"));
	        label.setOpaque(true);
	        
	        if (isSelected) {
	          label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	          label.setBackground(new Color(163, 184, 204));
	        }
	        else {
	          label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
	          if (index % 2 != 0)
	            label.setBackground(new Color(223, 234, 245));
	          else
	            label.setBackground(Color.WHITE);
	        }

	        label.setMaximumSize(label.getPreferredSize());
	        return label;
	      }
	    };
	    
	    return listCellRenderer;
	  }
 
 
}
